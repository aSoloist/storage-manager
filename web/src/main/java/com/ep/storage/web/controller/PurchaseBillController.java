package com.ep.storage.web.controller;

import com.ep.commons.domain.model.Pagination;
import com.ep.commons.web.controller.BaseController;
import com.ep.storage.domain.model.PurchaseBill;
import com.ep.storage.domain.service.PurchaseBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/purchase")
public class PurchaseBillController extends BaseController {

    @Autowired
    PurchaseBillService purchaseBillService;

    /**
     * 查询采购单(分页)
     *
     * @param organId
     * @param status
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping(value = "/getPurchase", method = RequestMethod.GET)
    public Pagination<PurchaseBill> getPurchase(@RequestParam(required = false) List<String> organId,
                                                @RequestParam(required = false) List<Integer> status,
                                                @RequestParam(required = false) Integer page,
                                                @RequestParam(required = false) Integer rows){
        Pagination<PurchaseBill> purchaseBillPagination = new Pagination<>(page, rows);
        Integer startIndex = purchaseBillPagination.getStartIdx();
        purchaseBillPagination.setCount(purchaseBillService.getPurchaseCount(organId, status));
        purchaseBillPagination.setRecords(purchaseBillService.getPurchase(organId, status, startIndex, rows));
        return purchaseBillPagination;
    }

    /**
     * 查询采购单数量
     *
     * @param organId
     * @param status
     * @return
     */
    @RequestMapping(value = "/getPurchaseCount", method = RequestMethod.GET)
    public Integer getPurchaseCount(@RequestParam(required = false) List<String> organId,
                                    @RequestParam(required = false) List<Integer> status){
        return purchaseBillService.getPurchaseCount(organId, status);
    }

    /**
     * 条件查询采购单
     *
     * @param organId
     * @param creatorId
     * @param status
     * @param purchaseSn
     * @param purchaseEntryId
     * @return
     */
    @RequestMapping(value = "/getPurchaseBy", method = RequestMethod.GET)
    public List<PurchaseBill> getPurchaseBy(@RequestParam(required = false) List<String> organId,
                                            @RequestParam(required = false) List<String> creatorId,
                                            @RequestParam(required = false) List<Integer> status,
                                            @RequestParam(required = false) List<String> purchaseSn,
                                            @RequestParam(required = false) List<String> purchaseEntryId){
        return purchaseBillService.getPurchaseBy(organId, creatorId, status, purchaseSn, purchaseEntryId);
    }

    /**
     * 条件查询采购单数量
     *
     * @param organId
     * @param creatorId
     * @param status
     * @param purchaseSn
     * @param purchaseEntryId
     * @return
     */
    @RequestMapping(value = "/getCountBy", method = RequestMethod.GET)
    public Integer getCountBy(@RequestParam(required = false) List<String> organId,
                              @RequestParam(required = false) List<String> creatorId,
                              @RequestParam(required = false) List<Integer> status,
                              @RequestParam(required = false) List<String> purchaseSn,
                              @RequestParam(required = false) List<String> purchaseEntryId){
        return purchaseBillService.getCountBy(organId, creatorId, status, purchaseSn, purchaseEntryId);
    }

    /**
     * 查询单个采购单
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/getOne", method = RequestMethod.GET)
    public PurchaseBill getOne(@RequestParam(required = true) String id){
        return purchaseBillService.getOne(id);
    }

    /**
     * 新增或修改
     *
     * @param purchaseBill
     */
    @RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
    public void saveOrUpdate(@RequestBody PurchaseBill purchaseBill) {

        purchaseBill.setCreator(this.currentUser);
        purchaseBill.setOrgan(this.primaryOrgan);
        purchaseBillService.saveOrUpdate(purchaseBill);
    }

    /**
     * 状态修改
     *
     * @param id
     * @param status
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public void updateStatus(@RequestParam String id,
                             @RequestParam Integer status) {
        purchaseBillService.updateStatus(id, status);
    }
}
