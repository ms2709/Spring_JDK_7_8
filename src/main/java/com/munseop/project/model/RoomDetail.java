package com.munseop.project.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.eclipse.persistence.annotations.JoinFetch;
import org.eclipse.persistence.annotations.JoinFetchType;
import org.eclipse.persistence.annotations.UuidGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Administrator on 2017-01-10.
 */
@Entity(name = "RoomDetails")
public class RoomDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roomDetailId")
    private Integer roomDetailId;

    private String loginId;

    private String roomId;
    private Date indate;
    private Date outdate;
    private String status;

    @JoinFetch(JoinFetchType.INNER)
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference("roomDetail_room")
    @JoinColumn(name="roomId", insertable = false, updatable = false)
    private Room room;

    public Integer getRoomDetailId() {return this.roomDetailId;}
    public String getLoginId() {return this.loginId;}
    public String getRoomId() {return this.roomId;}
    public Date getIndate() {return this.indate;}
    public Date getOutdate() {return this.outdate;}
    public String getStatus() {return this.status;}

    public Room getRoom() {return this.room;}

    public void setRoomDetailId(Integer roomDetailId) {this.roomDetailId = roomDetailId;}
    public void setLoginId(String loginId) {this.loginId = loginId;}
    public void setRoomId(String roomId) {this.roomId = roomId;}
    public void setIndate(Date indate) {this.indate = indate;}
    public void setOutdate(Date outdate) {this.outdate = outdate;}
    public void setStatus(String status) {this.status = status;}

    public void setRoom(Room room) {this.room = room;}
}
