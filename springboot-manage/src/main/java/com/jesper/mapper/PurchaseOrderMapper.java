package com.jesper.mapper;

import com.jesper.hftc.entity.PurchaseOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author 廖凡
 * @Date 2020/3/1 16:47
 */
@Mapper
public interface PurchaseOrderMapper {

    void insert(PurchaseOrder purchaseOrder);

    int count();

    List<PurchaseOrder> getList(@Param("purchaseOrder") PurchaseOrder purchaseOrder, @Param("start")int start,@Param("end") int end);

    int update(PurchaseOrder purchaseOrder);

    int updateFinisnPurchaseService(@Param("id") Integer id, @Param("state") Integer state);

    PurchaseOrder getById(@Param("id") Integer id);
}
