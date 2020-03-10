package com.jesper.mapper;

import com.jesper.hftc.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author 廖凡
 * @Date 2020/2/18 15:54
 */
@Mapper
public interface CategoryMapper {

    Category getById(@Param("id") Integer id);

    List<Category> categoryList();

    int delete(@Param("id") Integer id);

    int insert(Category category);

    int update(Category category);


}
