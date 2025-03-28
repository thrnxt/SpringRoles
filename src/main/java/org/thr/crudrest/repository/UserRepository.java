package org.thr.crudrest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.thr.crudrest.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
