package com.jesper.mapper;

import com.jesper.hftc.entity.SupplierManagement;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 供应商
 * @Author 廖凡
 * @Date 2020/2/24 21:27
 */
@Mapper
public interface SupplierManagementMapper {
    SupplierManagement getById(String id);

    List<SupplierManagement> getList(@Param("start") Integer start, @Param("end")Integer end);

    int delete(String id);

    int update(SupplierManagement supplierManagement);

    int add(SupplierManagement supplierManagement);

    int count();

    List<SupplierManagement> list();
}
