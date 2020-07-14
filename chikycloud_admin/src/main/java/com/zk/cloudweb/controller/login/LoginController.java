package com.zk.cloudweb.controller.login;


import com.zk.cloudweb.entity.User;
import com.zk.cloudweb.util.Enum.ResultEnum;
import com.zk.cloudweb.util.Result;
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
            Result result = new Result(ResultEnum.NO,"登录失败，账号或者密码错误,请重新输入",true);

//            String msg = "用户或密码错误";
//            if (StringUtils.isNotEmpty(e.getMessage()))
//            {
//                msg = e.getMessage();
//            }
            return result;
        }
    }

    @RequestMapping("/getinit")
    @ResponseBody
    public String getinit(){
        return "{\n" +
                "  \"homeInfo\": {\n" +
                "    \"title\": \"首页\",\n" +
                "    \"href\": \"page/welcome-1.html?t=1\"\n" +
                "  },\n" +
                "  \"logoInfo\": {\n" +
                "    \"title\": \"学校\",\n" +
                "    \"image\": \"images/logo.png\",\n" +
                "    \"href\": \"\"\n" +
                "  },\n" +
                "  \"menuInfo\": [\n" +
                "    {\n" +
                "      \"title\": \"个人\",\n" +
                "      \"icon\": \"fa fa-address-book\",\n" +
                "      \"href\": \"\",\n" +
                "      \"target\": \"_self\",\n" +
                "      \"child\": [\n" +
                "        {\n" +
                "          \"title\": \"主页模板\",\n" +
                "          \"href\": \"\",\n" +
                "          \"icon\": \"fa fa-home\",\n" +
                "          \"target\": \"_self\",\n" +
                "          \"child\": [\n" +
                "            {\n" +
                "              \"title\": \"主页一\",\n" +
                "              \"href\": \"page/welcome-1.html\",\n" +
                "              \"icon\": \"fa fa-tachometer\",\n" +
                "              \"target\": \"_self\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"title\": \"主页二\",\n" +
                "              \"href\": \"page/welcome-2.html\",\n" +
                "              \"icon\": \"fa fa-tachometer\",\n" +
                "              \"target\": \"_self\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"title\": \"主页三\",\n" +
                "              \"href\": \"page/welcome-3.html\",\n" +
                "              \"icon\": \"fa fa-tachometer\",\n" +
                "              \"target\": \"_self\"\n" +
                "            }\n" +
                "          ]\n" +
                "        },\n" +
                "        {\n" +
                "          \"title\": \"菜单管理\",\n" +
                "          \"href\": \"page/menu.html\",\n" +
                "          \"icon\": \"fa fa-window-maximize\",\n" +
                "          \"target\": \"_self\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"title\": \"系统设置\",\n" +
                "          \"href\": \"page/setting.html\",\n" +
                "          \"icon\": \"fa fa-gears\",\n" +
                "          \"target\": \"_self\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"title\": \"表格示例\",\n" +
                "          \"href\": \"page/table.html\",\n" +
                "          \"icon\": \"fa fa-file-text\",\n" +
                "          \"target\": \"_self\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"title\": \"表单示例\",\n" +
                "          \"href\": \"\",\n" +
                "          \"icon\": \"fa fa-calendar\",\n" +
                "          \"target\": \"_self\",\n" +
                "          \"child\": [\n" +
                "            {\n" +
                "              \"title\": \"普通表单\",\n" +
                "              \"href\": \"page/form.html\",\n" +
                "              \"icon\": \"fa fa-list-alt\",\n" +
                "              \"target\": \"_self\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"title\": \"分步表单\",\n" +
                "              \"href\": \"page/form-step.html\",\n" +
                "              \"icon\": \"fa fa-navicon\",\n" +
                "              \"target\": \"_self\"\n" +
                "            }\n" +
                "          ]\n" +
                "        },\n" +
                "        {\n" +
                "          \"title\": \"登录模板\",\n" +
                "          \"href\": \"\",\n" +
                "          \"icon\": \"fa fa-flag-o\",\n" +
                "          \"target\": \"_self\",\n" +
                "          \"child\": [\n" +
                "            {\n" +
                "              \"title\": \"登录-1\",\n" +
                "              \"href\": \"page/login-1.html\",\n" +
                "              \"icon\": \"fa fa-stumbleupon-circle\",\n" +
                "              \"target\": \"_blank\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"title\": \"登录-2\",\n" +
                "              \"href\": \"page/login-2.html\",\n" +
                "              \"icon\": \"fa fa-viacoin\",\n" +
                "              \"target\": \"_blank\"\n" +
                "            }\n" +
                "          ]\n" +
                "        },\n" +
                "        {\n" +
                "          \"title\": \"异常页面\",\n" +
                "          \"href\": \"\",\n" +
                "          \"icon\": \"fa fa-home\",\n" +
                "          \"target\": \"_self\",\n" +
                "          \"child\": [\n" +
                "            {\n" +
                "              \"title\": \"404页面\",\n" +
                "              \"href\": \"page/404.html\",\n" +
                "              \"icon\": \"fa fa-hourglass-end\",\n" +
                "              \"target\": \"_self\"\n" +
                "            }\n" +
                "          ]\n" +
                "        },\n" +
                "        {\n" +
                "          \"title\": \"其它界面\",\n" +
                "          \"href\": \"\",\n" +
                "          \"icon\": \"fa fa-snowflake-o\",\n" +
                "          \"target\": \"\",\n" +
                "          \"child\": [\n" +
                "            {\n" +
                "              \"title\": \"按钮示例\",\n" +
                "              \"href\": \"page/button.html\",\n" +
                "              \"icon\": \"fa fa-snowflake-o\",\n" +
                "              \"target\": \"_self\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"title\": \"弹出层\",\n" +
                "              \"href\": \"page/layer.html\",\n" +
                "              \"icon\": \"fa fa-shield\",\n" +
                "              \"target\": \"_self\"\n" +
                "            }\n" +
                "          ]\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"title\": \"学员\",\n" +
                "      \"icon\": \"fa fa-lemon-o\",\n" +
                "      \"href\": \"\",\n" +
                "      \"target\": \"_self\",\n" +
                "      \"child\": [\n" +
                "        {\n" +
                "          \"title\": \"图标列表\",\n" +
                "          \"href\": \"page/icon.html\",\n" +
                "          \"icon\": \"fa fa-dot-circle-o\",\n" +
                "          \"target\": \"_self\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"title\": \"图标选择\",\n" +
                "          \"href\": \"page/icon-picker.html\",\n" +
                "          \"icon\": \"fa fa-adn\",\n" +
                "          \"target\": \"_self\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"title\": \"颜色选择\",\n" +
                "          \"href\": \"page/color-select.html\",\n" +
                "          \"icon\": \"fa fa-dashboard\",\n" +
                "          \"target\": \"_self\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"title\": \"下拉选择\",\n" +
                "          \"href\": \"page/table-select.html\",\n" +
                "          \"icon\": \"fa fa-angle-double-down\",\n" +
                "          \"target\": \"_self\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"title\": \"文件上传\",\n" +
                "          \"href\": \"page/upload.html\",\n" +
                "          \"icon\": \"fa fa-arrow-up\",\n" +
                "          \"target\": \"_self\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"title\": \"富文本编辑器\",\n" +
                "          \"href\": \"page/editor.html\",\n" +
                "          \"icon\": \"fa fa-edit\",\n" +
                "          \"target\": \"_self\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"title\": \"省市县区选择器\",\n" +
                "          \"href\": \"page/area.html\",\n" +
                "          \"icon\": \"fa fa-rocket\",\n" +
                "          \"target\": \"_self\"\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"title\": \"班级\",\n" +
                "      \"icon\": \"fa fa-slideshare\",\n" +
                "      \"href\": \"\",\n" +
                "      \"target\": \"_self\",\n" +
                "      \"child\": [\n" +
                "        {\n" +
                "          \"title\": \"多级菜单\",\n" +
                "          \"href\": \"\",\n" +
                "          \"icon\": \"fa fa-meetup\",\n" +
                "          \"target\": \"\",\n" +
                "          \"child\": [\n" +
                "            {\n" +
                "              \"title\": \"按钮1\",\n" +
                "              \"href\": \"page/button.html?v=1\",\n" +
                "              \"icon\": \"fa fa-calendar\",\n" +
                "              \"target\": \"_self\",\n" +
                "              \"child\": [\n" +
                "                {\n" +
                "                  \"title\": \"按钮2\",\n" +
                "                  \"href\": \"page/button.html?v=2\",\n" +
                "                  \"icon\": \"fa fa-snowflake-o\",\n" +
                "                  \"target\": \"_self\",\n" +
                "                  \"child\": [\n" +
                "                    {\n" +
                "                      \"title\": \"按钮3\",\n" +
                "                      \"href\": \"page/button.html?v=3\",\n" +
                "                      \"icon\": \"fa fa-snowflake-o\",\n" +
                "                      \"target\": \"_self\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                      \"title\": \"表单4\",\n" +
                "                      \"href\": \"page/form.html?v=1\",\n" +
                "                      \"icon\": \"fa fa-calendar\",\n" +
                "                      \"target\": \"_self\"\n" +
                "                    }\n" +
                "                  ]\n" +
                "                }\n" +
                "              ]\n" +
                "            }\n" +
                "          ]\n" +
                "        },\n" +
                "        {\n" +
                "          \"title\": \"失效菜单\",\n" +
                "          \"href\": \"page/error.html\",\n" +
                "          \"icon\": \"fa fa-superpowers\",\n" +
                "          \"target\": \"_self\"\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"title\": \"个人信息\",\n" +
                "      \"href\": \"\",\n" +
                "      \"icon\": \"fa fa-home\",\n" +
                "      \"target\": \"_self\",\n" +
                "      \"child\": [\n" +
                "        {\n" +
                "          \"title\": \"个人中心\",\n" +
                "          \"href\": \"\",\n" +
                "          \"icon\": \"fa fa-meetup\",\n" +
                "          \"target\": \"\",\n" +
                "          \"child\": [\n" +
                "            {\n" +
                "              \"title\": \"查看个人信息\",\n" +
                "              \"href\": \"employee/getEmployeeHtml\",\n" +
                "              \"icon\": \"fa fa-list-alt\",\n" +
                "              \"target\": \"_self\"\n" +
                "            },{\n" +
                "              \"title\": \"修改个人信息\",\n" +
                "              \"href\": \"employee/updatePersonalHtml\",\n" +
                "              \"icon\": \"fa fa-list-alt\",\n" +
                "              \"target\": \"_self\"\n" +
                "            },{\n" +
                "              \"title\": \"查看辅导员信息\",\n" +
                "              \"href\": \"overtime/getOvertimeListHtml\",\n" +
                "              \"icon\": \"fa fa-list-alt\",\n" +
                "              \"target\": \"_self\"\n" +
                "            },{\n" +
                "              \"title\": \"请假申请\",\n" +
                "              \"href\": \"lea/getLeaHtml\",\n" +
                "              \"icon\": \"fa fa-list-alt\",\n" +
                "              \"target\": \"_self\"\n" +
                "            },{\n" +
                "              \"title\": \"查看请假记录\",\n" +
                "              \"href\": \"lea/getLeaListHtml\",\n" +
                "              \"icon\": \"fa fa-list-alt\",\n" +
                "              \"target\": \"_self\"\n" +
                "            }\n" +
                "          ]\n" +
                "\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"title\": \"学员管理\",\n" +
                "      \"href\": \"\",\n" +
                "      \"icon\": \"fa fa-home\",\n" +
                "      \"target\": \"_self\",\n" +
                "      \"child\": [\n" +
                "        {\n" +
                "          \"title\": \"学员管理\",\n" +
                "          \"href\": \"\",\n" +
                "          \"icon\": \"fa fa-meetup\",\n" +
                "          \"target\": \"\",\n" +
                "          \"child\": [\n" +
                "            {\n" +
                "              \"title\": \"在校学员管理\",\n" +
                "              \"href\": \"employee/getEmployeeListHtml\",\n" +
                "              \"icon\": \"fa fa-list-alt\",\n" +
                "              \"target\": \"_self\"\n" +
                "            },{\n" +
                "              \"title\": \"毕业学员管理\",\n" +
                "              \"href\": \"employee/getGraduateEmployeeListHtml\",\n" +
                "              \"icon\": \"fa fa-list-alt\",\n" +
                "              \"target\": \"_self\"\n" +
                "            },{\n" +
                "              \"title\": \"学员调动记录\",\n" +
                "              \"href\": \"stutransfer/getStutransferListHtml\",\n" +
                "              \"icon\": \"fa fa-list-alt\",\n" +
                "              \"target\": \"_self\"\n" +
                "            }\n" +
                "          ]\n" +
                "        },{\n" +
                "          \"title\": \"考勤管理\",\n" +
                "          \"href\": \"\",\n" +
                "          \"icon\": \"fa fa-meetup\",\n" +
                "          \"target\": \"\",\n" +
                "          \"child\": [\n" +
                "            {\n" +
                "              \"title\": \"考勤记录\",\n" +
                "              \"href\": \"checkwork/getCheckworkListHtml\",\n" +
                "              \"icon\": \"fa fa-list-alt\",\n" +
                "              \"target\": \"_self\"\n" +
                "            },{\n" +
                "              \"title\": \"辅导管理\",\n" +
                "              \"href\": \"overtime/getOvertimeListHtml\",\n" +
                "              \"icon\": \"fa fa-list-alt\",\n" +
                "              \"target\": \"_self\"\n" +
                "            }\n" +
                "          ]\n" +
                "        },{\n" +
                "          \"title\": \"请假管理\",\n" +
                "          \"href\": \"\",\n" +
                "          \"icon\": \"fa fa-meetup\",\n" +
                "          \"target\": \"\",\n" +
                "          \"child\": [\n" +
                "            {\n" +
                "              \"title\": \"未批准列表\",\n" +
                "              \"href\": \"lea/getNoLeaListAllHtml\",\n" +
                "              \"icon\": \"fa fa-list-alt\",\n" +
                "              \"target\": \"_self\"\n" +
                "            },{\n" +
                "              \"title\": \"已批准列表\",\n" +
                "              \"href\": \"lea/getOnLeaListAllHtml\",\n" +
                "              \"icon\": \"fa fa-list-alt\",\n" +
                "              \"target\": \"_self\"\n" +
                "            },{\n" +
                "              \"title\": \"请假记录\",\n" +
                "              \"href\": \"lea/getLeaListAllHtml\",\n" +
                "              \"icon\": \"fa fa-list-alt\",\n" +
                "              \"target\": \"_self\"\n" +
                "            }\n" +
                "          ]\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"title\": \"班级管理\",\n" +
                "      \"href\": \"\",\n" +
                "      \"icon\": \"fa fa-home\",\n" +
                "      \"target\": \"_self\",\n" +
                "      \"child\": [\n" +
                "        {\n" +
                "          \"title\": \"班级\",\n" +
                "          \"href\": \"\",\n" +
                "          \"icon\": \"fa fa-meetup\",\n" +
                "          \"target\": \"\",\n" +
                "          \"child\": [\n" +
                "            {\n" +
                "              \"title\": \"班级管理\",\n" +
                "              \"href\": \"department/getDepartmentListHtml\",\n" +
                "              \"icon\": \"fa fa-list-alt\",\n" +
                "              \"target\": \"_self\"\n" +
                "            },{\n" +
                "              \"title\": \"职位管理\",\n" +
                "              \"href\": \"position/getPositionListHtml\",\n" +
                "              \"icon\": \"fa fa-list-alt\",\n" +
                "              \"target\": \"_self\"\n" +
                "            }\n" +
                "          ]\n" +
                "\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"title\": \"招聘\",\n" +
                "      \"href\": \"\",\n" +
                "      \"icon\": \"fa fa-home\",\n" +
                "      \"target\": \"_self\",\n" +
                "      \"child\": [\n" +
                "        {\n" +
                "          \"title\": \"招聘信息\",\n" +
                "          \"href\": \"\",\n" +
                "          \"icon\": \"fa fa-meetup\",\n" +
                "          \"target\": \"\",\n" +
                "          \"child\": [\n" +
                "            {\n" +
                "              \"title\": \"招聘管理\",\n" +
                "              \"href\": \"company/getCompanyListHtml\",\n" +
                "              \"icon\": \"fa fa-list-alt\",\n" +
                "              \"target\": \"_self\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"title\": \"招聘信息\",\n" +
                "              \"href\": \"company/getCompanyAndDeliveryListHtml\",\n" +
                "              \"icon\": \"fa fa-list-alt\",\n" +
                "              \"target\": \"_self\"\n" +
                "            },{\n" +
                "              \"title\": \"已投递信息\",\n" +
                "              \"href\": \"delivery/getDeliveryListHtml\",\n" +
                "              \"icon\": \"fa fa-list-alt\",\n" +
                "              \"target\": \"_self\"\n" +
                "            }\n" +
                "          ]\n" +
                "\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"title\": \"数据分析\",\n" +
                "      \"href\": \"\",\n" +
                "      \"icon\": \"fa fa-home\",\n" +
                "      \"target\": \"_self\",\n" +
                "      \"child\": [\n" +
                "        {\n" +
                "          \"title\": \"数据信息\",\n" +
                "          \"href\": \"\",\n" +
                "          \"icon\": \"fa fa-meetup\",\n" +
                "          \"target\": \"\",\n" +
                "          \"child\": [\n" +
                "            {\n" +
                "              \"title\": \"学员数据\",\n" +
                "              \"href\": \"company/getCompanyListHtml\",\n" +
                "              \"icon\": \"fa fa-list-alt\",\n" +
                "              \"target\": \"_self\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"title\": \"考勤数据\",\n" +
                "              \"href\": \"company/getCompanyAndDeliveryListHtml\",\n" +
                "              \"icon\": \"fa fa-list-alt\",\n" +
                "              \"target\": \"_self\"\n" +
                "            }\n" +
                "          ]\n" +
                "\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}";
    }




}
