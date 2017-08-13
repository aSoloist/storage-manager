package com.ep.storage.web.controller;

import com.ep.commons.domain.model.Pagination;
import com.ep.commons.tool.util.StringToEnumConverterFactory;
import com.ep.commons.web.controller.BaseController;
import com.ep.storage.domain.dao.StocksDao;
import com.ep.storage.domain.model.Stocks;
import com.ep.storage.domain.service.StocksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by ly on 17-8-9
 */

@RestController
@RequestMapping("/stocks")
public class StocksController extends BaseController{
    @Autowired
    StocksService stocksService;

    /**
     * 获取库存分页
     *
     * @param organId
     * @param status
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping(value = "/getList", method = RequestMethod.GET)
    public Pagination<Stocks> getList(@RequestParam(required = false)List<String> organId,
                                      @RequestParam(required = false)List<Integer> status,
                                      @RequestParam(required = false)Integer page,
                                      @RequestParam(required = false)Integer rows){
        Pagination<Stocks> stocksPagination = new Pagination<Stocks>(page, rows);
        Integer startIndex = stocksPagination.getStartIdx();
        stocksPagination.setCount(stocksService.getCount(organId, status));
        stocksPagination.setRecords(stocksService.getList(organId, status, startIndex, rows));
        return stocksPagination;
    }

    /**
     * 获取库存数量
     *
     * @param organId
     * @param status
     * @return
     */
    @RequestMapping(value = "/getCount", method = RequestMethod.GET)
    public Integer getCount(@RequestParam(required = false)List<String> organId,
                            @RequestParam(required = false)List<Integer> status){
        return stocksService.getCount(organId, status);
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
    @RequestMapping(value = "/getListBy", method = RequestMethod.GET)
    public List<Stocks> getListBy(@RequestParam(required = false)List<String> ownerId,
                                  @RequestParam(required = false)List<String> organId,
                                  @RequestParam(required = false)List<Integer> status,
                                  @RequestParam(required = false)List<String> goodsId,
                                  @RequestParam(required = false)List<String> goodsName,
                                  @RequestParam(required = false)String beginTime,
                                  @RequestParam(required = false)String endTime,
                                  @RequestParam(required = true)String function) throws ParseException {
        Date begin = null;
        Date end = null;
        if (beginTime != null && !"".equals(beginTime)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            begin = sdf.parse(beginTime);
        }
        if (endTime != null && !"".equals(endTime)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            end = sdf.parse(endTime);
        }

        StocksDao.Function function1 = null;
        if (function != null){
            function1 = new StringToEnumConverterFactory().getConverter(StocksDao.Function.class).convert(function);
        }

        return stocksService.getListBy(ownerId, organId, status, goodsId, goodsName, begin, end, function1);
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
    @RequestMapping(value = "/getCountBy", method = RequestMethod.GET)
    public Integer getCountBy(@RequestParam(required = false)List<String> ownerId,
                              @RequestParam(required = false)List<String> organId,
                              @RequestParam(required = false)List<Integer> status,
                              @RequestParam(required = false)List<String> goodsId,
                              @RequestParam(required = false)List<String> goodsName,
                              @RequestParam(required = false)String beginTime,
                              @RequestParam(required = false)String endTime,
                              @RequestParam(required = true)String function) throws ParseException {
        Date begin = null;
        Date end = null;
        if (beginTime != null && !"".equals(beginTime)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            begin = sdf.parse(beginTime);
        }
        if (endTime != null && !"".equals(endTime)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            end = sdf.parse(endTime);
        }

        StocksDao.Function function1 = null;
        if (function != null){
            function1 = new StringToEnumConverterFactory().getConverter(StocksDao.Function.class).convert(function);
        }

        return stocksService.getCountBy(ownerId, organId, status, goodsId, goodsName, begin, end, function1);
    }

    /**
     * 获取单个库存
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/getOne", method = RequestMethod.GET)
    public Stocks getOne(@RequestParam(required = true)String id){
        return stocksService.getOne(id);
    }

    /**
     * 保存或修改库存
     *
     * @param stocks
     */
    @RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
    public void saveOrUpdate(@RequestBody Stocks stocks){
        stocks.setOwner(this.currentUser);
        stocks.setOrgan(this.primaryOrgan);
        stocksService.saveOrUpdate(stocks);
    }

    /**
     * 状态修改
     *
     * @param id
     * @param status
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public void updateStatus(@RequestParam String id,
                             @RequestParam Integer status) {
        stocksService.updateStatus(id, status);
    }
}
