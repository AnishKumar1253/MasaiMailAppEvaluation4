package com.masai.app.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.masai.app.entity.Email;
import com.masai.app.entity.User;
import com.masai.app.service.EmailService;

@RestController
@RequestMapping("/emails")
public class EmailController {
    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping
    public ResponseEntity<List<Email>> getEmails(@RequestParam(required = false) User sender,
                                                 @RequestParam(required = false) List<User> recipients,
                                                 @RequestParam(required = false) boolean starred) {
        List<Email> emails = (List<Email>) EmailService.getEmails(sender, recipients, starred);
        return new ResponseEntity<>(emails, HttpStatus.OK);
    }

    @PutMapping("/{id}/starred")
    public ResponseEntity<Email> toggleStarred(@PathVariable long id) {
        Email updatedEmail = EmailService.toggleStarred(id);
        return new ResponseEntity<>(updatedEmail, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmail(@PathVariable long id) {
        EmailService.deleteEmail(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

	public EmailService getEmailService() {
		return emailService;
	}
}
