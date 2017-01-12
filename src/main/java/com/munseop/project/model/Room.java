package com.munseop.project.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.eclipse.persistence.annotations.UuidGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2017-01-10.
 */
@Entity(name = "Rooms")
@UuidGenerator(name="room_id_gen")
public class Room implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "room_id_gen")
    @Column(name="roomId")
    private String roomId;
    private String roomName;
    private Date createDate;
    private String status;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
    @JsonManagedReference("roomDetail_room")
    private List<RoomDetail> roomDetails;

    public String getRoomId() {return this.roomId;}
    public String getRoomName() {return this.roomName;}
    public Date getCreateDate() {return this.createDate;}
    public String getStatus() {return this.status;}
    public List<RoomDetail> getRoomDetails() {return this.roomDetails;}

    public void setRoomId(String roomId) {this.roomId = roomId;}
    public void setRoomName(String roomName) {this.roomName = roomName;}
    public void setCreateDate(Date createDate) {this.createDate = createDate;}
    public void setStatus(String status) {this.status = status;}
    public void setRoomDetails(List<RoomDetail> roomDetails) {this.roomDetails = roomDetails;}
}
