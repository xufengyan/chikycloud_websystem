package com.zk.cloudweb.controller.menu;


import com.alibaba.fastjson.JSON;

import com.zk.cloudweb.entity.*;
import com.zk.cloudweb.entity.index.homeInfo;
import com.zk.cloudweb.entity.index.logoInfo;
import com.zk.cloudweb.entity.index.muenData;
import com.zk.cloudweb.service.IUserHeadmenuService;
import com.zk.cloudweb.service.IUserMenuService;
import com.zk.cloudweb.service.IUserRoleMenuService;
import com.zk.cloudweb.service.IUserSecondarymemuService;
import com.zk.cloudweb.util.Enum.ResultEnum;
import com.zk.cloudweb.util.Result;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

/**
 * 头部菜单Controller
 * 
 * @author ruoyi
 * @date 2020-05-21
 */
@Controller
@RequestMapping("/headmenu")
public class UserHeadmenuController
{

    @Autowired
    private IUserHeadmenuService userHeadmenuService;
    @Autowired
    private IUserRoleMenuService userRoleMenuService;
    @Autowired
    private IUserMenuService userMenuService;
    @Autowired
    private IUserSecondarymemuService userSecondarymemuService;


    /**
     * 查询头部菜单
     * @return
     */
    @RequestMapping("/getHeadmenuListHtml")
    public String getHeadmenuListHtml(){
        return "menu/headmenuList";
    }

    /**
     * 添加头部菜单
     * @return
     */
    @RequestMapping("/addHeadmenuHtml")
    public String addHeadmenuHtml(){
        return "menu/headmenuAdd";
    }


    /**
     * 修改头部菜单
     * @return
     */
    @RequestMapping("/updateHeadmenuHtml")
    public ModelAndView updateHeadmenuHtml(ModelAndView model, String id){
        model.setViewName("menu/headmenuUpdate");
        model.addObject("id",id);
        return model;
    }


    /**
     * 查询头部菜单列表
     * @param userHeadmenu
     * @return
     */
    @RequestMapping("/getHeadmenuList")
    @ResponseBody
    public Result getHeadmenuList(UserHeadmenu userHeadmenu){

        List<UserHeadmenu> userHeadmenus = userHeadmenuService.selectUserHeadmenuList(userHeadmenu);

        Result result = new Result(ResultEnum.OK,userHeadmenus,true);

        result.setCount(userHeadmenuService.selectUserHeadmenuListCount(userHeadmenu));
        return result;
    }


    /**
     * 查询单个菜单
     * @param userHeadmenu
     * @return
     */
    @RequestMapping("/getHeadmenu")
    @ResponseBody
    public Result getHeadmenu(UserHeadmenu userHeadmenu){

        UserHeadmenu headmenu = userHeadmenuService.selectUserHeadmenuById(userHeadmenu.getId());

        Result result = new Result(ResultEnum.OK,headmenu,true);
        return result;
    }

    /**
     * 添加头部菜单
     * @param userHeadmenu
     * @return
     */
    @RequestMapping("/addHeadmenu")
    @ResponseBody
    public Result addHeadmenu(@RequestBody(required = true) UserHeadmenu userHeadmenu){
        int res = userHeadmenuService.insertUserHeadmenu(userHeadmenu);
        Result result = new Result(ResultEnum.OK,res,true);
        return result;
    }


    /**
     * 修改菜单
     * @param userHeadmenu
     * @return
     */
    @RequestMapping("/updateHeadmenu")
    @ResponseBody
    public Result updateHeadmenu(@RequestBody(required = true) UserHeadmenu userHeadmenu){
        int res = userHeadmenuService.updateUserHeadmenu(userHeadmenu);
        Result result = new Result(ResultEnum.OK,res,true);
        return result;
    }






    /**
     * 根据用户所拥有的权限查询菜单
     * @return
     */
    @RequestMapping("/getmenu")
    @ResponseBody
    public String getmenu(){
        /**
         * 从shiro中查询用户信息
         */
        Subject subject= SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();

        UserRoleMenu userRoleMenu = new UserRoleMenu();
        userRoleMenu.setRoleId(user.getRoleId());
        List<UserRoleMenu> userRoleMenus =  userRoleMenuService.selectUserRoleMenuList(userRoleMenu);

        List<UserHeadmenu> userHeadmenus = new ArrayList<>();
        List<UserSecondarymemu> userSecondarymemus = new ArrayList<>();
        for (UserRoleMenu roleMenu : userRoleMenus) {
            UserMenu u = new UserMenu();
            u.setId(roleMenu.getMenuId());
            UserHeadmenu userHeadmenu = userMenuService.selectALlMenu(u);
            userHeadmenus.add(userHeadmenu);
        }
        Map<String, Set<String>> ms = new HashMap<>();
        Set<String> headTitleSet= new LinkedHashSet<>();
        for (UserHeadmenu userHeadmenu : userHeadmenus) {
            headTitleSet.add(userHeadmenu.getTitle());
        }
        
        for (String s : headTitleSet) {
            Set<String> secondarySet = new HashSet<>();
            for (UserHeadmenu userHeadmenu : userHeadmenus) {
                if(s.equals(userHeadmenu.getTitle())){
                    for (UserSecondarymemu userSecondarymemu : userHeadmenu.getChild()) {
                        secondarySet.add(userSecondarymemu.getTitle());
                    }
                }

            }
            ms.put(s, secondarySet);
        }
        List<UserHeadmenu> userHeadmenus1 = new ArrayList<>();
        for (String s : headTitleSet) {
            UserHeadmenu uh = new UserHeadmenu();
            List<UserSecondarymemu> us= new ArrayList<>();
            for (UserHeadmenu userHeadmenu : userHeadmenus) {
                if(s.equals(userHeadmenu.getTitle())){
                    uh.setTitle(userHeadmenu.getTitle());
                    uh.setHref(userHeadmenu.getHref());
                    uh.setIcon(userHeadmenu.getIcon());
                    uh.setTarget(userHeadmenu.getTarget());
                    us.add(userHeadmenu.getChild().get(0));
                }
            }
            uh.setChild(us);
            userHeadmenus1.add(uh);
        }
        for (UserHeadmenu userHeadmenu : userHeadmenus1) {
           List<UserSecondarymemu> userSecondarymemus1 = new ArrayList<>();
            Set<String> sets = ms.get(userHeadmenu.getTitle());
            for (String s : sets) {
                UserSecondarymemu us = new UserSecondarymemu();
                List<UserMenu> um= new ArrayList<>();
                for (UserSecondarymemu userSecondarymemu : userHeadmenu.getChild()) {
                    if (s.equals(userSecondarymemu.getTitle())){
                        us.setTitle(userSecondarymemu.getTitle());
                        us.setHref(userSecondarymemu.getHref());
                        us.setIcon(userSecondarymemu.getIcon());
                        us.setTarget(userSecondarymemu.getTarget());
                        um.add(userSecondarymemu.getChild().get(0));
                    }
                }
                us.setChild(um);
                userSecondarymemus1.add(us);
            }
            userHeadmenu.setChild(userSecondarymemus1);
        }


        muenData muenData = new muenData();
        muenData.setHomeInfo(new homeInfo());
        muenData.setLogoInfo(new logoInfo());
        muenData.setMenuInfo(userHeadmenus1);


        String json= JSON.toJSONString(muenData);
        return json;
    }


//    @RequestMapping("/delHeadmenu")
//    @ResponseBody
//    public Result delHeadmenu(String id){
//
//
//
//
//    }






}
