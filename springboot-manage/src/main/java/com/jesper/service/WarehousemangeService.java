package com.jesper.service;

import com.jesper.hftc.config.Result;
import com.jesper.hftc.entity.Warehousemanage;

import java.util.List;

/**
 * 仓库管理
 * @Author 廖凡
 * @Date 2020/2/28 16:26
 */
public interface WarehousemangeService {

    Result add(Warehousemanage warehousemanage);

    Result edit(Warehousemanage warehousemanage);

    Warehousemanage getById(Integer id);

    Result loss(Integer id, Integer lossNumber);

    Result sale(Integer id, Integer saleNumber);

    Result inStorage(Integer productId,Integer id,Integer number);

    List<Warehousemanage> getList(Warehousemanage warehousemanage, Integer start, Integer end);

    List<Warehousemanage> getListByProductId(Integer productId);

    List<Warehousemanage> getInwarehousemanageList(Integer id);

    int count();
}
