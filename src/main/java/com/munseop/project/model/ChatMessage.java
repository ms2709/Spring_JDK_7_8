package com.munseop.project.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.eclipse.persistence.annotations.JoinFetch;
import org.eclipse.persistence.annotations.JoinFetchType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017-01-11.
 */
@Entity(name="ChatMessages")
public class ChatMessage implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "messageId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer messageId;

    private String roomId;
    private String message;
    private String sendUserId;
    private String recvUserId;
    private Date messageDate;
    private String status;

    @JoinFetch(JoinFetchType.INNER)
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference("chatMessage_room")
    @JoinColumn(name="roomId", insertable = false, updatable = false)
    private Room room;

    public Integer getMessageId() {return this.messageId;}
    public String getRoomId() {return this.roomId;}
    public String getMessage() {return this.message;}
    public String getSendUserId() {return this.sendUserId;}
    public String getRecvUserId() {return this.recvUserId;}
    public Date getMessageDate() {return this.messageDate;}
    public String getStatus() {return this.status;}

    public Room getRoom() {return this.room;}

    public void setMessageId(Integer messageId) {this.messageId = messageId;}
    public void setRoomId(String roomId) {this.roomId = roomId;}
    public void setMessage(String message) {this.message = message;}
    public void setSendUserId(String sendUserId) {this.sendUserId = sendUserId;}
    public void setRecvUserId(String recvUserId) {this.recvUserId = recvUserId;}
    public void setMessageDate(Date messageDate) {this.messageDate = messageDate;}
    public void setStatus(String status) {this.status = status;}

    public void setRoom(Room room) {this.room = room;}
}
