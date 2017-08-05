package com.ep.storage.domain.dao;

import com.ep.commons.domain.dao.BaseDao;
import com.ep.commons.domain.model.HqlArgs;
import com.ep.storage.domain.model.StorageBill;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 出入库单据数据访问层
 *
 * Created by ly on 17-8-5
 */
@Repository
public class StorageBillDao extends BaseDao<StorageBill, String> {

    /**
     * 获取查询语句
     *
     * @param organId
     * @param creatorId
     * @param direction
     * @param status
     * @param storageSn
     * @return
     */
    private HqlArgs getHqlArgs(List<String> organId, List<String> creatorId, StorageBill.Direction direction, List<Integer> status, List<String> storageSn){
        String hql = "from StorageBill where 1=1";
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

        if (direction != null && StringUtils.isNoneBlank(direction.toString())){
            hql += " and direction = :direction";
            map.put("direction", direction);
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

        if (storageSn != null && storageSn.size() > 0){
            if (storageSn.size() == 1){
                hql += " and sn = :storageSn";
                map.put("storageSn", storageSn.get(0));
            } else {
                hql += " and sn in :storageSn";
                map.put("storageSn", storageSn);
            }
        }

        return new HqlArgs(hql, map);
    }

    /**
     * 查询出入库单(分页)
     * 按创建时间排序
     *
     * @param organId
     * @param status
     * @param direction
     * @param startIndex
     * @param rows
     * @return
     */
    public List<StorageBill> getAll(List<String> organId, List<Integer> status, StorageBill.Direction direction, Integer startIndex, Integer rows){
        HqlArgs hqlArgs = getHqlArgs(organId, null, direction, status, null);
        String hql = hqlArgs.getHql();
        hql += " order by createTime desc";
        List<StorageBill> list = findByNamedParam(hql, startIndex, rows, hqlArgs.getArgs());
        return list;
    }

    /**
     * 查询所有单据数量
     *
     * @param organId
     * @param status
     * @param direction
     * @return
     */
    public Integer getCount(List<String> organId, List<Integer> status, StorageBill.Direction direction){
        HqlArgs hqlArgs = getHqlArgs(organId, null, direction, status, null);
        return (int)getCount(hqlArgs.getHql(), hqlArgs.getArgs());
    }

    /**
     * 条件查询出入库单据
     *
     * @param organId
     * @param creatorId
     * @param direction
     * @param status
     * @param storageSn
     * @return
     */
    public List<StorageBill> getListBy(List<String> organId, List<String> creatorId, StorageBill.Direction direction, List<Integer> status, List<String> storageSn){
        HqlArgs hqlArgs = getHqlArgs(organId, creatorId, direction, status, storageSn);
        String hql = hqlArgs.getHql();
        hql += " order by createTime desc";
        List<StorageBill> list = findByNamedParam(hql, hqlArgs.getArgs());
        return list;
    }

    /**
     * 条件查询单据数量
     *
     * @param organId
     * @param creatorId
     * @param direction
     * @param status
     * @param storageSn
     * @return
     */
    public Integer getCountBy(List<String> organId, List<String> creatorId, StorageBill.Direction direction, List<Integer> status, List<String> storageSn) {
        HqlArgs hqlArgs = getHqlArgs(organId, creatorId, direction, status, storageSn);
        return (int) getCount(hqlArgs.getHql(), hqlArgs.getArgs());
    }

    /**
     * 状态修改
     *
     * @param id
     */
    public void updateStatus(String id, Integer status) {

        String sql = " UPDATE storage_storage_bill SET status = ? WHERE id = ?";
        Session session = this.getSession();
        SQLQuery sqlQuery = session.createSQLQuery(sql);
        sqlQuery.setInteger(0, status);
        sqlQuery.setString(1, id);
        sqlQuery.executeUpdate();
    }
}
