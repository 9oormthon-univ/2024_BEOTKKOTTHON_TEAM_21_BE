package com.teamkrews.User.repository;

import com.teamkrews.User.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> findById(final Long id);
    public Optional<User> findByLoginId(final String loginId);

}
