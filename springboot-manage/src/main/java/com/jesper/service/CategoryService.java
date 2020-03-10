package com.jesper.service;

import com.jesper.hftc.entity.Category;

import java.util.List;

/**
 * @Author 廖凡
 * @Date 2020/2/17 22:37
 */
public interface CategoryService {
    void add(Category category);

    List<Category> getCategoryList();

    void delete(Integer id);

    void edit(Category category);
}
