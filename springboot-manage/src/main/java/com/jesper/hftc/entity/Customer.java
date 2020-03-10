package com.jesper.hftc.entity;

import com.alibaba.fastjson.JSONObject;
import com.jesper.model.BaseObject;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 客户信息
 * @Author 廖凡
 * @Date 2020/2/15 15:33
 */
@Data
public class Customer extends BaseObject implements Serializable {
    private String id;
    private String name;
    private String tel;
    private String email;
    private String level;
    private BigDecimal costMoney;
    private String remark;
    @Override
    public String toString() {
        return JSONObject.toJSONString(this).toString();
    }

}
