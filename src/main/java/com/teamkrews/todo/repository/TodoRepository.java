package com.teamkrews.todo.repository;


import com.teamkrews.todo.model.Todo;
import com.teamkrews.workspace.model.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findAllByWorkspaceAndCompleted(Workspace workspace, Boolean completed);
}
