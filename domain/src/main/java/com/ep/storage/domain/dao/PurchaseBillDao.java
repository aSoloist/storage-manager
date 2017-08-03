package com.ep.storage.domain.dao;

import com.ep.commons.domain.dao.BaseDao;
import com.ep.commons.domain.model.HqlArgs;
import com.ep.storage.domain.model.PurchaseBill;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 采购单数据访问层
 *
 * Created by ly on 17-8-1
 */

@Repository
public class PurchaseBillDao extends BaseDao<PurchaseBill, String> {

    /**
     * 获取查询语句
     *
     * @param organId
     * @param creatorId
     * @param status
     * @param purchaseSn
     */
    public HqlArgs getHqlArgs(List<String> organId, List<String> creatorId, List<Integer> status, List<String> purchaseSn){
        String hql = "from PurchaseBill where 1=1";
        Map<String, Object> map = new HashMap<>();
        if (organId != null && organId.size() > 0){
            if (organId.size() == 1){
                hql += " and organ.id = :organId";
                map.put("organId", organId.get(0));
            } else {
                hql += " and organ.id in :organId";
                map.put("organId", organId);
            }
        }

        if (creatorId != null && creatorId.size() > 0){
            if (creatorId.size() == 1){
                hql += " and creator.id = :creatorId";
                map.put("creatorId", creatorId.get(0));
            } else {
                hql += " and creator.id in :creatorId";
                map.put("creatorId", creatorId);
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
                hql += " and sn = :purchaseSn";
                map.put("purchaseSn", purchaseSn.get(0));
            } else {
                hql += " and sn in :purchaseSn";
                map.put("purchaseSn", purchaseSn);
            }
        }
        return new HqlArgs(hql, map);
    }

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
        HqlArgs hqlArgs = getHqlArgs(organId, null, status, null);
        String hql = hqlArgs.getHql();
        hql += " order by createTime desc";
        List<PurchaseBill> list = findByNamedParam(hql, startIndex, rows, hqlArgs.getArgs());
        return list;
    }

    /**
     * 查询采购单数量
     *
     * @param organId
     * @param status
     * @return
     */
    public Integer getPurchaseCount(List<String> organId, List<Integer> status){
        HqlArgs hqlArgs = getHqlArgs(organId, null, status, null);
        return (int) getCount(hqlArgs.getHql(), hqlArgs.getArgs());
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
        HqlArgs hqlArgs = getHqlArgs(organId, creatorId, status, purchaseSn);
        String hql = hqlArgs.getHql();
        hql += " order by createTime desc";
        List<PurchaseBill> list = findByNamedParam(hql, hqlArgs.getArgs());
        return list;
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
        HqlArgs hqlArgs = getHqlArgs(organId, creatorId, status, purchaseSn);
        return (int) getCount(hqlArgs.getHql(), hqlArgs.getArgs());
    }

    /**
     * 状态修改
     *
     * @param id
     */
    public void updateStatus(String id, Integer status) {

        String sql = " UPDATE storage_purchase SET status = ? WHERE id = ?";
        Session session = this.getSession();
        SQLQuery sqlQuery = session.createSQLQuery(sql);
        sqlQuery.setInteger(0, status);
        sqlQuery.setString(1, id);
        sqlQuery.executeUpdate();
    }
}
