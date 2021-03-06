package com.ep.storage.domain.service;

import com.ep.commons.domain.service.IService;
import com.ep.commons.tool.annotation.SaveLog;
import com.ep.storage.domain.dao.StocksDao;
import com.ep.storage.domain.model.Stocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class StocksService implements IService<Stocks, String> {
    @Autowired
    StocksDao stocksDao;

    /**
     * 获取库存分页
     *
     * @param organId
     * @param status
     * @param startIndex
     * @param rows
     * @return
     */
    public List<Stocks> getList(List<String> organId, List<Integer> status, Integer startIndex, Integer rows){
        return stocksDao.getAll(organId, status, startIndex, rows);
    }

    /**
     * 获取库存数量
     *
     * @param organId
     * @param status
     * @return
     */
    public Integer getCount(List<String> organId, List<Integer> status){
        return stocksDao.getCount(organId, status);
    }

    /**
     * 条件获取库存
     *
     * @param ownerId
     * @param organId
     * @param status
     * @param goodsId
     * @param goodsName
     * @param beginTime
     * @param endTime
     * @param function
     * @return
     */
    public List<Stocks> getListBy(List<String> ownerId, List<String> organId, List<Integer> status, List<String> goodsId, List<String> goodsName, Date beginTime, Date endTime, StocksDao.Function function){
        if (function.equals(StocksDao.Function.Info)){
            return stocksDao.getStocksInfo(ownerId, organId, status, goodsId, goodsName);
        } else if (function.equals(StocksDao.Function.Record)){
            return stocksDao.getListBy(ownerId, organId, status, goodsId, goodsName, beginTime, endTime);
        } else if (function.equals(StocksDao.Function.State)){
            return stocksDao.getReportForm(ownerId, organId, status, goodsId, goodsName, beginTime, endTime);
        }

        return stocksDao.getStocksInfo(ownerId, organId, status, goodsId, goodsName);
    }

    /**
     * 条件获取库存数量
     *
     * @param ownerId
     * @param organId
     * @param status
     * @param goodsId
     * @param goodsName
     * @param beginTime
     * @param endTime
     * @param function
     * @return
     */
    public Integer getCountBy(List<String> ownerId, List<String> organId, List<Integer> status, List<String> goodsId, List<String> goodsName, Date beginTime, Date endTime, StocksDao.Function function){
        if (function.equals(StocksDao.Function.Record)){
            return stocksDao.getListCount(ownerId, organId, status, goodsId, goodsName, beginTime, endTime);
        } else if(function.equals(StocksDao.Function.Info)){
            return stocksDao.getStocksCount(ownerId, organId, status, goodsId, goodsName);
        }

        return stocksDao.getStocksCount(ownerId, organId, status, goodsId, goodsName);
    }

    /**
     * 查找单个库存
     *
     * @param id
     * @return
     */
    public Stocks get(String id){
        return stocksDao.get(id);
    }

    /**
     * 保存或修改库存
     *
     * @param stocks
     */
    @SaveLog(message = "保存或修改库存")
    public Stocks saveOrUpdate(Stocks stocks){
        stocksDao.saveOrUpdate(stocks);
        return stocks;
    }

    /**
     * 删除库存
     *
     * @param id
     * @return
     */
    @SaveLog(message = "删除库存", clazz = Stocks.class)
    public String delete(String id) {
        stocksDao.updateStatus(id, -1);
        return id;
    }

    @Override
    public List<Stocks> list(String s, List<String> list, List<String> list1, List<Integer> list2, Integer integer, Integer integer1) {
        return null;
    }

    @Override
    public long listSize(String s, List<String> list, List<String> list1, List<Integer> list2) {
        return 0;
    }

    /**
     * 状态修改
     *
     * @param id
     * @param status
     */
    @SaveLog(message = "库存状态修改")
    public Stocks updateStatus(String id, Integer status){
        stocksDao.updateStatus(id, status);
        return stocksDao.get(id);
    }
}
