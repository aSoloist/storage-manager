package com.ep.storage.domain.dao;

import com.ep.commons.domain.dao.BaseDao;
import com.ep.commons.domain.model.HqlArgs;
import com.ep.storage.domain.model.PurchaseBillEntry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PurchaseBillEntryDao extends BaseDao<PurchaseBillEntry, String>{

    /**
     * 获取查询语句
     *
     * @param goodsId
     * @param goodsName
     * @param status
     * @param purchaseSn
     */
    public HqlArgs getHqlArgs(List<String> goodsId, List<String> goodsName, List<Integer> status, List<String> purchaseSn){
        String hql = "from PurchaseBillEntry where 1=1";
        Map<String, Object> map = new HashMap<>();
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

        if (purchaseSn != null && purchaseSn.size() > 0){
            if (purchaseSn.size() == 1){
                hql += " and purchase.sn = :purchaseSn";
                map.put("purchaseSn", purchaseSn.get(0));
            } else {
                hql += " and purchase.sn in :purchaseSn";
                map.put("purchaseSn", purchaseSn);
            }
        }

        return new HqlArgs(hql, map);
    }

    /**
     * 获取单据下所有分录
     *
     * @param status
     * @param purchaseSn
     */
    public List<PurchaseBillEntry> getAll(List<Integer> status, List<String> purchaseSn){
        HqlArgs hqlArgs = getHqlArgs(null, null, status, purchaseSn);
        String hql = hqlArgs.getHql();
        hql += " order by createTime desc";
        List<PurchaseBillEntry> list = findByNamedParam(hql, hqlArgs.getArgs());
        return list;
    }

    /**
     * 获取单据下所有分录数量
     *
     * @param status
     * @param purchaseSn
     */
    public Integer getCount(List<Integer> status, List<String> purchaseSn){
        HqlArgs hqlArgs = getHqlArgs(null, null, status, purchaseSn);
        return (int) getCount(hqlArgs.getHql(), hqlArgs.getArgs());
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
        HqlArgs hqlArgs = getHqlArgs(goodsId, goodsName, status, purchaseSn);
        String hql = hqlArgs.getHql();
        hql += " order by createTime desc";
        List<PurchaseBillEntry> list = findByNamedParam(hql, hqlArgs.getArgs());
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
        HqlArgs hqlArgs = getHqlArgs(goodsId, goodsName, status, purchaseSn);
        return (int) getCount(hqlArgs.getHql(), hqlArgs.getArgs());
    }
}
