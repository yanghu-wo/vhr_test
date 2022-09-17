package com.study.vhr.entity;

import java.io.Serializable;

/**
 * @ClassName: JsonResult
 * @Author: LiuYun
 * @Description:
 * @Data: Create in 9:25 2022/9/3
 */
public class JsonResult implements Serializable {
    //异常代码
    protected String code;
    // 异常信息
    protected String msg;

    public JsonResult() {
        this.code = "200";
        this.msg = "操作成功";
    }

    public JsonResult(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "JsonResult{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
