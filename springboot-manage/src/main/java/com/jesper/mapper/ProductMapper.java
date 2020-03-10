package com.jesper.mapper;

import com.jesper.hftc.entity.Product;
import com.jesper.hftc.entity.PurchaseOrder;
import com.jesper.hftc.entity.Warehousemanage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author 廖凡
 * @Date 2020/2/17 22:41
 */
@Mapper
public interface ProductMapper {

    int insert(Product product);

    int delete(Integer id);

    int edit(Product product);

    Product getProductById(Integer id);

    List<Product> getProductList(Product product);

    List<Product> getProductByCategory(String cateGoryName,@Param("start") Integer start,@Param("end") Integer end);

    int count(Product product);

    int loss(Product product);

    int sale(Product product);

    int inStorage(Product product);

    List<Product> getSaleProductList(List<Integer> list);
}
