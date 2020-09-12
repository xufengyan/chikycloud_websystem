package com.zk.cloudweb.controller.login;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.zk.cloudweb.entity.User;
import com.zk.cloudweb.service.IUserService;
import com.zk.cloudweb.util.Enum.ResultEnum;
import com.zk.cloudweb.util.RedisKey;
import com.zk.cloudweb.util.RedisUtil;
import com.zk.cloudweb.util.Result;
import com.zk.cloudweb.util.VerifyCodeUtil;
import com.zk.cloudweb.util.email.EmailUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

/**
 * @author xf
 * @version 1.0
 * @date 2020/8/11 11:45
 */
@Controller
public class SignUpController {
    @Resource(name = "captchaProducer")
    private Producer captchaProducer;

    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;

    @Autowired
    private IUserService userService;
    @Autowired
    RedisUtil redisUtil;
    /**
     * 跳转到注册页面
     * @return
     */
    @RequestMapping("/register")
    public String getSignUpHtml(){
        return "register";
    }

    /**
     * 向用户邮箱发送验证码
     * @return
     */
    @RequestMapping("/sendAuthCode")
    @ResponseBody
    public Result sendAuthCode(String uName){
        Result result= null;
        String authCode = null;

        //验证当前账号是否注册过
        User user = new User();
        user.setUName(uName);
        List<User> users = userService.selectUserList(user);

        if (users.size()>0){
            return new Result(ResultEnum.SIGNUP_ERROR,false);
        }
        Jedis jedis = null;
        try {
            jedis = redisUtil.getJedis();
            authCode = jedis.get(RedisKey.EMAILAUTHCODE +":"+uName);
            if (null!=authCode){
             return result =new Result(ResultEnum.REPEATEDREQUEST,false);
            }else {
                authCode = VerifyCodeUtil.generateVerifyCode(4);
                jedis.set(RedisKey.EMAILAUTHCODE+":"+uName,authCode);
                //设置过期时间为一分钟
                jedis.expire(RedisKey.EMAILAUTHCODE+":"+uName,60);
                result =new Result(ResultEnum.OK,true);
                EmailUtil.sendEmail(uName,authCode);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            jedis.close();
        }
        return result;
    }


    /**
     * 用户注册
     * @param user
     * @return
     */
    @RequestMapping("/addUser")
    @ResponseBody
    public Result addUser(User user){
        Result result = null;
        String authCode = user.getAuthCode();
        Jedis jedis = null;
        try {
            jedis = redisUtil.getJedis();
            String resAuthCode = jedis.get(RedisKey.EMAILAUTHCODE +":"+user.getUName());
            if (null!=authCode&&null!=resAuthCode){
                if (authCode.equals(resAuthCode)){//判断
                    user.setRoleId("zk1111");
                    int res = userService.insertUser(user);
                    if(res==0){
                        result = new Result(ResultEnum.SIGNUP_ERROR,false);
                    }else {
                        //注册成功后自动登录
                        UsernamePasswordToken token = new UsernamePasswordToken(user.getUName(), user.getUPassword());
                        Subject subject = SecurityUtils.getSubject();
                        try
                        {
                            subject.login(token);
                            result = new Result(ResultEnum.OK,true);
                        }
                        catch (AuthenticationException e)
                        {
                            result = new Result(ResultEnum.NO,"登录失败，账号或者密码错误,请重新输入",false);

                        }
                    }

                }else {
                    result = new Result(ResultEnum.AUTHCODE_ERROR,false);
                }
            }else {
                result = new Result(ResultEnum.NOAUTHCODE_ERROR,false);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            jedis.close();
        }
        return result;
    }


    /**
     * 验证码生成
     */
    @GetMapping(value = "/captchaImage")
    public ModelAndView getKaptchaImage(HttpServletRequest request, HttpServletResponse response)
    {
        ServletOutputStream out = null;
        try
        {
            HttpSession session = request.getSession();
            response.setDateHeader("Expires", 0);
            response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
            response.addHeader("Cache-Control", "post-check=0, pre-check=0");
            response.setHeader("Pragma", "no-cache");
            response.setContentType("image/jpeg");

            String type = request.getParameter("type");
            String capStr = null;
            String code = null;
            BufferedImage bi = null;
//            if ("math".equals(type))
//            {
                    //计算验证码
//                String capText = captchaProducerMath.createText();
//                capStr = capText.substring(0, capText.lastIndexOf("@"));
//                code = capText.substring(capText.lastIndexOf("@") + 1);
//                bi = captchaProducerMath.createImage(capStr);
//            }
//            else if ("char".equals(type))
//            {
                //纯字符验证码
                capStr = code = captchaProducer.createText();
                bi = captchaProducerMath.createImage(capStr);
//            }
            session.setAttribute(Constants.KAPTCHA_SESSION_KEY, code);
            out = response.getOutputStream();
            ImageIO.write(bi, "jpg", out);
            out.flush();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (out != null)
                {
                    out.close();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return null;
    }

}
