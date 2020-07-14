package com.zk.cloudweb.util;

import com.zk.cloudweb.util.Enum.ResultEnum;
import lombok.Data;

@Data
public class Result<T> {
    /*返回码*/
    private Integer code;
    private Integer count;
    /*返回消息*/
    private String msg;
    private boolean success;
    private T data;
    private T data2;
    public Result(ResultEnum resultEnum, T data, Boolean success) {
        this(resultEnum);
        this.data = data;
        this.success = success;
    }
    public Result(ResultEnum resultEnum, T data, T data2 , Boolean success) {
        this(resultEnum);
        this.data = data;
        this.data2 = data2;
        this.success = success;
    }
    public Result(ResultEnum resultEnum) {
        this.code = resultEnum.getCode();
        this.msg = resultEnum.getMsg();
    }

    public Result(ResultEnum commonNull, boolean b) {
        this(commonNull);
        this.success = b;
    }

}
