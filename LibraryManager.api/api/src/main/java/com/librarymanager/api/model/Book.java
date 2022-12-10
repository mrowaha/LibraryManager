package com.librarymanager.api.model;
 
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity(name = "book")
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private String[] genres;

    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id", nullable = false)
    private Author author;

    @ManyToMany
    @JoinTable(
        name = "users_issued",
        joinColumns = @JoinColumn(name = "book_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> users_issued = new HashSet<>();

    public Book() {}

    public Book(String name, String description, String[] genres, Integer quantity) {
        this.name = name;
        this.description = description;
        this.genres = genres;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    } 

    public String getDescription() {
        return description;
    }

    public String[] getGenres() {
        return genres;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Author getAuthor() {
        return author;
    }

    public Set<User> getUsers_issued() {
        return users_issued;
    }

    public void setId(Long id){
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setGenres(String[] genre) {
        this.genres = genre;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setUsers_issued(Set<User> users) {
        this.users_issued = users;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Book compareToBook) {
            //this does not compare the id of the books because the ids are auto incremented upon creation
            return this.name.equalsIgnoreCase(compareToBook.name);
        }

        return false;
    }

    @Override
    public String toString() {
        return "Name: " + this.getName() + "\nDescription: " + this.getDescription() + "\n"; 
    }

    public void issueToUser(User user) {
        users_issued.add(user);
    }

    public void unissueFromUser(User user) {
        users_issued.remove(user);
    }

}