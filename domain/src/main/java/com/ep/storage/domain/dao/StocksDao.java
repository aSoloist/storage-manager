package com.ep.storage.domain.dao;

import com.ep.commons.domain.dao.BaseDao;
import com.ep.commons.domain.model.HqlArgs;
import com.ep.storage.domain.model.Stocks;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 库存数据访问层
 *
 * Created by ly on 17-8-9
 */
@Repository
public class StocksDao extends BaseDao<Stocks, String>{

    /**
     * 1 Record 某一时间段的库存记录
     * 2 State 某一时间段的报表统计
     * 3 Info 目前的库存信息
     */
    public enum Function {
        Record, State, Info
    }

    /**
     *
     * @param ownerId
     * @param organId
     * @param status
     * @param goodsId
     * @param goodsName
     * @param beginTime
     * @param endTime
     * @return
     */
    private HqlArgs getHqlArgs(List<String> ownerId, List<String> organId, List<Integer> status, List<String> goodsId, List<String> goodsName, Date beginTime, Date endTime){
        String hql = "from Stocks where 1=1";
        Map<String, Object> map = new HashMap<>();

        if (ownerId != null && ownerId.size() > 0){
            if (ownerId.size() == 1){
                hql += " and owner.id = :ownerId";
                map.put("ownerId", ownerId.get(0));
            } else {
                hql += " and owner.id in :ownerId";
                map.put("ownerId", ownerId);
            }
        }

        if (organId != null && organId.size() > 0){
            if (organId.size() == 1){
                hql += " and organ.id = :organId";
                map.put("organId", organId.get(0));
            } else {
                hql += " and organ.id in :organId";
                map.put("organId", organId);
            }
        }

        if (status != null && status.size() > 0) {
            if (status.size() == 1) {
                hql += " and status = :status";
                map.put("status", status.get(0));
            } else {
                hql += " and status in :status";
                map.put("status", status);
            }
        } else {
            hql += " and status <> -1";
        }

        if (goodsId != null && goodsId.size() > 0){
            if (goodsId.size() == 1){
                hql += " and goods.id = :goodsId";
                map.put("goodsId", goodsId.get(0));
            } else {
                hql += " and goods.id in :goodsId";
                map.put("goodsId", goodsId);
            }
        }

        if (goodsName != null && goodsName.size() > 0){
            if (goodsName.size() == 1){
                hql += " and goods.name = :goodsName";
                map.put("goodsName", goodsName.get(0));
            } else {
                hql += " and goods.name in :goodsName";
                map.put("goodsName", goodsName);
            }
        }

        if (beginTime != null && endTime != null){
            hql += " and createTime between :beginTime and :endTime";
            map.put("beginTime", beginTime);
            map.put("endTime", endTime);
        } else if (endTime != null){
            hql += " and createTime between :beginTime and :endTime";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                map.put("beginTime", sdf.parse("2017-07-01"));
            } catch (ParseException e) {
            }
            map.put("endTime", endTime);
        }

