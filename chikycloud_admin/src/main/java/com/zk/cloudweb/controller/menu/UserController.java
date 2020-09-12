package com.zk.cloudweb.controller.menu;

import com.zk.cloudweb.entity.User;
import com.zk.cloudweb.service.IUserService;
import com.zk.cloudweb.util.Enum.ResultEnum;
import com.zk.cloudweb.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 用户管理
 * @author xf
 * @version 1.0
 * @date 2020/8/4 16:10
 */

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;

    /**
     * 跳转到用户列表页面
     * @return
     */
    @RequestMapping("/getUserListHtml")
    public String getUserListHtml(){
        return "user/userList";
    }

    @RequestMapping("/addUserHtml")
    public String addUserHtml(){
        return "user/userAdd";
    }


    /**
     * 查询用户
     * @return
     */
    @RequestMapping("/getUserList")
    @ResponseBody
    public Result getUserList(User user){
        List<User> users = userService.selectUserList(user);
        Result result =new Result(ResultEnum.OK,users,true);
        result.setCount(userService.selectUserListCount(user));
        return result;
    }


    /**
     * 添加用户
     * @param user
     * @return
     */
    @RequestMapping("/addUser")
    @ResponseBody
    public Result addUser(User user){
        Result result = null;
        int res = userService.insertUser(user);
        if(res == 0){
            result = new Result(ResultEnum.SIGNUP_ERROR,false);
        }else {
            result =new Result(ResultEnum.OK,true);
        }
        return result;
    }

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    @RequestMapping("/updateUser")
    @ResponseBody
    public Result updateUser(User user){

        int res = userService.updateUser(user);
        Result result =new Result(ResultEnum.OK,true);
        return result;
    }

    /**
     * 删除用户
     * @param user
     * @return
     */
    @RequestMapping("/deleteUser")
    @ResponseBody
    public Result deleteUser(User user){
        int res = userService.deleteUserById(user.getId());
        Result result =new Result(ResultEnum.OK,true);
        return result;
    }



}
