package com.munseop.project.service;

import com.munseop.common.ObjectMapperBean;
import com.munseop.project.model.Member;
import com.munseop.project.service.repo.MemberRepo;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by 심문섭 on 2016-12-26.
 *
 * 모든 서비스의 부모클래스
 * 범용적으로 사용하기 위해서는 프로퍼티의 접근제어자를 protected로 선언해야 함
 */
public abstract class RootService {

    @Autowired
    public ObjectMapperBean mapperBean;

    @Autowired
    protected JPAQueryFactory queryFactory;


    @Autowired
    protected MemberRepo memberRepo;

    //resources/META-INF/persistence.xml에 선언된 persistence-unit의 name
    @PersistenceContext(name="ctxJPA")
    protected EntityManager em;

}
