package com.teamkrews.chat.model;

import com.teamkrews.workspace.model.Workspace;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
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

    @OneToMany(mappedBy = "chatRoom")
    private List<ChatRoomUser> chatRoomUsers;

    @ManyToOne
    @JoinColumn(name = "id")
    private Workspace workspace;
}