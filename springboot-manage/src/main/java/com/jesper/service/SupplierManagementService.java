package com.jesper.service;

import com.jesper.hftc.entity.SupplierManagement;

import java.util.List;

/**
 * @Author 廖凡
 * @Date 2020/2/24 22:07
 */
public interface SupplierManagementService {
    SupplierManagement getById(String id);

    int count();

    List<SupplierManagement> getList(Integer start, Integer end);

    List<SupplierManagement> list();
    boolean add(SupplierManagement supplierManagement);

    boolean delete(String id);
}
