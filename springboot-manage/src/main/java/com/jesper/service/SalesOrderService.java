package com.jesper.service;

import com.alibaba.fastjson.JSONObject;
import com.jesper.hftc.config.Result;
import com.jesper.hftc.entity.Product;
import com.jesper.hftc.entity.SalesOrder;
import com.jesper.hftc.entity.SalesOrderChild;

import java.util.List;

/**
 * @Author 廖凡
 * @Date 2020/2/19 19:47
 */
public interface SalesOrderService {
    List<SalesOrder> getOrdersByCustomerId(SalesOrder salesOrder);

    int count(SalesOrder salesOrder);

    List<SalesOrder> getList(SalesOrder salesOrder);

    Result saleProduct(String ids);

    Result saleForm();

    Result saleFormCreate(JSONObject jsonObject);

    SalesOrder getSaleFormById(String id);

    void clean();

//    void ckProduct(String id);

    List<SalesOrderChild> getProduct(String id);

    Result saleOrderChildCk(Integer id, Integer warehouseId, Integer saleNumber);

    void updateCkStatus(String id);
}
