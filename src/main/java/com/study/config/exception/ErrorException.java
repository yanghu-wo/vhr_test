package com.study.config.exception;

/**
 * @ClassName: ErrorException
 * @Author: LiuYun
 * @Description:
 * @Data: Create in 9:30 2022/9/3
 */
public class ErrorException extends RuntimeException{
    private static final long serialVersionUID = -7480022450501760611L;

    /**
     * 异常码
     */
    private String code;
    /**
     * 异常提示信息
     */
    private String message;

    public ErrorException(ExceptionMsgEnum exceptionMsgEnum) {
        this.code = exceptionMsgEnum.getCode();
        this.message = exceptionMsgEnum.getMsg();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "BusinessErrorException{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
