package com.ep.storage.domain.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.ep.commons.domain.model.AbstractVersionModel;
import com.ep.commons.domain.model.Organ;
import com.ep.commons.domain.model.User;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 抽象单据基类
 *
 * @author 文卡<wkwenka@gmail.com>  on 17-8-1.
 */
@MappedSuperclass
public class AbstractBill extends AbstractVersionModel {

    @Column(length = 30, nullable = false, updatable = false)
    private String sn; // 单据编号

    @NotNull
    @Column(nullable = false)
    private Integer status = Integer.valueOf(0); // -1 标记删除 0 创建 1 生效

    @JSONField(serialize = false)
    @ManyToOne
    @JoinColumn(name = "creator_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private User creator; // 创建人

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_time", nullable = false, updatable = false)
    private Date createTime = new Date(); //创建时间

    @Transient
    private String creatorId;

    @Transient
    private String creatorName;


    @JSONField(serialize = false)
    @ManyToOne
    @JoinColumn(name = "organ_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private Organ organ; // 所属组织

    @Transient
    private String organId;

    @Transient
    private String organName;

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreatorId() {

        if (this.creatorId != null) {
            return this.creatorId;
        }

        if (this.creator != null) {
            return this.creator.getId();
        }

        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorName() {

        if (this.creatorName != null) {
            return this.creatorName;
        }

        if (this.creator != null) {
            return this.creator.getName();
        }

        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Organ getOrgan() {
        return organ;
    }

    public void setOrgan(Organ organ) {
        this.organ = organ;
    }

    public String getOrganId() {

        if (this.organId != null) {
            return this.organId;
        }

        if (this.organ != null) {
            return this.organ.getId();
        }

        return organId;
    }

    public void setOrganId(String organId) {
        this.organId = organId;
    }

    public String getOrganName() {

        if (this.organName != null) {
            return this.organName;
        }

        if (this.organ != null) {
            return this.organ.getName();
        }

        return organName;
    }

    public void setOrganName(String organName) {
        this.organName = organName;
    }
}
