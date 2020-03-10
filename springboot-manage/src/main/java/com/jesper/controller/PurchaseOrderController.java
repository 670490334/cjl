package com.jesper.controller;

import com.jesper.hftc.config.Result;
import com.jesper.hftc.entity.Product;
import com.jesper.hftc.entity.PurchaseOrder;
import com.jesper.hftc.entity.SupplierManagement;
import com.jesper.hftc.entity.Warehousemanage;
import com.jesper.service.ProductService;
import com.jesper.service.PurchaseOrderService;
import com.jesper.service.SupplierManagementService;
import com.jesper.service.WarehousemangeService;
import com.jesper.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 采购订单
 *
 * @Author 廖凡
 * @Date 2020/2/28 14:25
 */
@Controller
public class PurchaseOrderController {

    @Autowired
    private ProductService productService;

    @Autowired
    private WarehousemangeService warehousemangeService;

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @Autowired
    private SupplierManagementService supplierManagementService;

    @GetMapping("/user/purchaseOrderManage_{pageCurrent}_{pageSize}_{pageCount}")
    public String warehouseManage(Model model, @PathVariable Integer pageCurrent,
                                  @PathVariable Integer pageSize,
                                  @PathVariable Integer pageCount, PurchaseOrder purchaseOrder) {
        if (pageSize == 0) pageSize = 20;
        if (pageCurrent == 0) pageCurrent = 1;
        int rows = purchaseOrderService.count();
        if (pageCount == 0) pageCount = rows % pageSize == 0 ? (rows / pageSize) : (rows / pageSize) + 1;
        List<PurchaseOrder> list = purchaseOrderService.getList(purchaseOrder, (pageCurrent - 1) * pageSize, pageSize * pageCurrent);

        String pageHTML = PageUtil.getPageContent("purchaseOrderManage_{pageCurrent}_{pageSize}_{pageCount}", pageCurrent, pageSize, pageCount);
        model.addAttribute("pageHTML", pageHTML);
        model.addAttribute("purchaseOrder", purchaseOrder);
        model.addAttribute("purchaseOrderList", list);

        return "purchaseOrder/manage";
    }

    @PostMapping("/user/addPurchaseOrder")
    public String addPurchaseOrder(Model model, PurchaseOrder purchaseOrder) {
        Result result = purchaseOrderService.add(purchaseOrder);
        if (result.getCode() == 200) {
            return "redirect:purchaseOrderManage_0_0_0";
        } else {
            model.addAttribute("purchaseOrder", purchaseOrder);
            model.addAttribute("msg", "提交失败，" + result.getMessage());
            return "purchaseOrder/add";
        }

    }

    @GetMapping("/user/purchaseOrderAdd")
    public String purchaseOrderAdd(Model model, PurchaseOrder purchaseOrder) {
        model.addAttribute("supplierList", supplierManagementService.list());
        model.addAttribute("inwarehousemanageList", warehousemangeService.getInwarehousemanageList(purchaseOrder.getProductId()));
        Product product = productService.getProduct(purchaseOrder.getProductId());

        purchaseOrder.setProductName(product.getProductName());
        if (purchaseOrder.getId() != null) {
            PurchaseOrder obj = purchaseOrderService.getById(purchaseOrder.getId());
            model.addAttribute("purchaseOrder", obj);
            if (obj == null) {
                model.addAttribute("msg", "获取信息失败");
            } else {
                model.addAttribute("msg", "");
            }
        } else {
            model.addAttribute("purchaseOrder", purchaseOrder);
            model.addAttribute("msg", "");
        }
        return "purchaseOrder/add";

    }

    @ResponseBody
    @PostMapping("/user/finishPurchaseOrder")
    public Result finishPurchaseOrder(Integer id) {

        return purchaseOrderService.updateFinisnPurchaseService(id, 1);

    }

    @GetMapping("/user/purchaseOrderEdit")
    public String purchaseOrderEdit(Model model, PurchaseOrder purchaseOrder) {
        Integer id = purchaseOrder.getId();
        if (id != null) {
            purchaseOrder = purchaseOrderService.getById(purchaseOrder.getId());
        }
        if (purchaseOrder == null) {
            model.addAttribute("msg", "获取信息失败");
            purchaseOrder.setId(id);
        } else {
            model.addAttribute("msg", "");
        }
        model.addAttribute("purchaseOrder", purchaseOrder);
        return "purchaseOrder/edit";
    }

    @PostMapping("/user/editPpurchaseOrder")
    public String ediPpurchaseOrder(Model model, PurchaseOrder purchaseOrder) {

        boolean res = purchaseOrderService.update(purchaseOrder);

        if (res) {
            return "redirect:purchaseOrderManage_0_0_0";
        } else {
            model.addAttribute("purchaseOrder", purchaseOrder);
            model.addAttribute("msg", "更新失败");
            return "purchaseOrder/edit";
        }
    }
}
