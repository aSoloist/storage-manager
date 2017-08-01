package com.ep.storage.domain.model;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 采购单据分录
 *
 * Created by ly on 17-8-1
 *
 */

@Entity
@Table(name = "storage_purchase_entry")
public class PurchaseBillEntry extends AbstractBillEntry {
    @Column(name = "price", nullable = false)
    private BigDecimal price;//单价

    @Transient
    private BigDecimal total = getPrice().multiply(getQuantity());//总价

    @JSONField(serialize = false)
    @ManyToOne
    @JoinColumn(name = "purchase_id", nullable = false)
    private PurchaseBill purchase;//采购单

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public PurchaseBill getPurchase() {
        return purchase;
    }

    public void setPurchase(PurchaseBill purchase) {
        this.purchase = purchase;
    }
}
