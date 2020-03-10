package com.jesper.hftc.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * inventory_records
 * 出入库记录
 * @Author 廖凡
 * @Date 2020/2/15 15:48
 */
@Data
public class InventoryRecords implements Serializable {
    private Integer id;

    private Date createTime;

    private Integer inProductId;

    private Integer inProductNumber;

    private Integer style;
    @Override
    public String toString() {
        return JSONObject.toJSONString(this).toString();
    }
}
