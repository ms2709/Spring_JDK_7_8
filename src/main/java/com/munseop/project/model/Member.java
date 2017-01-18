package com.munseop.project.model;


import javax.persistence.*;
import java.io.Serializable;
/**
 * Created by temp_administrator on 2016-12-23.
 */
@Entity(name = "Members")
public class Member implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "memberId")
    private Integer memberId;

    private String loginId;

    private String password;

    private String memberName;

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getLoginId() { return this.loginId; }

    public void setLoginId(String loginId) { this.loginId = loginId; }

    public String getPassword() { return  this.password; }

    public void setPassword(String password) { this.password = password; }

    public String getMemberName() { return this.memberName; }

    public void setMemberName(String userName) { this.memberName = userName; }
}
