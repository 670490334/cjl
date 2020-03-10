package com.jesper.mapper;

import com.jesper.hftc.entity.SalesOrderChild;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author 廖凡
 * @Date 2020/3/6 17:26
 */
@Mapper
public interface SalesOrderChildMapper {
    void save(List<SalesOrderChild> list);

    List<SalesOrderChild> getByParentId(String parentId);

    SalesOrderChild getById(Integer id);

    void ck(SalesOrderChild salesOrderChild);
}
