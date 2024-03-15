package com.teamkrews.workspace.repository;

import com.teamkrews.workspace.model.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkspaceRepository extends JpaRepository<Workspace, String> {
    Optional<Workspace> findByWorkspaceUUID(String workspaceUUID);
}
