package com.munseop.project.service;


import com.munseop.project.model.Member;

import com.munseop.project.model.QMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 심문섭 on 2016-12-23.
 */

@Service("commonService")
public class CommonService extends  RootService {

    //테스트 메소드
    public List<Integer> getTestService(){
        List<Integer> items = new ArrayList<Integer>();
        for(int i= 0;i<10;i++){
            items.add(i);
        }
        return items;
    }

    public Member getMemberTest1(){
        QMember qMember = QMember.member;
        Member member = queryFactory.from(qMember).select(qMember).fetchFirst();
        return member;
    }

    public Member getMemberTest2(){
        Member member = memberRepo.findAll().get(0);
        return member;
    }

}
