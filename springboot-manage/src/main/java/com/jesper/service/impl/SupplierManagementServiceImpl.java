package com.jesper.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.jesper.hftc.config.MyConfig;
import com.jesper.hftc.entity.ApiLog;
import com.jesper.hftc.entity.SalesOrder;
import com.jesper.hftc.entity.SupplierManagement;
import com.jesper.mapper.ApiLogMapper;
import com.jesper.mapper.SupplierManagementMapper;
import com.jesper.service.SupplierManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @Author 廖凡
 * @Date 2020/2/24 22:17
 */
@Service
@SuppressWarnings("all")
public class SupplierManagementServiceImpl implements SupplierManagementService {

    @Autowired
    private ApiLogMapper apiLogMapper;
    @Autowired
    private SupplierManagementMapper supplierManagementMapper;
    @Override
    public SupplierManagement getById(String id) {
        return null;
    }

    @Override
    public int count() {
        int count = 0;
        try {
            count = supplierManagementMapper.count();
        }catch (Exception e ){
            System.out.println("SupplierManagementService.():错误信息："+e.getMessage());
        }
        return count;
    }

    @Override
    public List<SupplierManagement> list() {
        List<SupplierManagement> list = new ArrayList<>();
        try {
            list =  supplierManagementMapper.list();
        }catch (Exception e){
            System.out.println("SupplierManagementService.getList():"+e.getMessage());
        }
        return list;
    }

    @Override
    public List<SupplierManagement> getList(Integer start, Integer end) {
        List<SupplierManagement> list = new ArrayList<>();
        try {
            list =  supplierManagementMapper.getList(start,end);
        }catch (Exception e){
            System.out.println("SupplierManagementService.getList():"+e.getMessage());
        }
        return list;
    }

    @Override
    public boolean add(SupplierManagement supplierManagement) {
        ApiLog apiLog = new ApiLog();
        apiLog.setCreateTime(new Date());
        apiLog.setMethodName("SupplierManagementService.add()");
        apiLog.setParams(JSONObject.toJSONString(supplierManagement));
        try {
            if (StringUtils.isEmpty(supplierManagement.getId())){
                supplierManagement.setId(UUID.randomUUID().toString());
                supplierManagement.setCrateTime(new Date());
                supplierManagementMapper.add(supplierManagement);
            }else {
                supplierManagementMapper.update(supplierManagement);
            }
            apiLog.setSuccess(MyConfig.SUCCESS);
            return true;
        }catch (Exception e){
            apiLog.setSuccess(MyConfig.FAILD);
            apiLog.setMsg(e.getMessage().toString().length()>1000?e.getMessage().toString().substring(0,999):e.getMessage().toString());
            System.out.println(e.getMessage());
            return false;
        }finally {
            supplierManagement.setRemark("");
            apiLog.setParams(JSONObject.toJSONString(supplierManagement));
            apiLogMapper.save(apiLog);
        }


    }

    @Override
    public boolean delete(String id) {
        ApiLog apiLog = new ApiLog();
        apiLog.setCreateTime(new Date());
        apiLog.setMethodName("SupplierManagementService.delete()");
        apiLog.setParams("id");
        try {
            supplierManagementMapper.delete(id);
            apiLog.setSuccess(MyConfig.SUCCESS);
            return true;
        }catch (Exception e){
            apiLog.setSuccess(MyConfig.FAILD);
            apiLog.setMsg(e.getMessage().toString().length()>1000?e.getMessage().toString().substring(0,999):e.getMessage().toString());
            System.out.println(e.getMessage());
            return false;
        }finally {
            apiLogMapper.save(apiLog);
        }
    }
}
