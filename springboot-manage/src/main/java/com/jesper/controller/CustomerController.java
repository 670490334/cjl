package com.jesper.controller;

import com.jesper.hftc.entity.Customer;
import com.jesper.hftc.entity.Product;
import com.jesper.service.CustomerService;
import com.jesper.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @Author 廖凡
 * @Date 2020/2/19 16:28
 */
@Controller
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/user/customerAdd")
    public String customerAdd(Model model, Customer customer) {
        model.addAttribute("customer", customer);
        return "product/productAdd";
    }

    @GetMapping("user/customerEdit")
    public String customerEdit(Model model,Customer customer){
        if (customer.getId()!=null){
            customer=customerService.getById(customer.getId());
        }
        model.addAttribute("customer",customer);
        return "customer/customerEdit";
    }

    @PostMapping("/user/customerEdit")
    public String customerEdits(Model model, Customer customer) {
        customerService.add(customer);
        return "redirect:customerManage_0_0_0";
    }

    @GetMapping("user/customerManage_{pageCurrent}_{pageSize}_{pageCount}")
    public String customerManage(Model model, Customer customer, @PathVariable Integer pageCurrent,
                                 @PathVariable Integer pageSize,
                                 @PathVariable Integer pageCount) {
        if (pageSize == 0) pageSize = 20;
        if (pageCurrent == 0) pageCurrent = 1;
        int rows = customerService.count(customer);
        if (pageCount == 0) pageCount = rows % pageSize == 0 ? (rows / pageSize) : (rows / pageSize) + 1;
        customer.setStart((pageCurrent - 1) * pageSize);
        customer.setEnd(pageCount*pageSize);
        model.addAttribute("customerList", customerService.getAllCustomer(customer));
        model.addAttribute("customer",customer);
        String pageHTML = PageUtil.getPageContent("getProductList_{pageCurrent}_{pageSize}_{pageCount}?productName=" + customer.getName(), pageCurrent, pageSize, pageCount);
        model.addAttribute("pageHTML", pageHTML);
        return "customer/customerManage";
    }

    @GetMapping("user/customerBuy")
    public String customerBuy(String id){
        boolean res = customerService.buy(id);
        if (res){
            return "redirect:productSale_0_0_0";
        }else{
            return "redirect:customerManage_0_0_0";
        }
    }
}