        return new HqlArgs(hql, map);
    }

    /**
     * 获取库存分页
     *
     * @param organId
     * @param status
     * @param startIndex
     * @param rows
     * @return
     */
    public List<Stocks> getAll(List<String> organId, List<Integer> status, Integer startIndex, Integer rows){
        HqlArgs hqlArgs = getHqlArgs(null, organId, status, null, null, null, null);
        String hql = hqlArgs.getHql();
        hql += " order by createTime desc";
        List<Stocks> list = findByNamedParam(hql, startIndex, rows, hqlArgs.getArgs());
        return list;
    }

    /**
     * 获取库存数量
     *
     * @param organId
     * @param status
     * @return
     */
    public Integer getCount(List<String> organId, List<Integer> status){
        HqlArgs hqlArgs = getHqlArgs(null, organId, status, null, null, null, null);
        return (int)getCount(hqlArgs.getHql(), hqlArgs.getArgs());
    }

    /**
     * 条件获取库存记录
     *
     * @param ownerId
     * @param organId
     * @param status
     * @param goodsId
     * @param goodsName
     * @param beginTime
     * @param endTime
     * @return
     */
    public List<Stocks> getListBy(List<String> ownerId, List<String> organId, List<Integer> status, List<String> goodsId, List<String> goodsName, Date beginTime, Date endTime) {
        HqlArgs hqlArgs = getHqlArgs(ownerId, organId, status, goodsId, goodsName, beginTime, endTime);
        String hql = hqlArgs.getHql();
        hql += " order by createTime desc";
        List<Stocks> list = findByNamedParam(hql, hqlArgs.getArgs());
        return list;
    }

    /**
     * 条件获取库存记录数量
     *
     * @param ownerId
     * @param organId
     * @param status
     * @param goodsId
     * @param goodsName
     * @param beginTime
     * @param endTime
     * @return
     */
    public Integer getListCount(List<String> ownerId, List<String> organId, List<Integer> status, List<String> goodsId, List<String> goodsName, Date beginTime, Date endTime) {
        HqlArgs hqlArgs = getHqlArgs(ownerId, organId, status, goodsId, goodsName, beginTime, endTime);
        return (int) getCount(hqlArgs.getHql(), hqlArgs.getArgs());
    }

    /**
     * 获取库存信息
     *
     * @param ownerId
     * @param organId
     * @param status
     * @param goodsId
     * @param goodsName
     * @return
     */
    public List<Stocks> getStocksInfo(List<String> ownerId, List<String> organId, List<Integer> status, List<String> goodsId, List<String> goodsName) {
        HqlArgs hqlArgs = getHqlArgs(ownerId, organId, status, goodsId, goodsName, null, null);
        String hql = hqlArgs.getHql();
        hql += " order by createTime desc";
        List<Stocks> list = findByNamedParam(hql, hqlArgs.getArgs());
        return getNow(list);
    }

    /**
     * 获取库存数量
     *
     * @param ownerId
     * @param organId
     * @param status
     * @param goodsId
     * @param goodsName
     * @return
     */
    public Integer getStocksCount(List<String> ownerId, List<String> organId, List<Integer> status, List<String> goodsId, List<String> goodsName) {
        HqlArgs hqlArgs = getHqlArgs(ownerId, organId, status, goodsId, goodsName, null, null);
        List<Stocks> list = findByNamedParam(hqlArgs.getHql(), hqlArgs.getArgs());
        return getNow(list).size();
    }

    /**
     * 报表统计
     *
     * @param ownerId
     * @param organId
     * @param status
     * @param goodsId
     * @param goodsName
     * @param beginTime
     * @param endTime
     * @return
     */
    public List<Stocks> getReportForm(List<String> ownerId, List<String> organId, List<Integer> status, List<String> goodsId, List<String> goodsName, Date beginTime, Date endTime) {
        HqlArgs hqlArgs = getHqlArgs(ownerId, organId, status, goodsId, goodsName, beginTime, endTime);
        String hql = hqlArgs.getHql();
        List<Stocks> list = findByNamedParam(hql, hqlArgs.getArgs());
        return getNow(list, beginTime, endTime);
    }

    private List<Stocks> getNow(List<Stocks> stocksList){
        Date endTime = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date beginTime = null;
        try {
            beginTime = sdf.parse("2017-7-1");
        } catch (ParseException ignored) {
        }
        return getNow(stocksList, beginTime, endTime);
    }

    private List<Stocks> getNow(List<Stocks> stocksList, Date beginTime, Date endTime){
        List<Stocks> ulist = new ArrayList<>();
        ulist.add(stocksList.get(0));
        for (Stocks s : stocksList){
            for (Stocks s2 : ulist){
                if (!(s.getOwnerId().equals(s2.getOwnerId()) && s.getGoodsId().equals(s2.getGoodsId()))){
                    ulist.add(s);
                }
            }
        }
        List<Stocks> list = new ArrayList<>();
        for (Stocks stocks : ulist){
            String sql = "select sum(inventory_quantity) as inventory, sum(transit_quantity) as transit, sum(frozen_quantity) as frozen from storage_stocks where owner_id = ? and goods_id = ? and create_time between ? and ?";
            Stocks newStocks = new Stocks();
            Map map = findUniqueBySQL(sql, stocks.getOwnerId(), stocks.getGoodsId(), beginTime, endTime);
            newStocks.setOwner(stocks.getOwner());
            newStocks.setGoods(stocks.getGoods());
            newStocks.setOrgan(stocks.getOrgan());
            newStocks.setInventoryQuantity((BigDecimal) map.get("inventory"));
            newStocks.setTransitQuantity((BigDecimal) map.get("transit"));
            newStocks.setFrozenQuantity((BigDecimal) map.get("frozen"));
            list.add(newStocks);
        }
        return list;
    }

    /**
     * 状态修改
     *
     * @param id
     */
    public void updateStatus(String id, Integer status) {

        String sql = " UPDATE storage_stocks SET status = ? WHERE id = ?";
        Session session = this.getSession();
        SQLQuery sqlQuery = session.createSQLQuery(sql);
        sqlQuery.setInteger(0, status);
        sqlQuery.setString(1, id);
        sqlQuery.executeUpdate();
    }
}
