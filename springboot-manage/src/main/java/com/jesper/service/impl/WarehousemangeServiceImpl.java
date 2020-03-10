package com.jesper.service.impl;

import com.jesper.hftc.config.MyConfig;
import com.jesper.hftc.config.Result;
import com.jesper.hftc.entity.ApiLog;
import com.jesper.hftc.entity.Warehousemanage;
import com.jesper.mapper.ApiLogMapper;
import com.jesper.mapper.WarehousemangeMapper;
import com.jesper.service.WarehousemangeService;
import com.jesper.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author 廖凡
 * @Date 2020/2/28 16:28
 */
@SuppressWarnings("all")
@Service
public class WarehousemangeServiceImpl implements WarehousemangeService {

    @Autowired
    private ApiLogMapper apiLogMapper;
    @Autowired
    private WarehousemangeMapper warehousemangeMapper;

    @Override
    public Result add(Warehousemanage warehousemanage) {
        ApiLog apiLog = new ApiLog();
        apiLog.setCreateTime(new Date());
        apiLog.setMethodName("WarehousemangeService.add()");
        apiLog.setParams(warehousemanage.toString().length()>1000?warehousemanage.toString().substring(0,999):warehousemanage.toString());
        try {
            warehousemanage.setUpdateTime(new Date());
            //更新编辑
            if(warehousemanage.getId()!=null){
                warehousemangeMapper.edit(warehousemanage);
            }else {
                //插入
                warehousemanage.setCreateTime(new Date());
                warehousemangeMapper.insert(warehousemanage);
            }
            apiLog.setSuccess(MyConfig.SUCCESS);
            return Result.ok();
        }catch (Exception e){
            apiLog.setSuccess(MyConfig.FAILD);
            apiLog.setMsg(e.getMessage().length()>1000?e.getMessage().substring(0,999):e.getMessage());
            System.out.println(":----错误信息："+e.getMessage());
            return Result.ofMessage(400,e.getMessage());
        }finally {
            apiLogMapper.save(apiLog);
        }
    }

    @Override
    public Result edit(Warehousemanage warehousemanage) {
        ApiLog apiLog = new ApiLog();
        apiLog.setCreateTime(new Date());
        apiLog.setMethodName("WarehousemangeService.edit()");
        apiLog.setParams(warehousemanage.toString().length()>1000?warehousemanage.toString().substring(0,999):warehousemanage.toString());
        try {
            warehousemanage.setUpdateTime(new Date());
            warehousemangeMapper.edit(warehousemanage);
            apiLog.setSuccess(MyConfig.SUCCESS);
            return Result.ok();
        }catch (Exception e){
            apiLog.setSuccess(MyConfig.FAILD);
            apiLog.setMsg(e.getMessage().length()>1000?e.getMessage().substring(0,999):e.getMessage());
            System.out.println(":----错误信息："+e.getMessage());
            return Result.ofMessage(400,e.getMessage());
        }finally {
            apiLogMapper.save(apiLog);
        }
    }

    @Override


