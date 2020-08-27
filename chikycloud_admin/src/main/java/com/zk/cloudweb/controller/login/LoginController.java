package com.zk.cloudweb.controller.login;


import com.zk.cloudweb.controller.socket.service.serviceSend;
import com.zk.cloudweb.controller.socket.util.Analysis;
import com.zk.cloudweb.entity.User;
import com.zk.cloudweb.entity.ZkMachineSet;
import com.zk.cloudweb.util.StringUtils;
import com.zk.cloudweb.util.Enum.ResultEnum;
import com.zk.cloudweb.util.Result;
import com.zk.cloudweb.util.socketChannel.channelSingle;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


/**
 * @author xf
 * @version 1.0
 * @date 2020/5/21 9:39
 */
@Controller
public class LoginController {

    @RequestMapping("/login")
    public String loginHtml(){
        return "login";
    }

    @RequestMapping("/home")
    public String homeHtml() {
        return "home";
    }
    @RequestMapping("/index")
    public ModelAndView indexHtml(ModelAndView model){
        Subject subject= SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        model.setViewName("index");
        model.addObject("user",user);
        return model;
    }

    @RequestMapping(value = "/getLogin",method = RequestMethod.POST)
    @ResponseBody
    public Result userLogin(String userName, String password){

//        channelSingle channelSingleUtil = channelSingle.getChannelUtil();
//
//        serviceSend.socket_setMachine_Data(channelSingleUtil.getChannelMap().get("869467040604671"),new ZkMachineSet());

        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
        Subject subject = SecurityUtils.getSubject();
        try
        {
            subject.login(token);
            Result result = new Result(ResultEnum.OK,true);
            return result;
        }
        catch (AuthenticationException e)
        {
            Result result = new Result(ResultEnum.NO,false);
            result.setMsg("账号或者密码错误");
//            String msg = "用户或密码错误";
            if (StringUtils.isNotEmpty(e.getMessage()))
            {
                result.setMsg(e.getMessage());
            }
            return result;
        }
    }





}
