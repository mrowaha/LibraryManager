package com.librarymanager.api.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.librarymanager.api.model.Author;
import com.librarymanager.api.repository.AuthorRepository;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:3000"})
public class AuthorController {
    
    @Autowired
    private final AuthorRepository authorRepository;


    public AuthorController(AuthorRepository authorRepository) {

        this.authorRepository = authorRepository;

    }

    /*
     *   GET authors
     *   If a query param "name" is present, an author or a list of authors containing that name will be returned
     *   If a query param "genre" is present, an author or a list of authors writing in those genres will be returned
    */    
    @RequestMapping(value = "/author", method = RequestMethod.GET, produces = "application/json" )
    @ResponseBody
    public List<Author> getAllAuthors(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String genre
        // @CookieValue(name = "session", required = true) String session
    ) {

        if(name == null && genre == null)
            return this.authorRepository.findAll();

        //priority is given to name over genre    
        if(name != null){
            List<Author> authorsWithName = this.authorRepository.findByNameContaining(name);
            return authorsWithName;
        }

        List<Author> allAuthors = this.authorRepository.findAll();
        List<Author> authorsForGenre = new ArrayList<Author>();
        for (int i = 0; i < allAuthors.size(); i++) {
            Author currAuthor = allAuthors.get(i);
            
            for (int j = 0; j < currAuthor.getGenres().length; j++){
                if (currAuthor.getGenres()[j].equalsIgnoreCase(genre)) {
                    authorsForGenre.add(currAuthor);
                }
            }
        }  
        return authorsForGenre;
    }

    /* 
     * POST an author
     * create a new author with name, description and genre of writing
    */
    @RequestMapping(value = "/author", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    @ResponseBody
    public Optional<Author> addAuthor(
        @RequestBody Author author
        // @CookieValue(name = "session", required = true) String session
    ) {
    
        boolean exists = this.authorRepository.findByName(author.getName()).size() > 0;
    
        //return if any of the fields are empty
        if (exists || author.getName() == null || author.getDescription() == null || author.getGenres() == null){
            return Optional.empty();
        } 
        
        return Optional.of(this.authorRepository.save(author));
            
    }


    /*
     *  GET an author by id
     */
    @RequestMapping(value = "/author/{authorId}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Optional<Author> getAuthorById(
        @PathVariable(required = true) Long authorId
        // @CookieValue(name = "session", required = true) String session
    ) { 

        try{
            Author previousAuthor = this.authorRepository.findById(authorId).get();
            return Optional.of(previousAuthor);
        } catch (NoSuchElementException e) {
            return Optional.empty();
        }

    }

    /**
     * UPDATE an existing author's information
     */
    @RequestMapping(value = "/author/{authorId}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    @ResponseBody
    public Optional<Author> updateAuthor(
        @PathVariable(required = true) Long authorId,
        @RequestBody Author updatedAuthor
        // @CookieValue(name = "session", required = true) String session
    ) {

        try{
            Author previousAuthor = this.authorRepository.findById(authorId).get();

            String newName = updatedAuthor.getName();
            String newDescription = updatedAuthor.getDescription();
            String[] newGenres = updatedAuthor.getGenres();

            if (newName == null && newDescription == null && newGenres == null) {
                return Optional.of(previousAuthor);
            }

            if (newName != null) {
                previousAuthor.setName(newName);
            }

            if (newDescription != null) {
                previousAuthor.setDescription(newDescription);
            }

            if (newGenres != null) {
                previousAuthor.setGenres(newGenres);
            }

            this.authorRepository.save(previousAuthor);
            return Optional.of(previousAuthor); 

        } catch (NoSuchElementException e) {
            return Optional.empty();
        }

    }

    /**
     * DELETE an author
     */
    @RequestMapping(value = "/author/{authorId}", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<String> deleteBookById(
        @PathVariable(required = true) Long authorId
    //  @CookieValue(name = "session", required = true) String session
    ) {

        String message;
        try {
            @SuppressWarnings("unused")
            Author author = this.authorRepository.findById(authorId).get();
            message = String.format("{\"message\" : \"%s\"}", String.format("author with id %d deleted", authorId));
            this.authorRepository.deleteById(authorId);
            return ResponseEntity.ok(message);
        }catch(NoSuchElementException e){
            message = String.format("{\"message\" : \"%s\"}", String.format("no book with id %d exists", authorId));
            return ResponseEntity.ok(message);
        }

    }

}