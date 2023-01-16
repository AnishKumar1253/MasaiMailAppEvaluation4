package com.masai.app.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.masai.app.entity.User;
import com.masai.app.request.LoginRequest;
import com.masai.app.service.EmailService;
import com.masai.app.service.UserService;
import com.masai.app.entity.Email;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final EmailService emailService;

    public UserController(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@Valid @RequestBody User user) {
        User newUser = UserService.createUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody LoginRequest loginRequest) {
        User loggedInUser = UserService.loginUser(loginRequest);
        return new ResponseEntity<>(loggedInUser, HttpStatus.OK);
    }


    @GetMapping("/mails")
    public ResponseEntity<List<Email>> getUserEmails(@RequestParam String email,
                                                     @RequestParam(required = false) boolean starred) {
        List<Email> emails = emailService.getUserEmails(email, starred);
        return new ResponseEntity<>(emails, HttpStatus.OK);
    }

    @PostMapping("/mails")
    public ResponseEntity<Email> sendEmail(@Valid @RequestBody Email email) {
        Email sentEmail = EmailService.sendEmail(email);
        return new ResponseEntity<>(sentEmail, HttpStatus.CREATED);
    }

    @PutMapping("/mails/{id}/starred")
    public ResponseEntity<Email> toggleStarred(@PathVariable long id) {
        Email updatedEmail = EmailService.toggleStarred(id);
        return new ResponseEntity<>(updatedEmail, HttpStatus.OK);
    }

    @PutMapping("/{email}")
    public ResponseEntity<User> updateUser(@PathVariable String email, @Valid @RequestBody User user) {
        User updatedUser = UserService.updateUser(email, user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deleteUser(@PathVariable String email) {
        UserService.deleteUser(email);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
