package com.jesper.service.impl;

import com.jesper.hftc.config.MyConfig;
import com.jesper.hftc.entity.ApiLog;
import com.jesper.hftc.entity.Warehouse;
import com.jesper.mapper.ApiLogMapper;
import com.jesper.mapper.WarehouseMapper;
import com.jesper.service.WarehouseService;
import com.sun.tools.corba.se.idl.ExceptionEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.misc.Cache;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 仓库管理
 * @Author 廖凡
 * @Date 2020/2/19 15:04
 */
@Service
@SuppressWarnings("all")
public class WarehouseServiceImpl implements WarehouseService {


    @Autowired
    private ApiLogMapper apiLogMapper;
    @Autowired
    private WarehouseMapper warehouseMapper;
    @Override
    public Warehouse getById(Integer id) {
        Warehouse warehouse = new Warehouse();
        try {
            warehouse = warehouseMapper.getById(id);
        }catch (Exception e){
            System.out.println("获取仓库信息Id为"+id+"的信息失败");
        }
        return warehouse;
    }

    @Override
    public List<Warehouse> getList(Warehouse warehouse,Integer start,Integer end) {

        List<Warehouse> list = new ArrayList<>();
        try {
            list = warehouseMapper.getList(warehouse,start,end);
        }catch (Exception e){
            System.out.println("获取仓库List失败:"+e.getMessage());
        }
        return list;
    }

    @Override
    public void update(Warehouse warehouse) {
        ApiLog apiLog = new ApiLog();
        apiLog.setCreateTime(new Date());
        apiLog.setMethodName("WarehouseService.update()");
        apiLog.setParams(warehouse.toString());
        try {
            warehouseMapper.update(warehouse);
            apiLog.setSuccess(MyConfig.SUCCESS);
        }catch (Exception e){
            apiLog.setSuccess(MyConfig.FAILD);
            apiLog.setMsg(e.getMessage().toString().length()>50?e.getMessage().toString().substring(0,49):e.getMessage().toString());
            System.out.println("warehouse更新失败："+e.getMessage());
        }finally {
            apiLogMapper.save(apiLog);
        }
    }

    @Override
    public void instorage(Warehouse warehouse) {
        ApiLog apiLog = new ApiLog();
        apiLog.setCreateTime(new Date());
        apiLog.setMethodName("WarehouseService.instorage()");
        apiLog.setParams("入库操作：损耗"+warehouse.getLossNumber()+";入库："+warehouse.getNumber());
        int status = 0;
        try {
            Warehouse data = warehouseMapper.getById(warehouse.getId());
            apiLog.setParams(apiLog.getParams()+";;更新前：number="+warehouse.getNumber()+";loss_number="+warehouse.getLossNumber());
            //入库
            if (warehouse.getNumber()!=0){
                data.setNumber(data.getNumber()+warehouse.getNumber());
                status = 1;
            }
            //损耗
            if (warehouse.getLossNumber()!=0){
                data.setLossNumber(data.getLossNumber()+warehouse.getLossNumber());
                status = 1;
            }
            if (status == 1){
                data.setUpdateTime(new Date());
                warehouseMapper.instorage(data);
                System.out.println("更新成功");
            }else {
                System.out.println("未做任何操作");
            }
            apiLog.setSuccess(MyConfig.SUCCESS);
        }catch (Exception e){
            apiLog.setSuccess(MyConfig.FAILD);
            apiLog.setMsg(e.getMessage().toString().length()>50?e.getMessage().toString().substring(0,49):e.getMessage().toString());
            System.out.println("入库失败："+e.getMessage());
        }finally {
            if (status == 0){
                System.out.println("未做任何操作");
            }else {
                apiLogMapper.save(apiLog);
            }
        }
    }

    @Override
    public int count() {
        int count = 0;
        try {
            count =  warehouseMapper.count();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return count;
    }
}
