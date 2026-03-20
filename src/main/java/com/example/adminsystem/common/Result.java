package com.example.adminsystem.common;

public class Result {
    private int code;
    private String msg;
    private Object data;

    public Result() {}

    public Result(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static Result success(Object data) {
        return new Result(200, "操作成功", data);
    }

    public static Result success() {
        return new Result(200, "操作成功", null);
    }

    public static Result error(int code, String msg) {
        return new Result(code, msg, null);
    }

    public static Result error(String msg) {
        return new Result(400, msg, null);
    }

    public static Result unauth() {
        return new Result(401, "未登录或权限不足", null);
    }

    public static Result serverError() {
        return new Result(500, "服务器错误", null);
    }

    // getter和setter
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}