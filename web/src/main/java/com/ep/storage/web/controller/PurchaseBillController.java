package com.ep.storage.web.controller;

import com.ep.commons.domain.model.Pagination;
import com.ep.commons.domain.service.SnSerice;
import com.ep.commons.web.controller.BaseController;
import com.ep.storage.domain.model.PurchaseBill;
import com.ep.storage.domain.model.PurchaseBillEntry;
import com.ep.storage.domain.service.PurchaseBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/purchase")
public class PurchaseBillController extends BaseController {

    @Autowired
    PurchaseBillService purchaseBillService;

    @Autowired
    SnSerice snSerice;

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
     * @return
     */
    @RequestMapping(value = "/getPurchaseBy", method = RequestMethod.GET)
    public List<PurchaseBill> getPurchaseBy(@RequestParam(required = false) List<String> organId,
                                            @RequestParam(required = false) List<String> creatorId,
                                            @RequestParam(required = false) List<Integer> status,
                                            @RequestParam(required = false) List<String> purchaseSn){
        return purchaseBillService.getPurchaseBy(organId, creatorId, status, purchaseSn);
    }

    /**
     * 条件查询采购单数量
     *
     * @param organId
     * @param creatorId
     * @param status
     * @param purchaseSn
     * @return
     */
    @RequestMapping(value = "/getCountBy", method = RequestMethod.GET)
    public Integer getCountBy(@RequestParam(required = false) List<String> organId,
                              @RequestParam(required = false) List<String> creatorId,
                              @RequestParam(required = false) List<Integer> status,
                              @RequestParam(required = false) List<String> purchaseSn){
        return purchaseBillService.getCountBy(organId, creatorId, status, purchaseSn);
    }

    /**
     * 获取单据下所有分录
     *
     * @param status
     * @param purchaseSn
     */
    @RequestMapping(value = "/getEntry", method = RequestMethod.GET)
    public List<PurchaseBillEntry> getEntryAll(@RequestParam(required = false) List<Integer> status,
                                               @RequestParam(required = true) List<String> purchaseSn){
        return purchaseBillService.getEntryAll(status, purchaseSn);
    }

    /**
     * 获取单据下所有分录数量
     *
     * @param status
     * @param purchaseSn
     */
    @RequestMapping(value = "/getEntryCount", method = RequestMethod.GET)
    public Integer getEntryCount(@RequestParam(required = false) List<Integer> status,
                                 @RequestParam(required = true) List<String> purchaseSn){
        return purchaseBillService.getEntryCount(status, purchaseSn);
    }

    /**
     * 条件获取分录
     *
     * @param goodsId
     * @param goodsName
     * @param status
     * @param purchaseSn
     */
    @RequestMapping(value = "/getEntryBy", method = RequestMethod.GET)
    public List<PurchaseBillEntry> getEntryBy(@RequestParam(required = false) List<String> goodsId,
                                              @RequestParam(required = false) List<String> goodsName,
                                              @RequestParam(required = false) List<Integer> status,
                                              @RequestParam(required = false) List<String> purchaseSn){
        return purchaseBillService.getEntryBy(goodsId, goodsName, status, purchaseSn);
    }

    /**
     * 条件获取单据下分录数量
     *
     * @param goodsId
     * @param goodsName
     * @param status
     * @param purchaseSn
     */
    @RequestMapping(value = "/getEntryByCount", method = RequestMethod.GET)
    public Integer getEntryCount(@RequestParam(required = false) List<String> goodsId,
                                 @RequestParam(required = false) List<String> goodsName,
                                 @RequestParam(required = false) List<Integer> status,
                                 @RequestParam(required = false) List<String> purchaseSn){
        return purchaseBillService.getEntryByCount(goodsId, goodsName, status, purchaseSn);
    }

    /**
     * 查询单个采购单
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/getOne", method = RequestMethod.GET)
    public PurchaseBill getOne(@RequestParam(required = true) String id){
        return purchaseBillService.get(id);
    }

    /**
     * 新增或修改
     *
     * @param purchaseBill
     */
    @RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
    public PurchaseBill saveOrUpdate(@RequestBody PurchaseBill purchaseBill) {
        if (purchaseBill.getSn() == null){
            purchaseBill.setSn(snSerice.gen("CGD", this.primaryOrganId));
        }
        if (purchaseBill.getCreator() == null) {
            purchaseBill.setCreator(this.currentUser);
        }
        if (purchaseBill.getOrgan() == null) {
            purchaseBill.setOrgan(this.primaryOrgan);
        }
        return purchaseBillService.saveOrUpdate(purchaseBill);
    }

    /**
     * 保存分录
     *
     * @param purchaseBillEntry
     */
    @RequestMapping(value = "/saveEntry", method = RequestMethod.POST)
    public PurchaseBillEntry saveEntry(@RequestBody PurchaseBillEntry purchaseBillEntry){
        return purchaseBillService.saveOrUpdateEntry(purchaseBillEntry);
    }

    /**
     * 状态修改
     *
     * @param id
     * @param status
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public void updateStatus(@RequestParam String id,
                                     @RequestParam Integer status) {
        if (status == -1){
            purchaseBillService.delete(id);
        } else {
            purchaseBillService.updateStatus(id, status);
        }
    }
}


