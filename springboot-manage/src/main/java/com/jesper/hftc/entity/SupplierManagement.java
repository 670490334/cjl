package com.jesper.hftc.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;


import java.util.Date;

/**
 * 供应商管理
 *
 * @Author 廖凡
 * @Date 2020/2/15 16:05
 */
@Data
public class SupplierManagement {

    private String id;

    private Date crateTime;

    //供应商编号
    private String number;

    private String name;
    private String shortName;

    private String address;

    private String tel;

    private String linkman;

    private String email;

    private String remark;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this).toString();
    }
}
