package com.teamkrews.chatroom.model;

import com.teamkrews.User.model.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "chat_room")
@Getter
@Setter
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_room_id")
    private Long chatRoomId;
    private Long userCnt;
    private Boolean isGroup;
    @ManyToOne
    @JoinColumn(name = "id")
    private User creator;
}