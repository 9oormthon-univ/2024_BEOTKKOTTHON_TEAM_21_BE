package com.teamkrews.userworkspace.model;


import com.teamkrews.User.model.User;
import com.teamkrews.workspace.model.Workspace;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class UserWorkspace {
    @Id
    @Column(name = "user_workspace_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "workspace_id")
    private Workspace workspace;
}
