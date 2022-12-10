package com.librarymanager.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.librarymanager.api.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    List<Author> findByName(String name);  

    List<Author> findByNameContaining(String name);

    List<Author> findByGenres(String[] genres);
    
}

