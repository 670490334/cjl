package com.jesper.hftc.entity;

import com.alibaba.fastjson.JSONObject;
import com.jesper.hftc.config.BaseObject;
import lombok.Data;

import java.util.Date;

/**
 * 仓库管理
 * @Author 廖凡
 * @Date 2020/2/28 15:57
 */
@Data
public class Warehousemanage extends BaseObject {
    private Integer id;
    private Integer productId;
    private String wareName;
    private String address;
    private Integer number;
    private Integer lossNumber;
    private Integer saleNumber;
    private Date createTime;
    private Date updateTime;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this).toString();
    }
}
