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
import com.librarymanager.api.model.Book;
import com.librarymanager.api.model.User;
import com.librarymanager.api.repository.AuthorRepository;
import com.librarymanager.api.repository.BookRepository;
import com.librarymanager.api.repository.UserRepository;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:3000"}, allowedHeaders = {"Access-Control-Allow-Origin"}, exposedHeaders = {"Access-Control-Allow-Origin"})
public class BookController {

    @Autowired
    private final BookRepository bookRepository;

    @Autowired
    private final AuthorRepository authorRepository;

    @Autowired
    private final UserRepository userRepository;

    public BookController(BookRepository bookRepository, AuthorRepository authorRepository, UserRepository userRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    /**
     *  GET all books
     */
    @RequestMapping(value = "/book", method = RequestMethod.GET, produces = "application/json" )
    @ResponseBody
    public List<Book> getAllBooks(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String genre
        // @CookieValue(name = "session", required = true) String session
    ) {

        if(name == null && genre == null)
            return this.bookRepository.findAll();

        //priority is given to name over genre    
        if(name != null){
            List<Book> booksWithName = this.bookRepository.findByNameContaining(name);
            return booksWithName;
        }

        List<Book> allBooks = this.bookRepository.findAll();
        List<Book> booksForGenre = new ArrayList<Book>();
        for (int i = 0; i < allBooks.size(); i++) {
            Book currBook = allBooks.get(i);
            
            for (int j = 0; j < currBook.getGenres().length; j++) {
                if (currBook.getGenres()[j].equalsIgnoreCase(genre)) {
                    booksForGenre.add(currBook);
                }
            }
        }  
        return booksForGenre;

    }

    /*
     * POST a new book for the given author. Priorty is given to author before book creation 
     */
    @RequestMapping(value = "/author/{authorId}/book", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    @ResponseBody
    public Optional<Book> addBook(
        @RequestBody Book book,
        @PathVariable(required = true) Long authorId
        // @CookieValue(name = "session", required = true) String session
    ) {
       
        boolean exists = this.bookRepository.findByName(book.getName()).size() > 0;
        //return if any of the fields are empty
        if (exists || 
            book.getName() == null || 
            book.getDescription() == null) {
                return Optional.empty();
        } 
        
        try {
            Author author = authorRepository.findById(authorId).get();
            book.setAuthor(author);
            return Optional.of(this.bookRepository.save(book));
        } catch (NoSuchElementException e) {
            return Optional.empty();            
        }

    }

    /*
     * GET a book by id
     */
    @RequestMapping(value = "/book/{bookId}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Optional<Book> getBookById(
        @PathVariable(required = true) Long bookId
        // @CookieValue(name = "session", required = true) String session
    ) {

        try{
            Book previousBook = this.bookRepository.findById(bookId).get();
            return Optional.of(previousBook);
        }catch(NoSuchElementException e){
            return Optional.empty();
        }

    }

    /*
     * GET books by author id only
     */
    @RequestMapping(value = "/author/{authorId}/book", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Optional<List<Book>> getBooksByAuthor(
        @PathVariable Long authorId
        // @CookieValue(name = "session", required = true) String session
    ) {

        try {
            List<Book> booksForAuthor = this.bookRepository.findByAuthorId(authorId);
            return Optional.of(booksForAuthor);
        }catch(NoSuchElementException e){
            return Optional.empty();
        }

    }

    /*
     * UPDATE an exisiting book. Can update name, description, genres, author
     */
    @RequestMapping(value = "/book/{bookId}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    @ResponseBody
    public Optional<Book> updateBook(
        @PathVariable(required = true) Long bookId,
        @RequestBody Book updatedBook
        // @CookieValue(name = "session", required = true) String session
    ) {

        
        try{
            Book previousBook = this.bookRepository.findById(bookId).get();
            String newName = updatedBook.getName();
            String newDescription = updatedBook.getDescription();
            String[] newGenres = updatedBook.getGenres();
            Author author = updatedBook.getAuthor();

            if (author != null) {
                previousBook.setAuthor(authorRepository.findById(author.getId()).get());
            }

            if (newName == null && newDescription == null && newGenres == null) {
                return Optional.of(previousBook);
            }

            if (newName != null) {
                previousBook.setName(newName);
            }

            if (newDescription != null) {
                previousBook.setDescription(newDescription);
            }

            if (newGenres != null) {
                previousBook.setGenres(newGenres);
            }

            this.bookRepository.save(previousBook);
            return Optional.of(previousBook); 
        }catch(NoSuchElementException e){
            return Optional.empty();
        }

    }

    /**
     * DELETE an existing book
     */
    @RequestMapping(value = "/book/{bookId}", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<String> deleteBookById(
        @PathVariable(required = true) Long bookId
        // @CookieValue(name = "session", required = true) String session
    ) {

        String message;
        try {
            @SuppressWarnings("unused")
            Book previousBook = this.bookRepository.findById(bookId).get();
            message = String.format("{\"message\" : \"%s\"}", String.format("book with id %d deleted", bookId));
            this.bookRepository.deleteById(bookId);
            return ResponseEntity.ok(message);
        }catch(NoSuchElementException e){
            message = String.format("{\"message\" : \"%s\"}", String.format("no book with id %d exists", bookId));
            return ResponseEntity.ok(message);
        }

    }

    @RequestMapping(value = "/book/{bookId}/users/{userId}", method = RequestMethod.PUT)
    public Optional<Book> issueBookToUser(
        @PathVariable Long bookId,
        @PathVariable Long userId
        // @CookieValue(name = "session", required = true) String session
    ) {
        
        
        try {
            Book book = this.bookRepository.findById(bookId).get();
            int quantityInShelf = book.getQuantity();
            if(quantityInShelf <= 0) {
                return Optional.empty();
            }

            User user = this.userRepository.findById(userId).get();

            book.issueToUser(user);
            book.setQuantity(--quantityInShelf);

            this.bookRepository.save(book);
            book.setUsers_issued(null);
            return Optional.of(book);
        } catch (NoSuchElementException e) {
            return Optional.empty();            
        }
    }
    
}