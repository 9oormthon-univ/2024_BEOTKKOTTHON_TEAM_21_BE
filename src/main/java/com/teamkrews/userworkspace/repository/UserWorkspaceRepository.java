package com.teamkrews.userworkspace.repository;

import com.teamkrews.User.model.User;
import com.teamkrews.userworkspace.model.UserWorkspace;
import com.teamkrews.workspace.model.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserWorkspaceRepository extends JpaRepository<UserWorkspace, Long> {
    Optional<UserWorkspace> findByUserAndWorkspace(User user, Workspace workspace);
}
