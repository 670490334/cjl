package com.jesper;


import com.jesper.hftc.entity.Product;
import com.jesper.mapper.ProductMapper;
import com.jesper.model.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by jiangyunxiong on 2018/3/27.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class HelloWorld {

    @Autowired
    private ProductMapper productMapper;

    @Test
    public void testw(){
//        for (int i = 0;i<30; i++){
//            Product product = new Product();
//            product.setPrice(new BigDecimal(20));
//            product.setColor("红色");
//            product.setProductName("瓷砖"+i+"号");
//            product.setUnit("元");
//            product.setProductNumber("HF20200226"+i);
//            product.setRemark("测试数据");
//            product.setSpecifications("规格型号"+i);
//            product.setProductCategoryName("大瓷砖");
//            productMapper.insert(product);
//        }
        List<Integer> list = new ArrayList<>();
        list.add(6);
        list.add(5);
        list.add(8);
        List<Product> saleProductList = productMapper.getSaleProductList(list);
        for (Product p:saleProductList
             ) {
            System.out.println(p.toString());
        }
    }

    @Test
   public void test(){
        //获取当前日期

        Date today = new Date();


        //获取三十天前日期
        Calendar theCa = Calendar.getInstance();
        theCa.setTime(today);
        theCa.add(theCa.DATE, -31);//最后一个数字30可改，30天的意思
        Date start = theCa.getTime();
        System.out.println(start);
   }
   @Test
   public void test1(){
       Date date = new Date();
       date.setTime(1523635200000l);
       System.out.println(date);
   }

   @Test
   public void test2(){
       long a =0;
       double b =1;
       double c = b/a;
       System.out.println(c);
   }

}
