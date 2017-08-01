package com.ep.storage.domain.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.ep.commons.domain.model.AbstractVersionModel;
import com.ep.commons.domain.model.Organ;
import com.ep.commons.domain.model.User;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;

/**
 * 商品
 *
 * @author 文卡<wkwenka@gmail.com>  on 17-8-1.
 */
@Entity
@Table
public class Goods extends AbstractVersionModel {

    @Column(name = "name_", nullable = false)
    private String name;

    private String spell; //拼写

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_time", nullable = false, updatable = false)
    private Date createTime = new Date(); //创建时间

    @JSONField(serialize = false)
    @ManyToOne
    @JoinColumn(name = "creator_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private User creator; // 创建人


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


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpell() {
        return spell;
    }

    public void setSpell(String spell) {
        this.spell = spell;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
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
