package com.zk.cloudweb.controller.phone;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author xf
 * @version 1.0
 * @date 2020/10/10 17:23
 */
@Controller
@RequestMapping("measure")
public class zkMeasure {

    @RequestMapping("login.html")
    public String getLoginHtml(){
        return "H5/loginH5";
    }

    /**
     * 跳转到机器列表
     * @return
     */
    @RequestMapping("machineList.html")
    public String getMachineListHtml(){
        return "H5/machineListH5";
    }

    /**
     * 跳转测量列表
     * @return
     */
    @RequestMapping("measureList.html")
    public ModelAndView getMeasureListHtml(String machineNum, ModelAndView model){
        model.addObject("machineNum",machineNum);
        model.setViewName("H5/measureListH5");
        return model;
    }



}
