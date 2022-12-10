package com.librarymanager.api.model;

import jakarta.persistence.GenerationType;

import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.librarymanager.api.util.SHA256;

@Entity(name = "user")
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name = "users_issued",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private Set<Book> books = new HashSet<>();  
    
    public User() {}

    public User(String email, String password) {
        this.email = email;
        try{
            this.password = new SHA256(password).getHashed();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {

        try{
            this.password = new SHA256(password).getHashed();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    } 

    public void setId(Long id) {
        this.id = id;
    }


}
