package com.zk.cloudweb.util.exception;


import com.zk.cloudweb.util.exception.bean.BaseException;

/**
 * 用户信息异常类
 * 
 * @author xf
 */
public class UserException extends BaseException
{
    private static final long serialVersionUID = 1L;

    public UserException(String code, Object[] args)
    {
        super("user", code, args, null);
    }
}
