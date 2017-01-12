package com.munseop.project.service.repo;

import com.munseop.project.model.Member;
import com.munseop.project.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import java.util.UUID;

/**
 * Created by Administrator on 2017-01-10.
 */
public interface RoomRepo extends JpaRepository<Room, UUID> {
}
