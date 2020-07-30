package com.zk.cloudweb.controller.menu;

import com.zk.cloudweb.entity.UserMenu;
import com.zk.cloudweb.service.IUserMenuService;
import com.zk.cloudweb.util.Enum.ResultEnum;
import com.zk.cloudweb.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * 菜单controller
 * @author xf
 * @version 1.0
 * @date 2020/5/27 10:41
 */
@RequestMapping("menu")
@Controller
public class UserMenuController {

    @Autowired
    private IUserMenuService userMenuService;

    /**
     * 跳转到list页面
     * @return
     */
    @RequestMapping("/getMenuListHtml")
    public String getMenuListHtml(){
        return "menu/menuList";
    }


    /**
     * 跳转添加
     * @return
     */
    @RequestMapping("/addMenuHtml")
    public String addMenuHtml(){
        return "menu/menuAdd";
    }


    /**
     * 跳转修改页面
     * @return
     */
    @RequestMapping("/updateMenuHtml")
    public ModelAndView updateMenuHtml(ModelAndView model,String id,String headmenuId,String sercondarymenuId){
        model.addObject("headmenuId",headmenuId);
        model.addObject("sercondarymenuId",sercondarymenuId);
        model.addObject("id",id);
        model.setViewName("menu/menuUpdate");
        return model;
    }



    @RequestMapping("/getMenu")
    @ResponseBody
    public Result getMenu(UserMenu menu){
        UserMenu userMenu = userMenuService.selectUserMenuById(menu.getId());
        Result result = new Result(ResultEnum.OK,userMenu,true);
        return result;
    }


    @RequestMapping("/getMenuList")
    @ResponseBody
    public Result getMenuList(UserMenu menu){
      List list = userMenuService.selectALlMenuList(menu);
      Result result = new Result(ResultEnum.OK,list,true);
      result.setCount(userMenuService.selectALlMenuListCount(menu));
      return result;
    }



    @RequestMapping("/addMenu")
    @ResponseBody
    public Result addMenu(@RequestBody(required = true) UserMenu userMenu){
        int res = userMenuService.insertUserMenu(userMenu);
        Result result = new Result(ResultEnum.OK,res,true);
        return result;
    }



    @RequestMapping("/updateMenu")
    @ResponseBody
    public Result updateMenu(@RequestBody(required = true) UserMenu userMenu){
        int res = userMenuService.updateUserMenu(userMenu);
        Result result = new Result(ResultEnum.OK,res,true);
        return result;
    }


}
