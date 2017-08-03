package com.ep.storage.domain.model;

import javax.persistence.*;

/**
 * 出入库分录
 *
 * @author 文卡<wkwenka@gmail.com>  on 17-8-3.
 */
@Entity
@Table(name = "storage_storage_bill_entry")
public class StorageBillEntry extends AbstractBillEntry {

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private StorageBill owner;

    @Transient
    private String sn; //所属单号

    @Transient
    private String ownerId; //单据Id

    public StorageBill getOwner() {
        return owner;
    }

    public void setOwner(StorageBill owner) {
        this.owner = owner;
    }

    public String getSn() {

        if (this.sn != null) {
            return this.sn;
        }

        if (this.owner != null) {
            return this.owner.getSn();
        }

        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getOwnerId() {

        if (this.ownerId != null) {
            return this.ownerId;
        }

        if (this.owner != null) {
            return this.owner.getId();
        }

        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }
}
