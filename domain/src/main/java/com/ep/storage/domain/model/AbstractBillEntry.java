package com.ep.storage.domain.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.ep.commons.domain.model.AbstractModel;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 单据分录基类
 *
 * @author 文卡<wkwenka@gmail.com>  on 17-8-1.
 */
@MappedSuperclass
public class AbstractBillEntry extends AbstractModel {


    @ManyToOne
    @JoinColumn(name = "goods_id")
    @JSONField(serialize = false)
    private Goods goods; // 商品

    @Column(nullable = false)
    private BigDecimal quantity = BigDecimal.ZERO; // 数量

    @Transient
    private String goodsId;

    @Transient
    private String goodsName;

    private Integer status = Integer.valueOf(0); //状态

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_time")
    private Date createTime = new Date();


    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getGoodsId() {

        if (this.goodsId != null) {
            return this.goodsId;
        }

        if (this.goods != null) {
            return this.goods.getId();
        }

        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {

        if (this.goodsName != null) {
            return this.goodsName;
        }

        if (this.goods != null) {
            return this.goods.getName();
        }

        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
