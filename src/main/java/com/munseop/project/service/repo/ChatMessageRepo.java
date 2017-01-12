package com.munseop.project.service.repo;

import com.munseop.project.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Administrator on 2017-01-11.
 */
public interface ChatMessageRepo extends JpaRepository<ChatMessage, Integer> {
}