    public Warehousemanage getById(Integer id) {
        Warehousemanage warehousemanage = null;
        try {
            warehousemanage = warehousemangeMapper.getById(id);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return warehousemanage;
    }

    @Override
    public Result loss(Integer id, Integer lossNumber) {
        ApiLog apiLog = new ApiLog();
        apiLog.setCreateTime(new Date());
        apiLog.setMethodName("WarehousemangeService.loss()");
        apiLog.setParams("id:"+id+";lossNumber"+lossNumber);
        try {
            //查询原本仓库信息
            Warehousemanage warehousemanage = warehousemangeMapper.getById(id);
            //库存
            Integer baseLossNumber = warehousemanage.getLossNumber();
            if (baseLossNumber == null) baseLossNumber =0;
            Integer baseNumber = warehousemanage.getNumber();
            if (baseNumber == null) baseNumber = 0;
            //更新库存
            warehousemanage.setLossNumber(baseLossNumber+lossNumber);
            if (baseNumber < lossNumber) return Result.ofMessage(400,"库存不足");
            warehousemanage.setNumber(baseNumber-lossNumber);
            warehousemangeMapper.loss(warehousemanage);
            apiLog.setSuccess(MyConfig.SUCCESS);
            return Result.ok();
        }catch (Exception e){
            apiLog.setSuccess(MyConfig.FAILD);
            apiLog.setMsg(e.getMessage().length()>1000?e.getMessage().substring(0,999):e.getMessage());
            System.out.println(":----错误信息："+e.getMessage());
            return Result.ofMessage(400,e.getMessage());
        }finally {
            apiLogMapper.save(apiLog);
        }


    }

    @Override
    public Result sale(Integer id, Integer saleNumber) {
        ApiLog apiLog = new ApiLog();
        apiLog.setCreateTime(new Date());
        apiLog.setMethodName("WarehousemangeService.sale()");
        apiLog.setParams("id:"+id+";saleNumber"+saleNumber);
        try {
            //查询原本仓库信息
            Warehousemanage warehousemanage = warehousemangeMapper.getById(id);
            //库存
            Integer baseSaleNumber = warehousemanage.getSaleNumber();
            if (baseSaleNumber == null) baseSaleNumber =0;
            Integer baseNumber = warehousemanage.getNumber();
            if (baseNumber == null) baseNumber = 0;
            //更新库存
            warehousemanage.setLossNumber(baseSaleNumber+saleNumber);
            if (baseNumber<saleNumber) return Result.ofMessage(400,"库存不足");
            warehousemanage.setNumber(baseNumber-saleNumber);
            warehousemangeMapper.sale(warehousemanage);
            apiLog.setSuccess(MyConfig.SUCCESS);
            return Result.ok();
        }catch (Exception e){
            apiLog.setSuccess(MyConfig.FAILD);
            apiLog.setMsg(e.getMessage().length()>1000?e.getMessage().substring(0,999):e.getMessage());
            System.out.println(":----错误信息："+e.getMessage());
            return Result.ofMessage(400,e.getMessage());
        }finally {
            apiLogMapper.save(apiLog);
        }
    }

    @Override
    public Result inStorage(Integer productId,Integer id,Integer number) {
        ApiLog apiLog = new ApiLog();
        apiLog.setCreateTime(new Date());
        apiLog.setMethodName("WarehousemangeService.inStorage()");
        apiLog.setParams("id:"+id+";number"+number);
        try {
            //查询原本仓库信息
            Warehousemanage warehousemanage = warehousemangeMapper.getById(id);
            if (warehousemanage.getProductId()==null || warehousemanage.getProductId() == 0){
                warehousemangeMapper.addProduct(productId,id);
            }
            //库存
            Integer baseNumber = warehousemanage.getNumber();
            if (baseNumber == null) baseNumber = 0;
            //更新库存
            warehousemanage.setNumber(baseNumber+number);
            warehousemangeMapper.inStorage(warehousemanage);
            apiLog.setSuccess(MyConfig.SUCCESS);
            return Result.ok();
        }catch (Exception e){
            apiLog.setSuccess(MyConfig.FAILD);
            apiLog.setMsg(e.getMessage().length()>1000?e.getMessage().substring(0,999):e.getMessage());
            System.out.println(":----错误信息："+e.getMessage());
            return Result.ofMessage(400,e.getMessage());
        }finally {
            apiLogMapper.save(apiLog);
        }
    }

    @Override
    public List<Warehousemanage> getList(Warehousemanage warehousemanage, Integer start, Integer end) {
        List<Warehousemanage> list = new ArrayList<>();
        try {
            list = warehousemangeMapper.getList(warehousemanage.getWareName(),start,end);
        }catch (Exception e){
            System.out.println("错误信息："+e.getMessage());
        }
        for (Warehousemanage w:
             list) {
            w.setCreateTimeStr(DateUtil.getDateStr(w.getCreateTime()));
            w.setUpdateTimeStr(DateUtil.getDateStr(w.getUpdateTime()));
        }
        return list;
    }

    @Override
    public List<Warehousemanage> getListByProductId(Integer productId) {
        List<Warehousemanage> list = new ArrayList<>();
        try {
            list = warehousemangeMapper.getListByProductId(productId);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return list;
    }

    @Override
    public List<Warehousemanage> getInwarehousemanageList(Integer id) {
        List<Warehousemanage> list = new ArrayList<>();
        try {
            list = warehousemangeMapper.getInwarehousemanageList(id);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return list;
    }

    @Override
    public int count() {
        return warehousemangeMapper.count();
    }
}
