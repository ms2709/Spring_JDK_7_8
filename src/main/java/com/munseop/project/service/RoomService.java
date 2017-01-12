package com.munseop.project.service;

import com.munseop.project.model.Room;
import com.munseop.project.service.repo.RoomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2017-01-10.
 */
@Service("roomService")
public class RoomService extends RootService {
    @Autowired
    private RoomRepo roomRepo;

    public List<Room> getRoomList(){
        return  roomRepo.findAll();
    };

    @Transactional
    public void createRoom(Room room){

        try{
            roomRepo.saveAndFlush(room);
        }catch (Exception e){
            return;
        }
    }
}
