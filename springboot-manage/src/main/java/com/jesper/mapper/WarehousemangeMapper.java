package com.jesper.mapper;

import com.jesper.hftc.entity.Warehousemanage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author 廖凡
 * @Date 2020/2/28 16:01
 */
@Mapper
public interface WarehousemangeMapper {

    int insert(Warehousemanage warehousemanage);

    int edit(Warehousemanage warehousemanage);

    Warehousemanage getById(Integer id);

    int loss(Warehousemanage warehousemanage);

    int sale(Warehousemanage warehousemanage);

    int inStorage(Warehousemanage warehousemanage);

    List<Warehousemanage> getList(@Param("wareName") String wareName, @Param("start") Integer start, @Param("end") Integer end);

    List<Warehousemanage> getListByProductId(Integer productId);

    List<Warehousemanage> getInwarehousemanageList(Integer id);

    int count();

    int addProduct(@Param("productId") Integer productId,@Param("id") Integer id);
}
