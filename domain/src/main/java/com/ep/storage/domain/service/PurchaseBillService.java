package com.ep.storage.domain.service;

import com.ep.commons.domain.dao.SNDao;
import com.ep.commons.domain.service.IService;
import com.ep.commons.tool.annotation.SaveLog;
import com.ep.storage.domain.dao.*;
import com.ep.storage.domain.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PurchaseBillService implements IService<PurchaseBill, String> {
    @Autowired
    private PurchaseBillDao purchaseBillDao;

    @Autowired
    private PurchaseBillEntryDao purchaseBillEntryDao;

    @Autowired
    private StorageBillDao storageBillDao;

    @Autowired
    private StorageBillEntryDao storageBillEntryDao;

    @Autowired
    private SNDao snDao;

    @Autowired
    private StocksDao stocksDao;

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
     * 新增或者修改采购单
     *
     * @param purchase
     */
    @SaveLog(message = "新增或者修改采购单")
    public PurchaseBill saveOrUpdate(PurchaseBill purchase) {
        this.purchaseBillDao.saveOrUpdate(purchase);
        return purchase;
    }

    @SaveLog(
            message = "删除采购单",
            clazz = PurchaseBill.class
    )
    public String delete(String id) {
        purchaseBillDao.updateStatus(id, -1);
        return id;
    }

    @Override
    public List<PurchaseBill> list(String s, List<String> list, List<String> list1, List<Integer> list2, Integer integer, Integer integer1) {
        return null;
    }

    @Override
    public long listSize(String s, List<String> list, List<String> list1, List<Integer> list2) {
        return 0;
    }

    /**
     * 新增或者修改分录
     *
     * @param purchaseBillEntry
     */
    @SaveLog(message = "新增或者修改分录")
    public PurchaseBillEntry saveOrUpdateEntry(PurchaseBillEntry purchaseBillEntry) {
        this.purchaseBillEntryDao.saveOrUpdate(purchaseBillEntry);
        return purchaseBillEntry;
    }

    /**
     * 查找单个purchase
     *
     * @param id
     * @return
     */
    public PurchaseBill get(String id) {
        return purchaseBillDao.get(id);
    }

    /**
     * 状态修改
     *
     * @param id
     * @param status   -1 删除；0 暂存； 1 待采购； 2 已采购  状态为2时自动生成入库单
     */
    @SaveLog(message = "采购单状态修改")
    public PurchaseBill updateStatus(String id, Integer status) {

        purchaseBillDao.updateStatus(id, status);
        PurchaseBill purchase = purchaseBillDao.get(id);

        if (status == 1) {

            List<String> sn = new ArrayList<>();
            sn.add(purchase.getSn());
            List<PurchaseBillEntry> list = purchaseBillEntryDao.getEntryBy(null, null, null, sn);
            for (PurchaseBillEntry entry : list) {
                Stocks newStocks = new Stocks();
                newStocks.setGoods(entry.getGoods());
                newStocks.setTransitQuantity(entry.getQuantity());
                newStocks.setOrgan(purchase.getOrgan());
                newStocks.setOwner(purchase.getCreator());
                stocksDao.saveOrUpdate(newStocks);
            }
        }

        if (status == 2){
            List<String> sn = new ArrayList<>();
            sn.add(purchase.getSn());
            List<PurchaseBillEntry> list = purchaseBillEntryDao.getEntryBy(null, null, null, sn);
            StorageBill storageBill = new StorageBill();
            storageBill.setDirection(StorageBill.Direction.IN);
            storageBill.setCreator(purchase.getCreator());
            storageBill.setOrgan(purchase.getOrgan());
            storageBill.setSn(snDao.gen("RKD", purchase.getOrganId()));
            storageBill.setPurchaseBill(purchase);
            storageBillDao.saveOrUpdate(storageBill);
            for (PurchaseBillEntry p : list){
                StorageBillEntry storageBillEntry = new StorageBillEntry();
                storageBillEntry.setOwner(storageBill);
                storageBillEntry.setGoods(p.getGoods());
                storageBillEntry.setQuantity(p.getQuantity());
                storageBillEntryDao.saveOrUpdate(storageBillEntry);
            }
        }
        return purchase;
    }
}
