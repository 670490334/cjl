package com.jesper.service;

import com.jesper.hftc.config.Result;
import com.jesper.hftc.entity.Category;
import com.jesper.hftc.entity.Product;

import java.util.List;

/**
 * @Author 廖凡
 * @Date 2020/2/17 22:33
 */
public interface ProductService {
    void add(Product product);

    void edit(Product product);

    void delete(Integer id);

    Product getProduct(Integer id);

    List<Product> getProductListBy(String categoryName,Integer pageCurrent,Integer pageSize);

    List<Product> getProductList(Product product);

    int count(Product product);

    Result loss(Integer id, Integer lossNumber, Integer warehouseId);

    Result sale(Integer id,Integer saleNumber,Integer warehouseId);

    Result inStorage(Integer id,Integer inNumber,Integer warehouseId);

}
