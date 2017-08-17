package com.ep.storage.domain.service;

import com.ep.annotation.ServiceLog;
import com.ep.commons.domain.service.IService;
import com.ep.storage.domain.dao.StocksDao;
import com.ep.storage.domain.dao.StorageBillDao;
import com.ep.storage.domain.dao.StorageBillEntryDao;
import com.ep.storage.domain.model.Stocks;
import com.ep.storage.domain.model.StorageBill;
import com.ep.storage.domain.model.StorageBillEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 出入库单服务类
 *
 * Created by ly on 17-8-5
 */
@Service
@Transactional
public class StorageBillService implements IService<StorageBill, String> {

    @Autowired
    private StorageBillDao storageBillDao;

    @Autowired
    private StorageBillEntryDao storageBillEntryDao;

    @Autowired
    private StocksDao stocksDao;

    /**
     * 保存或修改出入单
     *
     * @param storageBill
     */
    @ServiceLog(description = "保存或修改出入单")
    public void saveOrUpdate(StorageBill storageBill) {
        this.storageBillDao.saveOrUpdate(storageBill);
    }

    public void delete(String s) { }

    /**
     * 状态修改
     *
     * @param id
     */
    @ServiceLog(description = "状态修改")
    public void update(String id, Integer status) {
        this.storageBillDao.updateStatus(id, status);

        if (status == 1){
            StorageBill storageBill = storageBillDao.get(id);
            List<String> sn = new ArrayList<>();
            sn.add(storageBill.getSn());
            List<StorageBillEntry> list = storageBillEntryDao.getListBy(null, null, null, sn);
            if (storageBill.getDirection().equals(StorageBill.Direction.IN)){
                for (StorageBillEntry entry : list){
                    Stocks stocks = new Stocks();
                    stocks.setOwner(storageBill.getCreator());
                    stocks.setOrgan(storageBill.getOrgan());
                    stocks.setGoods(entry.getGoods());
                    stocks.setTransitQuantity(entry.getQuantity().negate());
                    stocks.setInventoryQuantity(entry.getQuantity());
                    stocksDao.saveOrUpdate(stocks);
                }
            } else if (storageBill.getDirection().equals(StorageBill.Direction.OUT)){
                for (StorageBillEntry entry : list){
                    Stocks stocks = new Stocks();
                    stocks.setOwner(storageBill.getCreator());
                    stocks.setOrgan(storageBill.getOrgan());
                    stocks.setGoods(entry.getGoods());
                    stocks.setFrozenQuantity(entry.getQuantity().negate());
                    stocks.setInventoryQuantity(entry.getQuantity().negate());
                    stocksDao.saveOrUpdate(stocks);
                }
            }
        }
    }

    /**
     * 获取单个单据
     *
     * @param id
     * @return
     */
    @ServiceLog(description = "获取单个单据")
    public StorageBill get(String id) {
        return storageBillDao.get(id);
    }

    @Override
    public List<StorageBill> list(String param, List<String> organIds, List<String> userIds, List<Integer> status, Integer startIndex, Integer rows) {
        return null;
    }

    /**
     * 获取出入单分页
     *
     * @param organId
     * @param status
     * @param direction
     * @param startIndex
     * @param rows
     * @return
     */
    @ServiceLog(description = "获取出入单分页")
    public List<StorageBill> getList(List<String> organId, List<Integer> status, StorageBill.Direction direction, Integer startIndex, Integer rows){
        return storageBillDao.getAll(organId, status, direction, startIndex, rows);
    }

    @Override
    public long listSize(String param, List<String> organIds, List<String> userIds, List<Integer> status) {
        return 0;
    }

    /**
     * 获取单据数量
     *
     * @param organId
     * @param status
     * @param direction
     * @return
     */
    public Integer getListSize(List<String> organId, List<Integer> status, StorageBill.Direction direction){
        return storageBillDao.getCount(organId, status, direction);
    }

    /**
     * 条件获取出入单
     *
     * @param organId
     * @param creatorId
     * @param direction
     * @param status
     * @param storageSn
     * @return
     */
    @ServiceLog(description = "条件获取出入单")
    public List<StorageBill> getListBy(List<String> organId, List<String> creatorId, StorageBill.Direction direction, List<Integer> status, List<String> storageSn){
        return storageBillDao.getListBy(organId, creatorId, direction, status, storageSn);
    }

    /**
     * 条件获取出入单数量
     *
     * @param organId
     * @param creatorId
     * @param direction
     * @param status
     * @param storageSn
     * @return
     */
    @ServiceLog(description = "条件获取出入单数量")
    public Integer getListSizeBy(List<String> organId, List<String> creatorId, StorageBill.Direction direction, List<Integer> status, List<String> storageSn) {
        return storageBillDao.getCountBy(organId, creatorId, direction, status, storageSn);
    }

    /**
     * 获取单据下所有分录
     *
     * @param status
     * @param ownerSn
     * @return
     */
    @ServiceLog(description = "获取单据下所有分录")
    public List<StorageBillEntry> getEntryList(List<Integer> status, List<String> ownerSn){
        return storageBillEntryDao.getAll(status, ownerSn);
    }

    /**
     * 获取单据下分录数量
     *
     * @param status
     * @param ownerSn
     * @return
     */
    @ServiceLog(description = "获取单据下分录数量")
    public Integer getEntryCount(List<Integer> status, List<String> ownerSn){
        return storageBillEntryDao.getCount(status, ownerSn);
    }

    /**
     * 条件获取分录
     *
     * @param goodsId
     * @param goodsName
     * @param status
     * @param ownerSn
     * @return
     */
    @ServiceLog(description = "条件获取分录")
    public List<StorageBillEntry> getEntryListBy(List<String> goodsId, List<String> goodsName, List<Integer> status, List<String> ownerSn){
        return storageBillEntryDao.getListBy(goodsId, goodsName, status, ownerSn);
    }

    /**
     * 条件获取分录数量
     *
     * @param goodsId
     * @param goodsName
     * @param status
     * @param ownerSn
     * @return
     */
    @ServiceLog(description = "条件获取分录数量")
    public Integer getEntryCountBy(List<String> goodsId, List<String> goodsName, List<Integer> status, List<String> ownerSn){
        return storageBillEntryDao.getCountBy(goodsId, goodsName, status, ownerSn);
    }

    /**
     * 保存或者修改出入单分录
     *
     * @param storageBillEntry
     */
    @ServiceLog(description = "保存或者修改出入单分录")
    public void saveOrUpdateEntry(StorageBillEntry storageBillEntry){
        storageBillEntryDao.saveOrUpdate(storageBillEntry);

        StorageBill storageBill = storageBillDao.get(storageBillEntry.getOwnerId());
        if (storageBill.getDirection().equals(StorageBill.Direction.OUT)){
            Stocks stocks = new Stocks();
            stocks.setOwner(storageBill.getCreator());
            stocks.setOrgan(storageBill.getOrgan());
            stocks.setGoods(storageBillEntry.getGoods());
            stocks.setFrozenQuantity(storageBillEntry.getQuantity());
            stocksDao.saveOrUpdate(stocks);
        }
    }
}
