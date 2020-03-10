package com.jesper.service.impl;

import com.jesper.hftc.config.MyConfig;
import com.jesper.hftc.entity.ApiLog;
import com.jesper.hftc.entity.Category;
import com.jesper.mapper.ApiLogMapper;
import com.jesper.mapper.CategoryMapper;
import com.jesper.mapper.ItemCategoryMapper;
import com.jesper.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author 廖凡
 * @Date 2020/2/18 15:51
 */
@Service
@SuppressWarnings("all")
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private ApiLogMapper apiLogMapper;
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public void add(Category category) {
        ApiLog apiLog = new ApiLog();
        apiLog.setCreateTime(new Date());
        apiLog.setMethodName("CategoryService.add()");
        apiLog.setParams(category.toString());
        try {
            if (category.getId()!=null){
                categoryMapper.update(category);
            }else {
                categoryMapper.insert(category);
            }
            apiLog.setSuccess(MyConfig.SUCCESS);
        }catch (Exception e){
            apiLog.setSuccess(MyConfig.FAILD);
            apiLog.setMsg(e.getMessage().toString().length()>50?e.getMessage().toString().substring(0,49):e.getMessage().toString());
            System.out.println("新增商品分类："+category.getName()+";失败");
        }
    }

    @Override
    public List<Category> getCategoryList() {
        List<Category> categoryList = new ArrayList<>();
        try {
            categoryList = categoryMapper.categoryList();
        }catch (Exception e){
            System.out.println("getCategoryList（）错误信息："+e.getMessage());
        }
        return categoryList;
    }

    @Override
    public void delete(Integer id) {
        ApiLog apiLog = new ApiLog();
        apiLog.setCreateTime(new Date());
        apiLog.setMethodName("CategoryService.delete()");
        apiLog.setParams(id.toString());
        try {
            categoryMapper.delete(id);
            apiLog.setSuccess(MyConfig.SUCCESS);
        }catch (Exception e){
            apiLog.setSuccess(MyConfig.FAILD);
            apiLog.setMsg(e.getMessage().toString().length()>50?e.getMessage().toString().substring(0,49):e.getMessage().toString());
        }finally {
            apiLogMapper.save(apiLog);
        }
    }

    @Override
    public void edit(Category category) {
        ApiLog apiLog = new ApiLog();
        apiLog.setCreateTime(new Date());
        apiLog.setMethodName("CategoryService.edit()");
        apiLog.setParams(category.toString());
        try {
            categoryMapper.update(category);
            apiLog.setSuccess(MyConfig.SUCCESS);
        }catch (Exception e){
            apiLog.setSuccess(MyConfig.FAILD);
            apiLog.setMsg(e.getMessage().toString().length()>50?e.getMessage().toString().substring(0,49):e.getMessage().toString());
        }finally {
            apiLogMapper.save(apiLog);
        }
    }
}
