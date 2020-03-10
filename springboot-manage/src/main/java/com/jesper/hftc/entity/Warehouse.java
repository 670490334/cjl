package com.jesper.hftc.entity;

import com.alibaba.fastjson.JSONObject;
import com.jesper.model.BaseObject;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 仓库
 *
 * @Author 廖凡
 * @Date 2020/2/15 16:08
 */
@Data

public class Warehouse extends BaseObject {

    private Integer id;

    private String productName;

    private Integer productId;
    //库存
    private Integer number;

    private BigDecimal amount;

    private BigDecimal totalMoney;

    private String productNumber;

    private String productSpecifications;
    //货位
    private String address;
    //单位
    private String unit;
    private Integer lossNumber;
    //入库数量
    private Integer updateNumber;
    private Date updateTime;
    private Date createTime;
    private String remark;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this).toString();
    }
}
