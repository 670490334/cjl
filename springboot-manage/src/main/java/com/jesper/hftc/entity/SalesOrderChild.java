package com.jesper.hftc.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 出库单中的商品列表
 * @Author 廖凡
 * @Date 2020/3/4 1:28
 */
@Data
public class SalesOrderChild {
    private Integer id;

    private Integer productId;

    private String parentId;

    private Integer number ;

    private BigDecimal amount;

    private String jianshu;

    private String remark;

    private Product product;

    private String productName;

    private Integer ckStatus;

    private Integer ckNumber;

    private List<Warehousemanage> warehousemanageList;
    public SalesOrderChild(){

    }

    public SalesOrderChild(String parentId){
        this.parentId = parentId;
    }
}
