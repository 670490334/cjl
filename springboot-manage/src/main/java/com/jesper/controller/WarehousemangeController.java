package com.jesper.controller;

import com.jesper.hftc.config.Result;
import com.jesper.hftc.entity.Warehouse;
import com.jesper.hftc.entity.Warehousemanage;
import com.jesper.service.WarehouseService;
import com.jesper.service.WarehousemangeService;
import com.jesper.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * @Author 廖凡
 * @Date 2020/2/28 22:31
 */
@Controller
public class WarehousemangeController {
    @Autowired
    private WarehousemangeService warehousemangeService;

    @GetMapping("/user/warehouseManage_{pageCurrent}_{pageSize}_{pageCount}")
    public String warehouseManage(Model model, @PathVariable Integer pageCurrent,
                                  @PathVariable Integer pageSize,
                                  @PathVariable Integer pageCount, Warehousemanage warehousemanage) {
        if (pageSize == 0) pageSize = 20;
        if (pageCurrent == 0) pageCurrent = 1;
        int rows = warehousemangeService.count();
        if (pageCount == 0) pageCount = rows % pageSize == 0 ? (rows / pageSize) : (rows / pageSize) + 1;
        List<Warehousemanage> list = warehousemangeService.getList(warehousemanage,(pageCurrent - 1) * pageSize, pageSize*pageCurrent);

        String pageHTML = PageUtil.getPageContent("warehouseManage_{pageCurrent}_{pageSize}_{pageCount}", pageCurrent, pageSize, pageCount);
        model.addAttribute("pageHTML", pageHTML);
        model.addAttribute("warehousemanage", warehousemanage);
        model.addAttribute("warehousemanageList", list);

        return "warehousemanage/manage";
    }

    @GetMapping("/user/warehouseAdd")
    public  String warehouseEdit(Model model,Warehousemanage warehousemanage){
        if (warehousemanage.getId()!=null){
            Warehousemanage obj = warehousemangeService.getById(warehousemanage.getId());
            model.addAttribute("warehousemanage",obj);
            if (obj==null){
                model.addAttribute("msg","获取信息失败");
            }else{
                model.addAttribute("msg","");
            }

        }else {
            model.addAttribute("warehousemanage",warehousemanage);
            model.addAttribute("msg","");
        }
        return "warehousemanage/add";

    }

    @PostMapping("/user/Addwarehouse")
    public  String warehouseAdd(Model model,Warehousemanage warehousemanage){
        Result res = warehousemangeService.add(warehousemanage);
        if(res.getCode()==200){
            return "redirect:warehouseManage_0_0_0";
        }
        model.addAttribute("warehousemanage",warehousemanage);
        model.addAttribute("msg","提交失败,请查看后台日志");
        return "warehousemanage/add";
    }
}
