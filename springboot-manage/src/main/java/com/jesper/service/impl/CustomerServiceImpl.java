package com.jesper.service.impl;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.ser.std.UUIDSerializer;
import com.jesper.hftc.config.MyConfig;
import com.jesper.hftc.entity.ApiLog;
import com.jesper.hftc.entity.Customer;
import com.jesper.mapper.ApiLogMapper;
import com.jesper.mapper.CustomerMapper;
import com.jesper.redis.RedisService;
import com.jesper.redis.SaleProductIds;
import com.jesper.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.UUIDEditor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @Author 廖凡
 * @Date 2020/2/18 20:59
 */
@Service
@SuppressWarnings("all")
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private ApiLogMapper apiLogMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private RedisService redisService;

    @Override
    public void add(Customer customer) {
        ApiLog apiLog = new ApiLog();
        apiLog.setCreateTime(new Date());
        apiLog.setMethodName("CategoryService.add()");
        try {
            if (StringUtils.isEmpty(customer.getId().toString())) {
                customer.setId(UUID.randomUUID().toString());
                customer.setLevel("A");
                apiLog.setParams(customer.toString().length() > 1000 ? customer.toString().substring(0, 999) : customer.toString());
                customerMapper.insert(customer);
            } else {
                apiLog.setParams(customer.toString().length() > 1000 ? customer.toString().substring(0, 999) : customer.toString());
                customerMapper.update(customer);
            }
            apiLog.setSuccess(MyConfig.SUCCESS);
        } catch (Exception e) {
            apiLog.setSuccess(MyConfig.FAILD);
            apiLog.setMsg(e.getMessage().toString().length() > 100 ? e.getMessage().toString().substring(0, 99) : e.getMessage().toString());
            System.out.println("新增客户：+" + customer.getName() + "--失败");
        } finally {
            apiLogMapper.save(apiLog);
        }
    }

    @Override
    public void edit(Customer customer) {
        ApiLog apiLog = new ApiLog();
        apiLog.setCreateTime(new Date());
        apiLog.setMethodName("CategoryService.edit()");
        apiLog.setParams(customer.toString().length() > 1000 ? customer.toString().substring(0, 999) : customer.toString());
        try {
            customerMapper.update(customer);
            apiLog.setSuccess(MyConfig.SUCCESS);
        } catch (Exception e) {
            apiLog.setSuccess(MyConfig.FAILD);
            apiLog.setMsg(e.getMessage().toString().length() > 50 ? e.getMessage().toString().substring(0, 49) : e.getMessage().toString());
            System.out.println("修改客户：+" + customer.getName() + "--失败");
        } finally {
            apiLogMapper.save(apiLog);
        }
    }

    @Override
    public void delete(String id) {
        ApiLog apiLog = new ApiLog();
        apiLog.setCreateTime(new Date());
        apiLog.setMethodName("CategoryService.delete()");
        apiLog.setParams(id);
        try {
            customerMapper.delete(id);
            apiLog.setSuccess(MyConfig.SUCCESS);
        } catch (Exception e) {
            apiLog.setSuccess(MyConfig.FAILD);
            apiLog.setMsg(e.getMessage().toString().length() > 50 ? e.getMessage().toString().substring(0, 49) : e.getMessage().toString());
            System.out.println("修改客户id为：+" + id + "--失败");
        } finally {
            apiLogMapper.save(apiLog);
        }
    }

    @Override
    public Customer getById(String id) {
        Customer customer = new Customer();
        try {
            customer = customerMapper.getById(id);
            return customer;
        } catch (Exception e) {
            System.out.println("获取id为：+" + id + "客户--失败");
            return customer;
        }
    }

    @Override
    public List<Customer> getAllCustomer(Customer customer) {
        List<Customer> list = new ArrayList<>();
        try {
            list = customerMapper.getCustomerList(customer);
        } catch (Exception e) {
            System.out.println("获取客户信息失败");
        }
        return list;
    }

    @Override
    public int count(Customer customer) {
        return customerMapper.count(customer);
    }

    @Override
    public List<Customer> getCustomerList() {
        return customerMapper.getAllCustomerList();
    }

    @Override
    public boolean buy(String id) {
        SaleProductIds customerId = SaleProductIds.customerBuy;
        //购买客户 存入redis
        boolean res = redisService.set(customerId, "ID", id);
        return res;
    }

    @Override
    public Customer getRedisId() {
        Customer customer = new Customer();
        SaleProductIds customerId = SaleProductIds.customerBuy;
        String id = redisService.get(customerId, "ID", String.class);
        if (id == null){
            return customer;
        }
        return customerMapper.getById(id);
    }
}
