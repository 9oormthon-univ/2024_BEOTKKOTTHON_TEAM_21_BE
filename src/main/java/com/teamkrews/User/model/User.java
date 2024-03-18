package com.teamkrews.User.model;

import com.teamkrews.userworkspace.model.UserWorkspace;
import jakarta.persistence.*;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user")
@Setter
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nickName;
    private String loginId;
    private String email;
    private String password;
    private String profileImageUrl;

    @Column(unique = true)
    private String userUUID;

    @OneToMany(mappedBy = "user")
    private List<UserWorkspace> userWorkspaces;
}
