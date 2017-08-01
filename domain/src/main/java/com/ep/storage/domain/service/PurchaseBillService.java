package com.ep.storage.domain.service;

import com.ep.storage.domain.dao.PurchaseBillDao;
import com.ep.storage.domain.model.PurchaseBill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseBillService {
    @Autowired
    private PurchaseBillDao purchaseBillDao;

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
     * @param purchaseEntryId
     * @return
     */
    public List<PurchaseBill> getPurchaseBy(List<String> organId, List<String> creatorId, List<Integer> status, List<String> purchaseSn, List<String> purchaseEntryId){
        return purchaseBillDao.getPurchaseBy(organId, creatorId, status, purchaseSn, purchaseEntryId);
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
    public Integer getCountBy(List<String> organId, List<String> creatorId, List<Integer> status, List<String> purchaseSn, List<String> purchaseEntryId) {
        return purchaseBillDao.getCountBy(organId, creatorId, status, purchaseSn, purchaseEntryId);
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
     * 查找单个post
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
