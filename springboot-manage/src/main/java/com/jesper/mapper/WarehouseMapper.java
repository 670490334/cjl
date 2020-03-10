package com.jesper.mapper;

import com.jesper.hftc.entity.Warehouse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author 廖凡
 * @Date 2020/2/19 15:05
 */
@Mapper
public interface WarehouseMapper {
    int instorage(Warehouse warehouse);

    int update(Warehouse warehouse);

    List<Warehouse> getList(Warehouse warehouse, @Param("start") Integer start,@Param("end") Integer end);

    Warehouse getById(Integer id);

    int getNumberById(Integer id);

    int count();
}
