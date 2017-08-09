package com.ep.storage.domain.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.ep.commons.domain.model.AbstractVersionModel;
import com.ep.commons.domain.model.Organ;
import com.ep.commons.domain.model.User;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 库存
 *
 * Created by ly on 17-8-9
 */
@Entity
@Table(name = "storage_stocks")
public class Stocks extends AbstractVersionModel{

    @JSONField(serialize = false)
    @ManyToOne
    @JoinColumn(name = "owner_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private User owner;//拥有者

    @Transient
    private String ownerId;

    @Transient
    private String ownerName;

    @JSONField(serialize = false)
    @ManyToOne
    @JoinColumn(name = "organ_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private Organ organ; // 所属组织

    @Transient
    private String organId;

    @Transient
    private String organName;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_time", nullable = false, updatable = false)
    private Date createTime = new Date(); //创建时间

    @NotNull
    @Column(nullable = false)
    private Integer status = Integer.valueOf(0); // -1 标记删除 0 创建 1 生效

    @ManyToOne
    @JoinColumn(name = "goods_id")
    @JSONField(serialize = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private Goods goods; // 商品

    @Transient
    private String goodsId;

    @Transient
    private String goodsName;

    @Column(name = "inventory_quantity", nullable = false)
    private BigDecimal inventoryQuantity = BigDecimal.ZERO; //库存数量

    @Column(name = "transit_quantity", nullable = false)
    private BigDecimal transitQuantity = BigDecimal.ZERO; //在途数量

    @Column(name = "frozen_quantity", nullable = false)
    private BigDecimal frozenQuantity = BigDecimal.ZERO; //冻结数量

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getOwnerId() {
        if (this.ownerId != null){
            return this.ownerId;
        }

        if (this.owner != null){
            return this.owner.getId();
        }

        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerName() {
        if (this.ownerName != null){
            return this.ownerName;
        }

        if (this.owner != null){
            return this.owner.getName();
        }

        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Organ getOrgan() {
        return organ;
    }

    public void setOrgan(Organ organ) {
        this.organ = organ;
    }

    public String getOrganId() {
        if (this.organId != null){
            return this.organId;
        }

        if (this.organ != null){
            return this.organ.getId();
        }

        return organId;
    }

    public void setOrganId(String organId) {
        this.organId = organId;
    }

    public String getOrganName() {
        if (this.ownerName != null){
            return this.organName;
        }

        if (this.organ != null){
            return this.organ.getName();
        }

        return organName;
    }

    public void setOrganName(String organName) {
        this.organName = organName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public String getGoodsId() {
        if (this.goodsId != null){
            return this.goodsId;
        }

        if (this.goods != null){
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

    public BigDecimal getInventoryQuantity() {
        return inventoryQuantity;
    }

    public void setInventoryQuantity(BigDecimal inventoryQuantity) {
        this.inventoryQuantity = inventoryQuantity;
    }

    public BigDecimal getTransitQuantity() {
        return transitQuantity;
    }

    public void setTransitQuantity(BigDecimal transitQuantity) {
        this.transitQuantity = transitQuantity;
    }

    public BigDecimal getFrozenQuantity() {
        return frozenQuantity;
    }

    public void setFrozenQuantity(BigDecimal frozenQuantity) {
        this.frozenQuantity = frozenQuantity;
    }
}
