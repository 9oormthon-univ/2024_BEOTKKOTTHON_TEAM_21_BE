package com.teamkrews.dm.repository;

import com.teamkrews.dm.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}

