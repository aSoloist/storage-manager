package com.ep.storage.domain.model;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 采购单据
 *
 * Created by ly on 17-8-1
 */

@Entity
@Table(name = "storage_purchase")
public class PurchaseBill extends AbstractBill {

    private String remark;//备注

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
