package com.ep.storage.domain.service;

import com.ep.commons.domain.service.IService;
import com.ep.storage.domain.dao.StorageBillDao;
import com.ep.storage.domain.dao.StorageBillEntryDao;
import com.ep.storage.domain.model.StorageBill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 出入库单服务类
 *
 * @author 文卡<wkwenka@gmail.com>  on 17-8-3.
 */
@Service
public class StorageBillService implements IService<StorageBill, String> {

    @Autowired
    private StorageBillDao storageBillDao;

    @Autowired
    private StorageBillEntryDao storageBillEntryDao;

    @Override
    public void saveOrUpdate(StorageBill storageBill) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public StorageBill get(String s) {
        return null;
    }

    @Override
    public List<StorageBill> list(String param, List<String> organIds, List<String> userIds, List<Integer> status, Integer startIndex, Integer rows) {
        return null;
    }

    @Override
    public long listSize(String param, List<String> organIds, List<String> userIds, List<Integer> status) {
        return 0;
    }
}
