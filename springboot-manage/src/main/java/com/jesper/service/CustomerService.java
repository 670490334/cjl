package com.jesper.service;

import com.jesper.hftc.entity.Customer;

import java.util.List;

/**
 * @Author 廖凡
 * @Date 2020/2/17 22:39
 */
public interface CustomerService {
    void add(Customer customer);

    void edit(Customer customer);

    void delete(String id);

    Customer getById(String id);

    List<Customer> getAllCustomer(Customer customer);

    int count(Customer customer);

    List<Customer> getCustomerList();

    boolean buy(String id);

    Customer getRedisId();
}
