package cn.xiaoguo.exception;

import cn.xiaoguo.enums.AppHttpCodeEnum;

/**
 * @ClassName SystemException
 * @Description   系统异常
 * @Author 小果
 * @Date 2024/6/7 17:43
 * @Version 1.0
 */
public class SystemException extends RuntimeException{

    private int code;

    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public SystemException(AppHttpCodeEnum httpCodeEnum) {
        super(httpCodeEnum.getMsg());
        this.code = httpCodeEnum.getCode();
        this.msg = httpCodeEnum.getMsg();
    }

}