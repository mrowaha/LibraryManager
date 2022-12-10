package com.librarymanager.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.librarymanager.api.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    
    List<User> findByEmail (String email);
    
    List<User> findByPassword (String password);
}
