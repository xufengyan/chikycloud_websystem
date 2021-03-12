package com.zk.cloudweb.controller.menu;

import com.github.pagehelper.PageHelper;
import com.zk.cloudweb.entity.UserMenu;
import com.zk.cloudweb.entity.UserRoleMenu;
import com.zk.cloudweb.service.IUserMenuService;
import com.zk.cloudweb.service.IUserRoleMenuService;
import com.zk.cloudweb.util.Enum.ResultEnum;
import com.zk.cloudweb.util.Result;
import com.zk.cloudweb.util.page.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xf
 * @version 1.0
 * @date 2020/5/29 9:42
 */
@Controller
@RequestMapping("/roleMenu")
public class UserRoleMenuController {

    @Autowired
    private IUserRoleMenuService userRoleMenuService;
    @Autowired
    private IUserMenuService userMenuService;

    @RequestMapping("/getRoleMenuListHtml")
    public ModelAndView getRoleMenuListHtml(String roleId, String roleName, ModelAndView model){
        model.addObject("roleId",roleId);
        model.addObject("roleName",roleName);
        model.setViewName("menu/role_menuList");
        return model;
    }

    @RequestMapping("/getRoleMenuListAddHtml")
    public ModelAndView getRoleMenuListAddHtml(String roleId, ModelAndView model){
        model.addObject("roleId",roleId);
        model.setViewName("menu/role_menuListAdd");
        return model;
    }


    @RequestMapping("/getRoleMenuList")
    @ResponseBody
    public Result getRoleMenuList(UserRoleMenu roleMenu){
        PageHelper.startPage(roleMenu.getPage(),roleMenu.getLimit());
        List<UserRoleMenu> roleMenus = userRoleMenuService.selectUserRoleMenuList(roleMenu);
        List<UserMenu> userMenus = new ArrayList<>();
        if (roleMenus.size()>0){
            for (UserRoleMenu menu : roleMenus) {
                UserMenu resMenu = userMenuService.selectUserMenuById(menu.getMenuId());
                userMenus.add(resMenu);
            }
        }
        return PageUtil.setpage(userMenus);
    }

    /**
     * 查询当前用户不存在的权限
     * @param roleMenu
     * @return
     */
    @RequestMapping("/getNORoleMenuList")
    @ResponseBody
    public Result getNORoleMenuList(UserRoleMenu roleMenu){
        List<UserRoleMenu> userRoleMenus = userRoleMenuService.selectUserRoleMenuList(roleMenu);
        List<UserMenu> UserMenus = userMenuService.selectUserMenuList(new UserMenu());
        List<UserMenu> userMenus = new ArrayList<>();
        boolean b = true;
        if (UserMenus.size()>0){
            for (UserMenu menu : UserMenus) {
                for (UserRoleMenu userRoleMenu : userRoleMenus){
                    if (userRoleMenu.getMenuId().equals(menu.getId())){
                        b =false;
                    }
                }
                if (b){
                    UserMenu resMenu = userMenuService.selectUserMenuById(menu.getId());
                    userMenus.add(resMenu);
                }
                b = true;
            }
        }
        Result result = new Result(ResultEnum.OK,userMenus,true);
        return result;
    }


    /**
     * 批量添加权限
     * @param menuIdarr
     * @param roleId
     * @return
     */
    @RequestMapping("/addRoleMenuByArr")
    @ResponseBody
    public Result addRoleMenuByArr(String [] menuIdarr, String roleId){
        for (String s : menuIdarr) {
            UserRoleMenu userRoleMenu = new UserRoleMenu();
            userRoleMenu.setMenuId(s);
            userRoleMenu.setRoleId(roleId);
            userRoleMenuService.insertUserRoleMenu(userRoleMenu);
        }
        Result result = new Result(ResultEnum.OK,true);
        return result;
    }


    /**
     * 批量移除权限
     * @param menuIdarr
     * @param roleId
     * @return
     */
    @RequestMapping("/delRoleMenuByArr")
    @ResponseBody
    public Result delRoleMenuByArr(String [] menuIdarr, String roleId){
        for (String s : menuIdarr) {
            UserRoleMenu userRoleMenu = new UserRoleMenu();
            userRoleMenu.setMenuId(s);
            userRoleMenu.setRoleId(roleId);
            int res = userRoleMenuService.deleteUserRoleMenuByRM(userRoleMenu);
        }
        Result result = new Result(ResultEnum.OK,true);
        return result;
    }


}
