package com.dishit.jwt.Repositories;

import com.dishit.jwt.Entities.User;
import com.dishit.jwt.Enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);
    User findByRole(Role role);

    boolean existsByEmail(String email);
}
