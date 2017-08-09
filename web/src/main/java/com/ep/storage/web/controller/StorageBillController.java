package com.ep.storage.web.controller;

import com.ep.commons.domain.model.Pagination;
import com.ep.commons.domain.service.SnSerice;
import com.ep.commons.tool.util.StringToEnumConverterFactory;
import com.ep.commons.web.controller.BaseController;
import com.ep.storage.domain.model.StorageBill;
import com.ep.storage.domain.model.StorageBillEntry;
import com.ep.storage.domain.service.StorageBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/storage")
public class StorageBillController extends BaseController{

    @Autowired
    StorageBillService storageBillService;

    @Autowired
    SnSerice snSerice;

    /**
     * 获取出入单据分页
     *
     * @param organId
     * @param status
     * @param direction
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping(value = "/getList", method = RequestMethod.GET)
    public Pagination<StorageBill> getList(@RequestParam(required = false) List<String> organId,
                                           @RequestParam(required = false) List<Integer> status,
                                           @RequestParam(required = true) String direction,
                                           @RequestParam(required = false) Integer page,
                                           @RequestParam(required = false) Integer rows){
        StorageBill.Direction direction1 = null;
        if (direction != null){
            direction1 = new StringToEnumConverterFactory().getConverter(StorageBill.Direction.class).convert(direction);
        }

        Pagination<StorageBill> pagination = new Pagination<StorageBill>(page, rows);
        Integer startIndex = pagination.getStartIdx();
        pagination.setCount(storageBillService.getListSize(organId, status, direction1));
        pagination.setRecords(storageBillService.getList(organId, status, direction1, startIndex, rows));
        return pagination;
    }

    /**
     * 条件获取出入库单
     *
     * @param organId
     * @param creatorId
     * @param direction
     * @param status
     * @param storageSn
     * @return
     */
    @RequestMapping(value = "/getListBy", method = RequestMethod.GET)
    public List<StorageBill> getListBy(@RequestParam(required = false) List<String> organId,
                                       @RequestParam(required = false) List<String> creatorId,
                                       @RequestParam(required = false) String direction,
                                       @RequestParam(required = false) List<Integer> status,
                                       @RequestParam(required = false) List<String> storageSn){
        StorageBill.Direction direction1 = null;
        if (direction != null){
            direction1 = new StringToEnumConverterFactory().getConverter(StorageBill.Direction.class).convert(direction);
        }

        return storageBillService.getListBy(organId, creatorId, direction1, status, storageSn);
    }

    /**
     * 获取出入库单数量
     *
     * @param organId
     * @param status
     * @param direction
     * @return
     */
    @RequestMapping(value = "/getListCount", method = RequestMethod.GET)
    public Integer getListCount(@RequestParam(required = false) List<String> organId,
                                @RequestParam(required = false) List<Integer> status,
                                @RequestParam(required = false) String direction){
        StorageBill.Direction direction1 = null;
        if (direction != null){
            direction1 = new StringToEnumConverterFactory().getConverter(StorageBill.Direction.class).convert(direction);
        }

        return storageBillService.getListSize(organId, status, direction1);
    }

    /**
     * 条件获取出入库单数量
     *
     * @param organId
     * @param creatorId
     * @param direction
     * @param status
     * @param storageSn
     * @return
     */
    @RequestMapping(value = "/getListByCount", method = RequestMethod.GET)
    public Integer getListByCount(@RequestParam(required = false) List<String> organId,
                                  @RequestParam(required = false) List<String> creatorId,
                                  @RequestParam(required = false) String direction,
                                  @RequestParam(required = false) List<Integer> status,
                                  @RequestParam(required = false) List<String> storageSn){
        StorageBill.Direction direction1 = null;
        if (direction != null){
            direction1 = new StringToEnumConverterFactory().getConverter(StorageBill.Direction.class).convert(direction);
        }

        return storageBillService.getListSizeBy(organId, creatorId, direction1, status, storageSn);
    }

    /**
     * 获取单据下所有分录
     *
     * @param status
     * @param storageSn
     * @return
     */
    @RequestMapping(value = "/getEntry", method = RequestMethod.GET)
    public List<StorageBillEntry> getEntryAll(@RequestParam(required = false) List<Integer> status,
                                              @RequestParam(required = true) List<String> storageSn){
        return storageBillService.getEntryList(status, storageSn);
    }

    /**
     * 获取单据下所有分录数量
     *
     * @param status
     * @param storageSn
     */
    @RequestMapping(value = "/getEntryCount", method = RequestMethod.GET)
    public Integer getEntryCount(@RequestParam(required = false) List<Integer> status,
                                 @RequestParam(required = true) List<String> storageSn){
        return storageBillService.getEntryCount(status, storageSn);
    }

    /**
     * 条件获取分录
     *
     * @param goodsId
     * @param goodsName
     * @param status
     * @param ownerSn
     */
    @RequestMapping(value = "/getEntryBy", method = RequestMethod.GET)
    public List<StorageBillEntry> getEntryBy(@RequestParam(required = false) List<String> goodsId,
                                             @RequestParam(required = false) List<String> goodsName,
                                             @RequestParam(required = false) List<Integer> status,
                                             @RequestParam(required = false) List<String> ownerSn){
        return storageBillService.getEntryListBy(goodsId, goodsName, status, ownerSn);
    }

    /**
     * 条件获取单据下分录数量
     *
     * @param goodsId
     * @param goodsName
     * @param status
     * @param ownerSn
     */
    @RequestMapping(value = "/getEntryByCount", method = RequestMethod.GET)
    public Integer getEntryCount(@RequestParam(required = false) List<String> goodsId,
                                 @RequestParam(required = false) List<String> goodsName,
                                 @RequestParam(required = false) List<Integer> status,
                                 @RequestParam(required = false) List<String> ownerSn){
        return storageBillService.getEntryCountBy(goodsId, goodsName, status, ownerSn);
    }

    /**
     * 查询单个出入库单
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/getOne", method = RequestMethod.GET)
    public StorageBill getOne(@RequestParam(required = true) String id){
        return storageBillService.get(id);
    }

    /**
     * 新增或修改
     *
     * @param storageBill
     */
    @RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
    public void saveOrUpdate(@RequestBody StorageBill storageBill) {
        storageBill.setSn(snSerice.gen("RKD", this.primaryOrganId));
        storageBill.setCreator(this.currentUser);
        storageBill.setOrgan(this.primaryOrgan);
        storageBillService.saveOrUpdate(storageBill);
    }

    /**
     * 保存分录
     *
     * @param storageBillEntry
     */
    @RequestMapping(value = "/saveEntry", method = RequestMethod.POST)
    public void saveEntry(@RequestBody StorageBillEntry storageBillEntry){
        storageBillService.saveOrUpdateEntry(storageBillEntry);
    }

    /**
     * 状态修改
     *
     * @param id
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public void updateStatus(@RequestParam String id,
                             @RequestParam Integer status) {
        storageBillService.update(id, status);
    }
}
