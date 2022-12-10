package com.librarymanager.api.controller;


import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
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

import com.librarymanager.api.model.Book;
import com.librarymanager.api.model.User;
import com.librarymanager.api.repository.BookRepository;
import com.librarymanager.api.repository.UserRepository;
import com.librarymanager.api.util.SHA256;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:3000"})
public class UserController {
    
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final BookRepository bookRepository;

    public UserController(UserRepository userRepository, BookRepository bookRepository) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }
    

    @RequestMapping(value="/user", method = RequestMethod.POST, consumes = "application/json")
    public Optional<User> addUser(
        @RequestBody User user
    ) {

        boolean exists = this.userRepository.findByEmail(user.getEmail()).size() > 0;
        if(exists) {
            return Optional.empty();
        }

        return Optional.of(this.userRepository.save(user));

    }


    @RequestMapping(value="/user", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ResponseEntity<String> getUserByEmail (
        @RequestParam(required = true) String email,
        @RequestParam(required = true) String password
    ) {

        try {

            User user = this.userRepository.findByEmail(email).get(0);
            String storedPassword = user.getPassword();
            String inputPassword = new SHA256(password).getHashed();
            if (!storedPassword.equals(inputPassword)) {
                return ResponseEntity.ok("failed");
            }
            //set up a session cookie
            ResponseCookie cookie = ResponseCookie.from("session", storedPassword)
                                    .secure(false)
                                    .httpOnly(false)
                                    .build(); 

            return ResponseEntity.ok().header("Set-Cookie", cookie.toString()).build();

        } catch (NoSuchElementException e) {
            return ResponseEntity.ok("failed");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return ResponseEntity.ok("failed");
        } catch (IndexOutOfBoundsException e) {
            return ResponseEntity.ok("failed");
        }

    }

    @RequestMapping(value = "/user/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteUserById(
        @PathVariable(required = true) Long userId
        // @CookieValue(name = "session", required = true) String session
    ) {

        String message;
        try {
            @SuppressWarnings("unused")
            User previousUser = this.userRepository.findById(userId).get();
            message = String.format("{\"message\" : \"%s\"}", String.format("user with id %d deleted", userId));
            this.userRepository.deleteById(userId);
            return ResponseEntity.ok(message);
        }catch(NoSuchElementException e){
            message = String.format("{\"message\" : \"%s\"}", String.format("no user with id %d exists", userId));
            return ResponseEntity.ok(message);
        }

    }


    @RequestMapping(value = "/user/{userId}/book", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Optional<List<Book>> getBooksForUser(
        @PathVariable Long userId
        // @CookieValue(name = "session", required = true) String session
    ) {
        
        try {
            User user = this.userRepository.findById(userId).get();
            List<Book> booksIssuedToUser = new ArrayList<Book>(user.getBooks());
            //remove users_issued field
            for(int i = 0; i < booksIssuedToUser.size(); i++) {
                booksIssuedToUser.get(i).setUsers_issued(null);
            }

            return Optional.of(booksIssuedToUser);
        } catch (NoSuchElementException e) {
            return Optional.empty();
        }        

    }

    @RequestMapping(value = "/user/{userId}/book/{bookId}", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseBody
    public Optional<User> unissueBookForUser(
        @PathVariable Long userId,
        @PathVariable Long bookId
        // @CookieValue(name = "session", required = true) String session
    ) {
        
        try {
            User user = this.userRepository.findById(userId).get();
            Book book = this.bookRepository.findById(bookId).get();
            int quantityInShelf = book.getQuantity();

            book.unissueFromUser(user);
            book.setQuantity(++quantityInShelf);
            this.userRepository.save(user);
            return Optional.of(user);
        } catch (NoSuchElementException e) {
            return Optional.empty();
        }

    }

}