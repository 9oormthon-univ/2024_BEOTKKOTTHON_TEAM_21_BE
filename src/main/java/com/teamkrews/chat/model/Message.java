package com.teamkrews.chat.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Message {

    public enum MessageType {
        TEXT, IMAGE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageId;

    private Long senderId;

    private String content;

    private Long chatRoomId;

    private LocalDateTime createdAt;

    private MessageType messageType;

}
