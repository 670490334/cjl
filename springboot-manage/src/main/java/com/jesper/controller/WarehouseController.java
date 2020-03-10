//package com.jesper.controller;
//
//import com.jesper.hftc.entity.Product;
//import com.jesper.hftc.entity.SupplierManagement;
//import com.jesper.hftc.entity.Warehouse;
//import com.jesper.service.WarehouseService;
//import com.jesper.util.PageUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//
//import java.util.List;
//
///**
// * 仓库
// * @Author 廖凡
// * @Date 2020/2/28 14:27
// */
//@Controller
//public class WarehouseController {
//    @Autowired
//    private WarehouseService warehouseService;
//
//    @GetMapping("/user/warehouseManage_{pageCurrent}_{pageSize}_{pageCount}")
//    public String warehouseManage(Model model, @PathVariable Integer pageCurrent,
//                                           @PathVariable Integer pageSize,
//                                           @PathVariable Integer pageCount, Warehouse warehousemanage) {
//        if (pageSize == 0) pageSize = 20;
//        if (pageCurrent == 0) pageCurrent = 1;
//        int rows = warehouseService.count();
//        if (pageCount == 0) pageCount = rows % pageSize == 0 ? (rows / pageSize) : (rows / pageSize) + 1;
//        List<Warehouse> list = warehouseService.getList(warehousemanage,(pageCurrent - 1) * pageSize, pageSize*pageCurrent);
//
//        String pageHTML = PageUtil.getPageContent("warehouseManage_{pageCurrent}_{pageSize}_{pageCount}", pageCurrent, pageSize, pageCount);
//        model.addAttribute("pageHTML", pageHTML);
//        model.addAttribute("warehousemanage", warehousemanage);
//        model.addAttribute("warehouseList", list);
//        return "warehousemanage/manage";
//    }
//
//    @GetMapping("/user/warehouseAdd")
//    public  String productEdit(Model model,Warehouse warehousemanage){
//        if (warehousemanage.getId()!=null){
//            warehouseService.getById(warehousemanage.getId());
//        }
//        warehousemanage= warehouseService.getById(warehousemanage.getId());
//        model.addAttribute("warehousemanage",warehousemanage);
//        return "warehousemanage/add";
//    }
//}
