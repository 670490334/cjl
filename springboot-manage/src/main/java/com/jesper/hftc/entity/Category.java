package com.jesper.hftc.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * @Author 廖凡
 * @Date 2020/2/17 22:24
 */
@Data
public class Category {
    private Integer id;
    private String name;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this).toString();
    }
}
