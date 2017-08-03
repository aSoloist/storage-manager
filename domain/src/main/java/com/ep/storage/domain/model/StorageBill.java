package com.ep.storage.domain.model;

import javax.persistence.*;

/**
 * 出入库单
 *
 * @author 文卡<wkwenka@gmail.com>  on 17-8-3.
 */
@Entity
@Table(name = "storage_storage_bill")
public class StorageBill extends AbstractBill {

    public enum Direction {
        IN, OUT
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, updatable = false)
    private Direction direction;  // IN: 入库  OUT:出库

    private String remark; //备注

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
