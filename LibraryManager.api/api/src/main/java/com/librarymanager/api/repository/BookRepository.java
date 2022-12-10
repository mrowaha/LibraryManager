package com.librarymanager.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.librarymanager.api.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>{
   
    List<Book> findByName(String name);  

    List<Book> findByNameContaining(String name);

    List<Book> findByGenres(String[] genres);

    List<Book> findByAuthorId(Long authorId);

    void deleteByAuthorId(Long authorId);
}
