package com.bankwebapp.repository;

import com.bankwebapp.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.TreeSet;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String login);
    boolean existsByPlainPasswordAndUsername(String plainPassword, String username);
}
