package com.teamkrews.workspace.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Workspace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "workspace_id")
    private Long workspaceId;

    private String teamName;

    @Column(name = "workspace_uuid")
    private String workspaceUUID;

    private String profileImageUrl;

    private String explanation;

}