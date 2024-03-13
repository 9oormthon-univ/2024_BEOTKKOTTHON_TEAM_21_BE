package com.teamkrews.chat.repository;

import com.teamkrews.chat.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> findByEmail(final String email);
    public Optional<User> findByLoginId(final String loginId);

}