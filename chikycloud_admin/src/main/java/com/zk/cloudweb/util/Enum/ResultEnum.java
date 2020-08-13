package com.zk.cloudweb.util.Enum;

/**
 * @Author: LiXiang
 * @Description :
 * @Date: Created in 2018/9/9 20:21
 */
public enum ResultEnum {
    /*成功*/
    OK(0,"成功"),//这个参数不是默认成功参数，是layui列表需要的参数
    SUCCESS(200,"操作成功"),
    NO(1,"登录失败"),
    COMMON_NULL(101,"字符为空"),
    PARAMETER_ERROR(400, "参数错误"),
    UNSESSION_ERR(401,"登录超时"),
    UNKONW_ERR(500,"未知异常"),
    UNPOWER_ERR(507,"权限不足，访问失败"),
    INMACHINE_ERR(470,"设备已添加，请勿重复添加"),
    REPEATEDREQUEST(301,"邮箱验证码重复请求"),
    AUTHCODE_ERROR(302,"验证码不匹配"),
    NOAUTHCODE_ERROR(303,"未发送验证码或者验证码已过期"),
    SIGNUP_ERROR(304,"该用户已注册，请使用其他邮箱进行注册")
    ;


    private Integer code;

    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
