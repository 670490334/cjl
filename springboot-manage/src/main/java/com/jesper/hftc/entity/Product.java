package com.jesper.hftc.entity;

import com.alibaba.fastjson.JSONObject;
import com.jesper.model.BaseObject;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author 廖凡
 * @Date 2020/2/15 16:12
 */
@Data

public class Product extends BaseObject implements Serializable {

    private Integer id;

    private String productName;

    private String productNumber;

    private String color;
    //规格型号
    private String specifications;

    private String unit;

    private BigDecimal price;

    private Integer saleNumber;

    private Integer lossNumber;

    private Integer inventoryNumber;

    private String productCategoryName;

    private String remark;

    private Date createTime;

    private Date updateTime;

    private BigDecimal minPrice;

    private BigDecimal maxPrice;


    @Override
    public String toString() {
        return JSONObject.toJSONString(this).toString();
    }
}
