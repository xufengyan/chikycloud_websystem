package com.zk.cloudweb.controller.menu;

import com.github.pagehelper.PageHelper;
import com.zk.cloudweb.entity.UserMenu;
import com.zk.cloudweb.entity.UserRole;
import com.zk.cloudweb.entity.UserRoleMenu;
import com.zk.cloudweb.service.IUserMenuService;
import com.zk.cloudweb.service.IUserRoleMenuService;
import com.zk.cloudweb.service.IUserRoleService;
import com.zk.cloudweb.util.Enum.ResultEnum;
import com.zk.cloudweb.util.Result;
import com.zk.cloudweb.util.getShiroUser;
import com.zk.cloudweb.util.page.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

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

    @Autowired
    private IUserMenuService userMenuService;

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

    /**
     * 跳转到用户角色添加页面
     * @return
     */
    @RequestMapping("/userRoleAddHtml")
    public ModelAndView userRoleAddHtml(String uId,String roleId,ModelAndView model){
        model.addObject("uId",uId);
        model.addObject("roleId",roleId);
        model.setViewName("user/userRoleAdd");
        return model;
    }

    @RequestMapping("/getRoleLsit")
    @ResponseBody
    public Result getRoleLsit(UserRole role){
        PageHelper.startPage(role.getPage(),role.getLimit());
        List<UserRole> userRoles = userRoleService.selectUserRoleList(role);
        return PageUtil.setpage(userRoles);
    }
//    getUserRoleLsit

    /**
     * 查询当前用户拥有的菜单
     * @param role
     * @return
     */
    @RequestMapping("/getUserRoleLsit")
    @ResponseBody
    public Result getUserRoleLsit(UserRole role){
        String roleId = role.getId();
        String [] roleIdArr = roleId.split(",");
        PageHelper.startPage(role.getPage(),role.getLimit());
        List<UserRole> userRoles = userRoleService.selectUserRoleListById(roleIdArr);
        int num = 1;
        int num2 = num;
        List<Map<String,Object>> roleMenuList = new ArrayList<>();
        for (int i = 0; i < userRoles.size(); i++) {
//            UserRoleMenu userRoleMenu = new UserRoleMenu();
//            userRoleMenu.setRoleId(userRoles.get(0).getId());
            Map<String,Object> map = new HashMap<>();
            map.put("authorityName",userRoles.get(i).getRoleName());
            map.put("authorityId",++num);
            map.put("menuIcon","layui-icon-set");
            map.put("checked",1);
            map.put("isMenu",0);
            map.put("parentId",-1);
            roleMenuList.add(map);
            num2 = num;
            List<UserMenu> roleMenus = userMenuService.selectUserRoleMenuByRoleId(userRoles.get(i).getId());
            for (int i1 = 0; i1 < roleMenus.size(); i1++) {
                Map<String,Object> map2 = new HashMap<>();
                map2.put("authorityName",roleMenus.get(i1).getTitle());
                map2.put("authorityId",++num);
                map2.put("menuIcon","layui-icon-set");
                map2.put("checked",1);
                map2.put("isMenu",1);
                map2.put("parentId",num2);
                roleMenuList.add(map2);
            }
            num2 = num;
        }
        return PageUtil.setpage(roleMenuList);
    }

    /**
     * 查询当前用户不拥有的角色
     * @param role
     * @return
     */
    @RequestMapping("/getUserNoRoleLsit")
    @ResponseBody
    public Result getUserNoRoleLsit(UserRole role){
        String roleId = role.getId();
        String [] roleIdArr = roleId.split(",");
        PageHelper.startPage(role.getPage(),role.getLimit());
        List<UserRole> userRoles = userRoleService.selectUserRoleList(role);
        Iterator<UserRole> iterator = userRoles.iterator();
        while (iterator.hasNext()){
            for (String s : roleIdArr) {
                if(iterator.next().getId().equals(s)){
                    iterator.remove();
                }
            }
        }
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
