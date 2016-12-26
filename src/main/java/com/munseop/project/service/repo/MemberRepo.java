package com.munseop.project.service.repo;

import com.munseop.project.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

/**
 * Created by 심문섭 on 2016-12-23.
 */
public interface MemberRepo extends JpaRepository<Member, Integer>,QueryDslPredicateExecutor<Member> {
}
