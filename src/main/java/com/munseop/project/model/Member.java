package com.munseop.project.model;


import javax.persistence.*;
import java.io.Serializable;
/**
 * Created by temp_administrator on 2016-12-23.
 */
@Entity(name = "members")
public class Member implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "memberid")
    private Integer memberId;

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }
}
