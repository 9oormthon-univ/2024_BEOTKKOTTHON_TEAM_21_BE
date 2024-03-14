package com.teamkrews.workspace.model;

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

    //채팅룸 추가 예정
}
