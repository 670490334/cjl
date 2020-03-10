package com.jesper.hftc.config;

import com.alibaba.fastjson.JSONObject;

/**
 * @Author 廖凡
 * @Date 2020/2/19 15:54
 */
public class Result {
    private boolean success;
    private String message;
    private Integer code;
    private Object data;
    public Result(){
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static Result ok(Object data){
        Result result = new Result();
        result.setCode(Status.SUCCESS.getCode());
        result.setData(data);
        result.setSuccess(true);
        return result;
    }

    public static Result ok(){
        Result result = new Result();
        result.setCode(Status.SUCCESS.getCode());
        result.setSuccess(true);
        return result;
    }
    public Result(int code,String message){
        this.code = code;
        this.message = message;
        this.data = null;
    }

    public static Result ofMessage(int code,String message){
        return new Result(code,message);
    }
    public static Result notLogin(){
        Result result = new Result();
        result.setSuccess(false);
        result.setCode(Status.NOT_LOGIN.getCode());
        result.setMessage(Status.NOT_LOGIN.getStandardMessage());
        return result;
    }
    /**
     * 枚举
     */
    public enum Status {
        SUCCESS(200,"OK"),
        BAD_REQUEST(400,"Bad Request"),
        INTERNAL_SERVER_ERROR(500,"UnKonwn Internal"),
        NOT_SUPPORTED_OPERATON(40005,"Operation not supported"),
        NOT_LOGIN(50000,"Not Login");


        private int code;
        private String standardMessage;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getStandardMessage() {
            return standardMessage;
        }

        public void setStandardMessage(String standardMessage) {
            this.standardMessage = standardMessage;
        }

        Status(int code,String standardMessage){
            this.code = code;
            this.standardMessage = standardMessage;
        }
    }

    @Override
    public String toString() {

        return JSONObject.toJSONString(this).toString();
    }
}
