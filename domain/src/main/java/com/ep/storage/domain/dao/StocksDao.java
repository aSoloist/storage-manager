package com.ep.storage.domain.dao;

import com.ep.commons.domain.dao.BaseDao;
import com.ep.commons.domain.model.HqlArgs;
import com.ep.storage.domain.model.Stocks;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 库存数据访问层
 *
 * Created by ly on 17-8-9
 */
@Repository
public class StocksDao extends BaseDao<Stocks, String>{
    /**
     *
     * @param ownerId
     * @param organId
     * @param status
     * @param goodsId
     * @param goodsName
     * @return
     */
    private HqlArgs getHqlArgs(List<String> ownerId, List<String> organId, List<Integer> status, List<String> goodsId, List<String> goodsName){
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
        HqlArgs hqlArgs = getHqlArgs(null, organId, status, null, null);
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
        HqlArgs hqlArgs = getHqlArgs(null, organId, status, null, null);
        return (int)getCount(hqlArgs.getHql(), hqlArgs.getArgs());
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
    public List<Stocks> getListBy(List<String> ownerId, List<String> organId, List<Integer> status, List<String> goodsId, List<String> goodsName) {
        HqlArgs hqlArgs = getHqlArgs(ownerId, organId, status, goodsId, goodsName);
        String hql = hqlArgs.getHql();
        hql += " order by createTime desc";
        List<Stocks> list = findByNamedParam(hql, hqlArgs.getArgs());
        return list;
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
    public Integer getListCount(List<String> ownerId, List<String> organId, List<Integer> status, List<String> goodsId, List<String> goodsName) {
        HqlArgs hqlArgs = getHqlArgs(ownerId, organId, status, goodsId, goodsName);
        return (int) getCount(hqlArgs.getHql(), hqlArgs.getArgs());
    }

    public Stocks getOne(String ownerId, String goodsId){
        String hql = "from Stocks where owner.id = ? and goods.id = ?";
        return findUnique(hql, ownerId, goodsId);
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
