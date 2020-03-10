package com.jesper.hftc.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 采购订单
 * purchase_order
 * @Author 廖凡
 * @Date 2020/2/15 15:54
 */
@Data
public class PurchaseOrder implements Serializable {
    private Integer id;

    private String purchaseNumber;

    private Date createTime;

    private Date updateTime;

    private Date purchaseTime;

    private Integer productId;

    private String productName;

    private String supplierId;
    //数量

    private Integer inwarehouseId;

    private Integer number;

    private BigDecimal totalMoney;

    private BigDecimal amount;

    private Integer style;

    private Integer returnNumber;

    private Integer state;

    private String supplierName;

    private String warename;

    private String remark;

    private Date beginTime;

    private Date endTime;

    private String createTimeStr;

    private String purchaseTimeStr;
    @Override
    public String toString() {
        return JSONObject.toJSONString(this).toString();
    }
}
