package com.ep.storage.domain.model;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 采购单据分录
 *
 * Created by ly on 17-8-1
 *
 */

@Entity
@Table(name = "storage_purchase_bill_entry")
public class PurchaseBillEntry extends AbstractBillEntry {
    @Column(name = "price", nullable = false)
    private BigDecimal price;//单价

    @ManyToOne
    @JoinColumn(name = "purchase_id")
    private PurchaseBill purchase;//采购单

    @Transient
    private String sn; //所属单号

    @Transient
    private String purchaseId; //单据Id

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public PurchaseBill getPurchase() {
        return purchase;
    }

    public void setPurchase(PurchaseBill purchase) {
        this.purchase = purchase;
    }

    public String getSn() {

        if (this.sn != null) {
            return this.sn;
        }

        if (this.purchase != null) {
            return this.purchase.getSn();
        }

        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getPurchaseId() {

        if (this.purchaseId != null) {
            return this.purchaseId;
        }

        if (this.purchase != null) {
            return this.purchase.getId();
        }

        return purchaseId;
    }

    public void setPurchaseId(String purchaseId) {
        this.purchaseId = purchaseId;
    }

    public String toString(){
        return "所属单号：" + this.sn;
    }
}
