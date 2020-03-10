package com.jesper.controller;

import com.alibaba.fastjson.JSONObject;
import com.jesper.hftc.config.Result;
import com.jesper.hftc.entity.Customer;
import com.jesper.hftc.entity.Product;
import com.jesper.hftc.entity.SalesOrder;
import com.jesper.hftc.entity.SalesOrderChild;
import com.jesper.service.CustomerService;
import com.jesper.service.SalesOrderService;
import com.jesper.service.WarehousemangeService;
import com.jesper.util.DateUtil;
import com.jesper.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author 廖凡
 * @Date 2020/2/19 17:30
 */
@SuppressWarnings("all")
@Controller
public class SalesOrderController {

    @Autowired
    private SalesOrderService salesOrderService;
    @Autowired
    private WarehousemangeService warehousemangeService;
    @Autowired
    private CustomerService customerService;

    /**
     * 根据客户id获取订单
     *
     * @param id
     * @return
     */
    @GetMapping("user/customerSalesOrderList_{pageCurrent}_{pageSize}_{pageCount}")
    public String customerSalesOrderList(Model model, @PathVariable Integer pageCurrent,
                                         @PathVariable Integer pageSize,
                                         @PathVariable Integer pageCount, SalesOrder salesOrder) {
        if (pageSize == 0) pageSize = 20;
        if (pageCurrent == 0) pageCurrent = 1;
        salesOrder.setMinOrderTime(DateUtil.strToDate(salesOrder.getMinOrderTimeStr()));
        if (salesOrder.getMinOrderTimeStr()!=null) {
            if(salesOrder.getMaxOrderTimeStr()==null){
                salesOrder.setMaxOrderTime(new Date());
            }else{
                salesOrder.setMaxOrderTime(DateUtil.strToDate(salesOrder.getMaxOrderTimeStr()));
            }
        }
        int rows = salesOrderService.count(salesOrder);
        if (pageCount == 0) pageCount = rows % pageSize == 0 ? (rows / pageSize) : (rows / pageSize) + 1;
        salesOrder.setStart((pageCurrent - 1) * pageSize);
        salesOrder.setEnd(pageCurrent*pageSize);
        List<SalesOrder> orderList = salesOrderService.getOrdersByCustomerId(salesOrder);

        String pageHTML = PageUtil.getPageContent("customerSalesOrderList_{pageCurrent}_{pageSize}_{pageCount}", pageCurrent, pageSize, pageCount);
        model.addAttribute("pageHTML", pageHTML);
        model.addAttribute("order", salesOrder);
        model.addAttribute("orderList", orderList);
        return "order/salesOrderManage";
    }

    @GetMapping("user/salesOrderManage_{pageCurrent}_{pageSize}_{pageCount}")
    public String salesOrderManage(Model model, @PathVariable Integer pageCurrent,
                                   @PathVariable Integer pageSize,
                                   @PathVariable Integer pageCount, SalesOrder salesOrder) {
        if (pageSize == 0) pageSize = 20;
        if (pageCurrent == 0) pageCurrent = 1;
        salesOrder.setMinOrderTime(DateUtil.strToDate(salesOrder.getMinOrderTimeStr()));
        if (salesOrder.getMinOrderTimeStr()!=null) {
            if(salesOrder.getMaxOrderTimeStr()==null){
                salesOrder.setMaxOrderTime(new Date());
            }else{
                salesOrder.setMaxOrderTime(DateUtil.strToDate(salesOrder.getMaxOrderTimeStr()));
            }
        }
        int rows = salesOrderService.count(salesOrder);
        if (pageCount == 0) pageCount = rows % pageSize == 0 ? (rows / pageSize) : (rows / pageSize) + 1;
        salesOrder.setStart((pageCurrent - 1) * pageSize);
        salesOrder.setEnd(pageCurrent*pageSize);
        List<SalesOrder> orderList = salesOrderService.getList(salesOrder);

        String pageHTML = PageUtil.getPageContent("salesOrderManage_{pageCurrent}_{pageSize}_{pageCount}", pageCurrent, pageSize, pageCount);
        model.addAttribute("pageHTML", pageHTML);
        model.addAttribute("order", salesOrder);
        model.addAttribute("orderList", orderList);
        return "order/salesOrderManage";
    }

    @ResponseBody
    @PostMapping("user/saleProduct")
    public Result saleProduct(@RequestBody JSONObject jsonObject) {
        return salesOrderService.saleProduct(jsonObject.getString("ids"));
    }

    @GetMapping("user/saleForm")
    public String saleForm(Model model) {
        Result result = salesOrderService.saleForm();
        List<Product> productList = new ArrayList<>();
        String msg = "";
        if (result.getCode() == 200) {

            productList = (List<Product>) result.getData();
        } else {
            msg = result.getMessage();
        }
        Customer customer = customerService.getRedisId();
        model.addAttribute("productList", productList);
        model.addAttribute("msg", msg);
        model.addAttribute("customer", customer);
        return "order/saleFormApply";
    }


    @ResponseBody
    @PostMapping("user/saleFormCreate")
    public Result saleFormCreate(Model model, @RequestBody JSONObject jsonObject) {

        Result result = salesOrderService.saleFormCreate(jsonObject);
        if (result.getCode() == 200) {
            //清空redis缓存商品
            salesOrderService.clean();
        }
        //todo
//        SalesOrder salesOrder = new SalesOrder();
//        String msg = "";
//        if (result.getCode() == 200){
//            salesOrder = (SalesOrder)result.getData();
//        }else{
//            msg = result.getMessage();
//        }
//        model.addAttribute("salesOrder",salesOrder);
//        model.addAttribute("msg",msg);
        return result;
    }

    @GetMapping("user/tosaleForm")
    public String tosaleForm(Model model, String id) {
        SalesOrder salesOrder = salesOrderService.getSaleFormById(id);
        if (salesOrder == null) {
            model.addAttribute("msg", "id为空");
        } else {
            model.addAttribute("salesOrder", salesOrder);
        }
        return "order/saleForm";
    }

    @GetMapping("user/salesOrderCk")
    public String salesOrderCk(String id, Model model) {
        List<SalesOrderChild> salesOrderChildList = salesOrderService.getProduct(id);
        if (salesOrderChildList.size() > 0) {
            model.addAttribute("salesOrderChildList", salesOrderChildList);
            return "order/orderCk";
        }else{
            salesOrderService.updateCkStatus(id);
            return "redirect:user/salesOrderManage_0_0_0";
        }
    }


    @ResponseBody
    @PostMapping("user/ckSaleOrder")
    public Result ckSaleOrder(Integer id, Integer warehouseId, Integer saleNumber) {
        Result result = salesOrderService.saleOrderChildCk(id, warehouseId, saleNumber);
        return result;
    }
}
