package com.munseop.project.service;

import com.munseop.project.model.ChatMessage;
import com.munseop.project.service.repo.ChatMessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrator on 2017-01-11.
 */
@Service("messageChatService")
public class ChatMessageService extends RootService {
    @Autowired
    private ChatMessageRepo chatMessageRepo;

    @Transactional
    public void saveMessage(ChatMessage message){

        try{
            chatMessageRepo.saveAndFlush(message);
        }catch (Exception e){
            return;
        }
    }
}
