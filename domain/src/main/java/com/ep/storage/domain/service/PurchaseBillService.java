package com.ep.storage.domain.service;

import com.ep.storage.domain.dao.PurchaseBillDao;
import com.ep.storage.domain.dao.PurchaseBillEntryDao;
import com.ep.storage.domain.model.PurchaseBill;
import com.ep.storage.domain.model.PurchaseBillEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseBillService {
    @Autowired
    private PurchaseBillDao purchaseBillDao;

    @Autowired
    private PurchaseBillEntryDao purchaseBillEntryDao;

    /**
     * 查询采购单(分页)
     *
     * @param organId
     * @param status
     * @param startIndex
     * @param rows
     * @return
     */
    public List<PurchaseBill> getPurchase(List<String> organId, List<Integer> status, Integer startIndex, Integer rows){
        return purchaseBillDao.getPurchase(organId,  status, startIndex, rows);
    }

    /**
     * 查询采购单数量
     *
     * @param organId
     * @param status
     * @return
     */
    public Integer getPurchaseCount(List<String> organId, List<Integer> status){
        return purchaseBillDao.getPurchaseCount(organId, status);
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
    public List<PurchaseBill> getPurchaseBy(List<String> organId, List<String> creatorId, List<Integer> status, List<String> purchaseSn){
        return purchaseBillDao.getPurchaseBy(organId, creatorId, status, purchaseSn);
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
    public Integer getCountBy(List<String> organId, List<String> creatorId, List<Integer> status, List<String> purchaseSn) {
        return purchaseBillDao.getCountBy(organId, creatorId, status, purchaseSn);
    }

    /**
     * 获取单据下所有分录
     *
     * @param status
     * @param purchaseSn
     */
    public List<PurchaseBillEntry> getEntryAll(List<Integer> status, List<String> purchaseSn){
        List<PurchaseBillEntry> list = purchaseBillEntryDao.getAll(status, purchaseSn);
        return list;
    }

    /**
     * 获取单据下所有分录数量
     *
     * @param status
     * @param purchaseSn
     */
    public Integer getEntryCount(List<Integer> status, List<String> purchaseSn){
        return purchaseBillEntryDao.getCount(status, purchaseSn);
    }

    /**
     * 条件获取分录
     *
     * @param goodsId
     * @param goodsName
     * @param status
     * @param purchaseSn
     */
    public List<PurchaseBillEntry> getEntryBy(List<String> goodsId, List<String> goodsName, List<Integer> status, List<String> purchaseSn){
        List<PurchaseBillEntry> list = purchaseBillEntryDao.getEntryBy(goodsId, goodsName, status, purchaseSn);
        return list;
    }

    /**
     * 条件获取分录数量
     *
     * @param goodsId
     * @param goodsName
     * @param status
     * @param purchaseSn
     */
    public Integer getEntryByCount(List<String> goodsId, List<String> goodsName, List<Integer> status, List<String> purchaseSn){
        return purchaseBillEntryDao.getEntryByCount(goodsId, goodsName, status, purchaseSn);
    }

    /**
     * 新增或者修改
     *
     * @param purchase
     */
    public void saveOrUpdate(PurchaseBill purchase) {
        this.purchaseBillDao.saveOrUpdate(purchase);
    }

    /**
     * 查找单个purchase
     *
     * @param id
     * @return
     */
    public PurchaseBill getOne(String id) {
        return purchaseBillDao.get(id);
    }

    /**
     * 状态修改
     *
     * @param id
     */
    public void updateStatus(String id, Integer status) {

        purchaseBillDao.updateStatus(id, status);
    }
}
