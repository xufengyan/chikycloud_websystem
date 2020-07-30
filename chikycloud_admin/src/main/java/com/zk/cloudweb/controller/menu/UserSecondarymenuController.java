package com.zk.cloudweb.controller.menu;

import com.zk.cloudweb.entity.UserSecondarymemu;
import com.zk.cloudweb.service.IUserSecondarymemuService;
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
 * @author xf
 * @version 1.0
 * @date 2020/5/26 15:03
 */
@RequestMapping("/secondarymenu")
@Controller
public class UserSecondarymenuController {
    @Autowired
    private IUserSecondarymemuService userSecondarymemuService;

    /**
     * 查询下拉菜单
     * @return
     */
    @RequestMapping("/getSecondarymenuListHtml")
    public String getHeadmenuListHtml(){
        return "menu/secondarymenuList";
    }

    /**
     * 添加下拉菜单
     * @return
     */
    @RequestMapping("/addSecondarymenuHtml")
    public String addHeadmenuHtml(){
        return "menu/secondarymenuAdd";
    }


    /**
     * 修改下拉菜单
     * @return
     */
    @RequestMapping("/updateSecondarymenuHtml")
    public ModelAndView updateHeadmenuHtml(ModelAndView model, String id , String headmenuId){
        model.setViewName("menu/secondarymenuUpdate");
        model.addObject("id",id);
        model.addObject("headmenuId",headmenuId);
        return model;
    }

    /**
     * 查询下拉菜单列表
     * @param userSecondarymenu
     * @return
     */
    @RequestMapping("/getSecondarymenuList")
    @ResponseBody
    public Result getSecondarymenuList(UserSecondarymemu userSecondarymenu){

        List userSecondarymemus = userSecondarymemuService.selectHeadSecondaryList(userSecondarymenu);

        Result result = new Result(ResultEnum.OK,userSecondarymemus,true);

        result.setCount(userSecondarymemuService.selectHeadSecondaryListCount(userSecondarymenu));
        return result;
    }


    /**
     * 根据id查找菜单
     * @param secondarymemu
     * @return
     */
    @RequestMapping("/getSecondarymenu")
    @ResponseBody
    public Result getSecondarymenu(UserSecondarymemu secondarymemu){
        UserSecondarymemu userSecondarymemu = userSecondarymemuService.selectUserSecondarymemuById(secondarymemu.getId());
        Result result = new Result(ResultEnum.OK,userSecondarymemu,true);
        return result;
    }

    /**
     * 添加下拉菜单
     * @param userSecondarymemu
     * @return
     */
    @RequestMapping("/addSecondarymenu")
    @ResponseBody
    public Result addSecondarymenu(@RequestBody(required = true) UserSecondarymemu userSecondarymemu){

        int res = userSecondarymemuService.insertUserSecondarymemu(userSecondarymemu);

        Result result = new Result(ResultEnum.OK,res,true);

        return result;
    }

    /**
     * 修改下拉菜单
     * @param userSecondarymemu
     * @return
     */
    @RequestMapping("/updateSecondarymenu")
    @ResponseBody
    public Result updateSecondarymenu(@RequestBody(required = true) UserSecondarymemu userSecondarymemu){

        int res = userSecondarymemuService.updateUserSecondarymemu(userSecondarymemu);

        Result result = new Result(ResultEnum.OK,res,true);

        return result;
    }

}
