package com.zk.cloudweb.controller.menu;

import com.github.pagehelper.PageHelper;
import com.zk.cloudweb.entity.UserRole;
import com.zk.cloudweb.service.IUserRoleService;
import com.zk.cloudweb.util.Enum.ResultEnum;
import com.zk.cloudweb.util.Result;
import com.zk.cloudweb.util.page.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author xf
 * @version 1.0
 * @date 2020/5/28 13:55
 */

@RequestMapping("/role")
@Controller
public class UserRoleController {

    @Autowired
    private IUserRoleService userRoleService;


    @RequestMapping("/getRoleListHtml")
    public String getRoleListHtml(){
        return "menu/roleList";
    }


    @RequestMapping("/addRoleHtml")
    public String addRoleHtml(){
        return "menu/roleAdd";
    }


    @RequestMapping("/updateRoleHtml")
    public ModelAndView updateRoleHtml(ModelAndView model,String id){
        model.setViewName("menu/roleUpdate");
        model.addObject("id",id);
        return model;
    }


    @RequestMapping("/getRoleLsit")
    @ResponseBody
    public Result getRoleLsit(UserRole role){
        PageHelper.startPage(role.getPage(),role.getLimit());
        List<UserRole> userRoles = userRoleService.selectUserRoleList(role);
        return PageUtil.setpage(userRoles);
    }



    @RequestMapping("/getRole")
    @ResponseBody
    public Result getRole(UserRole role){
        UserRole userRole = userRoleService.selectUserRoleById(role.getId());
        Result result = new Result(ResultEnum.OK,userRole,true);
        return result;
    }

    @RequestMapping("/addRole")
    @ResponseBody
    public Result addRole(@RequestBody(required = true) UserRole userRole){
        int res = userRoleService.insertUserRole(userRole);
        Result result = new Result(ResultEnum.OK,res,true);
        return result;
    }


    @RequestMapping("/updateRole")
    @ResponseBody
    public Result updateRole(@RequestBody(required = true) UserRole userRole){
        int res = userRoleService.updateUserRole(userRole);

        Result result = new Result(ResultEnum.OK,res,true);

        return result;
    }





}
