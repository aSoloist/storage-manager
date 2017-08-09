package com.ep.storage.domain.service;

import com.ep.storage.domain.dao.StocksDao;
import com.ep.storage.domain.model.Stocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StocksService {
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
     * @return
     */
    public List<Stocks> getListBy(List<String> ownerId, List<String> organId, List<Integer> status, List<String> goodsId, List<String> goodsName){
        return stocksDao.getListBy(ownerId, organId, status, goodsId, goodsName);
    }

    /**
     * 条件获取库存数量
     *
     * @param ownerId
     * @param organId
     * @param status
     * @param goodsId
     * @param goodsName
     * @return
     */
    public Integer getCountBy(List<String> ownerId, List<String> organId, List<Integer> status, List<String> goodsId, List<String> goodsName){
        return stocksDao.getListCount(ownerId, organId, status, goodsId, goodsName);
    }

    /**
     * 查找单个库存
     *
     * @param id
     * @return
     */
    public Stocks getOne(String id){
        return stocksDao.get(id);
    }

    /**
     * 保存或修改库存
     *
     * @param stocks
     */
    public void saveOrUpdate(Stocks stocks){
        stocksDao.saveOrUpdate(stocks);
    }

    /**
     * 状态修改
     *
     * @param id
     * @param status
     */
    public void updateStatus(String id, Integer status){
        stocksDao.updateStatus(id, status);
    }
}
