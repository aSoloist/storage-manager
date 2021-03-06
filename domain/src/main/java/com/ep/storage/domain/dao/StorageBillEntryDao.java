package com.ep.storage.domain.dao;

import com.ep.commons.domain.dao.BaseDao;
import com.ep.commons.domain.model.HqlArgs;
import com.ep.storage.domain.model.StorageBillEntry;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 出入库分录数据访问层
 *
 * Created by ly on 17-8-5
 */
@Repository
public class StorageBillEntryDao extends BaseDao<StorageBillEntry, String> {
    /**
     * 获取查询语句
     *
     * @param goodsId
     * @param goodsName
     * @param status
     * @param ownerSn
     */
    private HqlArgs getHqlArgs(List<String> goodsId, List<String> goodsName, List<Integer> status, List<String> ownerSn){
        String hql = "from StorageBillEntry where 1=1";
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

        if (ownerSn != null && ownerSn.size() > 0){
            if (ownerSn.size() == 1){
                hql += " and owner.sn = :ownerSn";
                map.put("ownerSn", ownerSn.get(0));
            } else {
                hql += " and owner.sn in :purchaseSn";
                map.put("ownerSn", ownerSn);
            }
        }

        return new HqlArgs(hql, map);
    }

    /**
     * 获取单据下所有分录
     *
     * @param status
     * @param ownerSn
     * @return
     */
    public List<StorageBillEntry> getAll(List<Integer> status, List<String> ownerSn){
        HqlArgs hqlArgs = getHqlArgs(null, null, status, ownerSn);
        String hql = hqlArgs.getHql();
        hql += " order by createTime desc";
        List<StorageBillEntry> list = findByNamedParam(hql, hqlArgs.getArgs());
        return list;
    }

    /**
     * 获取单据下所有分录数量
     *
     * @param status
     * @param ownerSn
     * @return
     */
    public Integer getCount(List<Integer> status, List<String> ownerSn){
        HqlArgs hqlArgs = getHqlArgs(null, null, status, ownerSn);
        return (int)getCount(hqlArgs.getHql(), hqlArgs.getArgs());
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
    public List<StorageBillEntry> getListBy(List<String> goodsId, List<String> goodsName, List<Integer> status, List<String> ownerSn){
        HqlArgs hqlArgs = getHqlArgs(goodsId, goodsName, status, ownerSn);
        String hql = hqlArgs.getHql();
        hql += " order by createTime desc";
        List<StorageBillEntry> list = findByNamedParam(hql, hqlArgs.getArgs());
        return list;
    }

    /**
     * 条件获取分录数量
     *
     * @param goodsId
     * @param goodsName
     * @param status
     * @param ownerSn
     */
    public Integer getCountBy(List<String> goodsId, List<String> goodsName, List<Integer> status, List<String> ownerSn){
        HqlArgs hqlArgs = getHqlArgs(goodsId, goodsName, status, ownerSn);
        return (int)getCount(hqlArgs.getHql(), hqlArgs.getArgs());
    }
}
