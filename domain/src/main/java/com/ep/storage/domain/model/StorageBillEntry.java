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
    private String ownerSn; //所属单号

    @Transient
    private String ownerId; //单据Id

    public StorageBill getOwner() {
        return owner;
    }

    public void setOwner(StorageBill owner) {
        this.owner = owner;
    }

    public String getOwnerSn() {

        if (this.ownerSn != null) {
            return this.ownerSn;
        }

        if (this.owner != null) {
            return this.owner.getSn();
        }

        return ownerSn;
    }

    public void setOwnerSn(String ownerSn) {
        this.ownerSn = ownerSn;
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

    public String toString(){
        return "所属单号：" + this.ownerSn;
    }
}
