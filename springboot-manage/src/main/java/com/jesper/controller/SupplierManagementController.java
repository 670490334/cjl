package com.jesper.controller;

import com.jesper.hftc.entity.Product;
import com.jesper.hftc.entity.SalesOrder;
import com.jesper.hftc.entity.SupplierManagement;
import com.jesper.model.ResObject;
import com.jesper.service.SupplierManagementService;
import com.jesper.util.Constant;
import com.jesper.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Author 廖凡
 * @Date 2020/2/24 22:03
 */
@Controller
public class SupplierManagementController {
    @Autowired
    private SupplierManagementService supplierManagementService;

    @GetMapping("/user/supplierManagementAdd")
    public String supplierManagementAdd(SupplierManagement supplierManagement, Model model) {
        if (!StringUtils.isEmpty(supplierManagement.getId())) {
            supplierManagement = supplierManagementService.getById(supplierManagement.getId());
        }
        model.addAttribute("supplierManagement", supplierManagement);
        model.addAttribute("msg", "");
        return "supplierManagement/add";
    }

    @GetMapping("/user/supplierManagementManage_{pageCurrent}_{pageSize}_{pageCount}")
    public String supplierManagementManage(Model model, @PathVariable Integer pageCurrent,
                                           @PathVariable Integer pageSize,
                                           @PathVariable Integer pageCount, SupplierManagement supplierManagement) {
        if (pageSize == 0) pageSize = 20;
        if (pageCurrent == 0) pageCurrent = 1;
        int rows = supplierManagementService.count();
        if (pageCount == 0) pageCount = rows % pageSize == 0 ? (rows / pageSize) : (rows / pageSize) + 1;
        List<SupplierManagement> list = supplierManagementService.getList((pageCurrent - 1) * pageSize, pageSize*pageCurrent);

        String pageHTML = PageUtil.getPageContent("supplierManagementManage_{pageCurrent}_{pageSize}_{pageCount}", pageCurrent, pageSize, pageCount);
        model.addAttribute("pageHTML", pageHTML);
        model.addAttribute("supplierManagement", supplierManagement);
        model.addAttribute("supplierManagementList", list);
        return "supplierManagement/manage";
    }

    @PostMapping("/user/addSupplierManagement")
    public String addSupplierManagement(Model model ,SupplierManagement supplierManagement) {
        boolean add = supplierManagementService.add(supplierManagement);
        if (add) {
            return "redirect:supplierManagementManage_0_0_0";
        } else {
            model.addAttribute("supplierManagement",supplierManagement);
            model.addAttribute("msg","提交失败，请重试或者查看错误日志");
            return "supplierManagement/add";
        }

    }


    @ResponseBody
    @PostMapping("/user/supplierManagementDelete")
    public ResObject<Object> productDelete(String id){
        boolean res = supplierManagementService.delete(id);
        ResObject<Object> object = new ResObject<Object>(Constant.Code01, Constant.Msg01, null, null);
        if(res){
            return object;
        }else {
            object.setResCode("02");
            object.setResMessage("删除失败");
            return object;
        }

    }
}
