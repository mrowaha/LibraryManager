package com.librarymanager.api.model;

import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity(name = "author")
@Table(name = "authors")
public class Author {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private String[] genres;

    @JsonIgnore
    @OneToMany(mappedBy = "author", orphanRemoval = true)
    private Set<Book> books = new HashSet<>();  

    public Author() {}

    public Author(String name, String description, String[] genres) {
        this.name = name;
        this.description = description;
        this.genres = genres;
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

    public Set<Book> getBooks() {
        return books;
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

    @Override
    public boolean equals(Object o) {
        if (o instanceof Author compareToAuthor) {
            //this does not compare the id of the books because the ids are auto incremented upon creation
            return this.name.equalsIgnoreCase(compareToAuthor.name);
        }

        return false;
    }

    @Override
    public String toString() {
        return "Name: " + this.getName() + "\nDescription: " + this.getDescription();
    }

}
