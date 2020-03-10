package com.jesper.hftc.entity;

import com.alibaba.fastjson.JSONObject;
import com.jesper.model.BaseObject;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author 廖凡
 * @Date 2020/2/15 15:58
 */
@Data

public class SalesOrder extends BaseObject implements Serializable {
    private String id;
    private String orderName;
    private String orderNumber;
    private String customerName;
    private String customerId;
    private Date createTime;
    private Date sellTime;
    private String productName;
    private String productNumber;
    private Integer productId;

    //出售数量
    private Integer number;

    private BigDecimal amount;

    //是否退货 1 0
    private Integer style;

    //交易状态
    private Integer status;
    //退货数量
    private Integer salesReturnNumber;

    private String remark;

    private String madeBy;

    private String passBy;

    private String jgkh;

    private String zy;

    private Integer ckcuStatus;//仓库出库：1：0

    private String sellTimeStr ;

    private String minOrderTimeStr;

    private String maxOrderTimeStr;

    private Date minOrderTime;
    private Date maxOrderTime;


    private List<SalesOrderChild> childList;
    @Override
    public String toString() {
        return JSONObject.toJSONString(this).toString();
    }
}
