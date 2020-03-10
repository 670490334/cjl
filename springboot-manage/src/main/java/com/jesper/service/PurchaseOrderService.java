package com.jesper.service;

import com.jesper.hftc.config.Result;
import com.jesper.hftc.entity.PurchaseOrder;
import com.jesper.hftc.entity.Warehousemanage;

import java.util.List;

/**
 * @Author 廖凡
 * @Date 2020/3/1 16:45
 */
public interface PurchaseOrderService {
    int count();

    List<PurchaseOrder> getList(PurchaseOrder purchaseOrder, int start, int end);

    boolean update(PurchaseOrder purchaseOrder);

    Result updateFinisnPurchaseService(Integer id, Integer state);

    Result add (PurchaseOrder purchaseOrder);

    PurchaseOrder getById(Integer id);

}
