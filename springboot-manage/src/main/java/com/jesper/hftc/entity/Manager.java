package com.jesper.hftc.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.io.Serializable;

/**
 * user类
 * @Author 凡
 * @Date 2020/2/15 15:52
 */
@Data
public class Manager implements Serializable {
    private String id;
    private String name;
    private String username;
    private String password;
    private String permission;
    @Override
    public String toString() {
        return JSONObject.toJSONString(this).toString();
    }
}
