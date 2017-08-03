package com.ep.storage.domain.model;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Set;

/**
 * 采购单据
 *
 * Created by ly on 17-8-1
 */

@Entity
@Table(name = "storage_purchase")
public class PurchaseBill extends AbstractBill {

    @OneToMany(mappedBy = "purchase")
    @Cascade(value = {org.hibernate.annotations.CascadeType.ALL})
    private Set<PurchaseBillEntry> billEntries;//采购分录

    private String remark;//备注

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Set<PurchaseBillEntry> getBillEntries() {
        return billEntries;
    }

    public void setBillEntries(Set<PurchaseBillEntry> billEntries) {
        this.billEntries = billEntries;
    }
}
