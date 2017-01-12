package com.munseop.common.model;

import jdk.nashorn.internal.ir.debug.JSONWriter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import javax.json.Json;
import javax.json.JsonObject;
import java.io.Serializable;

/**
 * Created by Administrator on 2017-01-09.
 */
public class CurrentMember implements Serializable{
    private static final long serialVersionID = 1L;

    private String userName;
    private Long memberId;
    private String loginId;
    private String password;
    private boolean isLoginMember;

    public String getUserName() { return this.userName; }
    public void setUserName(String userName){ this.userName = userName; }

    public String getLoginId() { return this.loginId; }
    public void setLoginId(String loginId){ this.loginId = loginId; }

    public String getPassword() { return this.password; }
    public void setPassword(String password){ this.password = password; }

    public Long getMemberId() { return this.memberId; }
    public void setMemberId(Long memberId){ this.memberId = memberId; }

    public boolean isLoginMember() { return this.isLoginMember; }
    public void setLoginMember(boolean isLoginMember) { this.isLoginMember = isLoginMember; }

    @Override
    public String toString(){

        return "";
    }
}
