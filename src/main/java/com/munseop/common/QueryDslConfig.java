package com.munseop.common;

import com.querydsl.jpa.EclipseLinkTemplates;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


/**
 * Created by 심문섭 on 2016-12-23.
 */

@Configuration
public class QueryDslConfig {
    private final Logger logger = LoggerFactory.getLogger( QueryDslConfig.class );

    public QueryDslConfig(){
        logger.info("QueryDslConfig Configration 생성됨");
    }

    @PersistenceContext(name="ctxJPA")
    EntityManager em;

    @Bean(name = "queryFactory")
    public JPAQueryFactory queryFactory(){
        EclipseLinkTemplates template = new EclipseLinkTemplates();
        return new JPAQueryFactory(template,em);
    }



}
