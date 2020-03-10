package com.jesper.service.impl;

import com.jesper.hftc.config.MyConfig;
import com.jesper.hftc.config.Result;
import com.jesper.hftc.entity.*;
import com.jesper.mapper.ApiLogMapper;
import com.jesper.mapper.ProductMapper;
import com.jesper.mapper.PurchaseOrderMapper;
import com.jesper.mapper.WarehousemangeMapper;
import com.jesper.service.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author 廖凡
 * @Date 2020/3/1 16:46
 */
@SuppressWarnings("all")
@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {
    @Autowired
    private ApiLogMapper apiLogMapper;
    @Autowired
    private PurchaseOrderMapper purchaseOrderMapper;

    @Autowired
    private WarehousemangeMapper warehousemangeMapper;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public int count() {
        int count = 0;
        try {
            count = purchaseOrderMapper.count();
        } catch (Exception e) {
            System.out.println("错误信息：" + e.getMessage());
        }
        return count;
    }

    @Override
    public List<PurchaseOrder> getList(PurchaseOrder purchaseOrder, int start, int end) {
        List<PurchaseOrder> list = new ArrayList<>();
        try {
            list = purchaseOrderMapper.getList(purchaseOrder, start, end);
        } catch (Exception e) {
            System.out.println("错误信息：" + e.getMessage());
        }
        return list;
    }

    @Override
    public boolean update(PurchaseOrder purchaseOrder) {
        if (purchaseOrder.getId() == null) {
            return false;
        }

        purchaseOrder.setUpdateTime(new Date());
        try {
            Integer returnNumber = purchaseOrder.getReturnNumber();
            if (returnNumber == null) {
                purchaseOrderMapper.update(purchaseOrder);
                return true;
            } else if (returnNumber >= 0) {
                purchaseOrder.setStyle(1);
                PurchaseOrder obj = purchaseOrderMapper.getById(purchaseOrder.getId());
                Integer baseReturnNumber = obj.getReturnNumber();
                if (baseReturnNumber == null) baseReturnNumber = 0;
                purchaseOrder.setReturnNumber(baseReturnNumber + returnNumber);
                purchaseOrderMapper.update(purchaseOrder);

                Warehousemanage warehousemanage = warehousemangeMapper.getById(obj.getInwarehouseId());
                Integer warehouseBaseNumber = warehousemanage.getNumber();
                if (warehouseBaseNumber >= returnNumber){
                    warehousemanage.setNumber(warehouseBaseNumber-returnNumber);
                    warehousemanage.setUpdateTime(new Date());
                    warehousemangeMapper.inStorage(warehousemanage);
                }
                return true;
            }else {
                return false;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public Result updateFinisnPurchaseService(Integer id, Integer state) {
        try {
            purchaseOrderMapper.updateFinisnPurchaseService(id, state);
            return Result.ok();
        } catch (Exception e) {
            return Result.ofMessage(400, e.getMessage());
        }

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result add(PurchaseOrder purchaseOrder) {
        if (purchaseOrder.getProductId() == 0 || purchaseOrder.getProductId() == null) {
            return Result.ofMessage(400, "请选择商品");
        }
        if (purchaseOrder.getInwarehouseId() == 0 || purchaseOrder.getInwarehouseId() == null) {
            return Result.ofMessage(400, "请选择仓库");
        }
        if (StringUtils.isEmpty(purchaseOrder.getSupplierId())) {
            return Result.ofMessage(400, "请选择供应商");
        }
        ApiLog apiLog = new ApiLog();
        apiLog.setCreateTime(new Date());
        apiLog.setMethodName("PurchaseOrderService.add()");
        apiLog.setParams(purchaseOrder.toString());
        try {
            Integer number = purchaseOrder.getNumber();
            if (number == null) number = 0;
            //入库数量不为0
            if (number != 0) {
                Warehousemanage warehousemanage = warehousemangeMapper.getById(purchaseOrder.getInwarehouseId());
                Integer baseNumber = warehousemanage.getNumber();
                if (baseNumber == null) baseNumber = 0;
                if (warehousemanage.getProductId() == null || warehousemanage.getProductId() == 0) {
                    warehousemangeMapper.addProduct(purchaseOrder.getProductId(), purchaseOrder.getInwarehouseId());
                }
                warehousemanage.setNumber(baseNumber + number);
                warehousemanage.setUpdateTime(new Date());
                //仓库入库
                warehousemangeMapper.inStorage(warehousemanage);

                Product product = productMapper.getProductById(purchaseOrder.getProductId());
                Integer baseProductNumber = product.getInventoryNumber();
                if (baseProductNumber == null) baseProductNumber = 0;
                product.setInventoryNumber(baseProductNumber + number);
                //商品入库
                productMapper.inStorage(product);
            }
            Date date = new Date();
            purchaseOrder.setCreateTime(date);

            purchaseOrder.setPurchaseTime(date);

            purchaseOrder.setPurchaseNumber("CGDD-" + date.getYear() + date.getMonth() + date.getDate() + "-" + purchaseOrder.getProductId());
            purchaseOrderMapper.insert(purchaseOrder);
            apiLog.setSuccess(MyConfig.SUCCESS);
            return Result.ok();
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//进行手动回滚
            apiLog.setSuccess(MyConfig.FAILD);
            apiLog.setMsg(e.getMessage().length() > 100 ? e.getMessage().substring(0, 99) : e.getMessage());
            System.out.println("错误：" + e.getMessage());
            return Result.ofMessage(400, e.getMessage());
        } finally {
            apiLogMapper.save(apiLog);
        }
    }

    @Override
    public PurchaseOrder getById(Integer id) {
        PurchaseOrder purchaseOrder = null;
        try {
            purchaseOrder = purchaseOrderMapper.getById(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return purchaseOrder;
    }
}
