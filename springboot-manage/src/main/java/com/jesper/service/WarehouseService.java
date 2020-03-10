package com.jesper.service;

import com.jesper.hftc.entity.Warehouse;

import java.util.List;

/**
 * @Author 廖凡
 * @Date 2020/2/18 21:48
 */
public interface WarehouseService {
    Warehouse getById(Integer id);

    List<Warehouse> getList(Warehouse warehouse,Integer start,Integer end);

    void update(Warehouse warehouse);

    /**
     * 入库
     * @param warehouse
     * @return
     */
    void instorage(Warehouse warehouse);

    int count();
}
