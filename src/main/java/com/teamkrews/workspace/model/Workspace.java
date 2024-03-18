package com.teamkrews.workspace.model;

import com.teamkrews.chat.model.ChatRoom;
import com.teamkrews.userworkspace.model.UserWorkspace;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Workspace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String teamName;
    private String workspaceUUID;
    @OneToMany(mappedBy = "workspace")
    private List<UserWorkspace> userWorkspaces;
    private String profileImageUrl;
    private String explanation;

    @OneToMany(mappedBy = "workspace")
    private List<ChatRoom> chatRooms;
}
