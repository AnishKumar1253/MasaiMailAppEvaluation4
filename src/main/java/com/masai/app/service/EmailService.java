package com.masai.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.masai.app.entity.Email;
import com.masai.app.entity.User;
import com.masai.app.exception.UserNotFoundException;
import com.masai.app.repository.EmailRepository;
import com.masai.app.repository.UserRepository;

@Service
public interface EmailService {
    public static final EmailRepository emailRepository = null;
    public static final UserRepository userRepository = null;

   

    public static Email sendEmail(Email email) {
        User sender = userRepository.findByEmail(email.getUser().getEmail());
        if (sender == null) {
            throw new UserNotFoundException("Sender with email " + email.getUser().getEmail() + " not found");
        }
        email.setUser(sender);
        for (String recipient : email.getRecipients()) {
            String existingRecipient = userRepository.findByEmail(recipient.getEmail());
            if (existingRecipient == null) {
                throw new UserNotFoundException("Recipient with email " + recipient.getEmail() + " not found");
            }
            recipient = existingRecipient;
        }
        return emailRepository.save(email);
    }

    public static List<Email> getEmails(User sender, List<User> recipients, boolean starred) {
        if (sender != null) {
            sender = userRepository.findByEmail(sender.getEmail());
            if (sender == null) {
                throw new UserNotFoundException("Sender with email " + sender.getEmail() + " not found");
            }
        }
        if (recipients != null) {
            for (User recipient : recipients) {
                User existingRecipient = userRepository.findByEmail(recipient.getEmail());
                if (existingRecipient == null) {
                    throw new UserNotFoundException("Recipient with email " + recipient.getEmail() + " not found");
                }
                recipient = existingRecipient;
            }
        }
        if (starred) {
            return emailRepository.findByStarred(true);
        }
        return emailRepository.findBySenderOrRecipients(sender, recipients);
    }

    public static Email updateEmail(String id, Email email) {
        jakarta.validation.constraints.Email existingEmail = emailRepository.findById(id)
                .orElseThrow();
        ((Email) existingEmail).setSubject(email.getSubject());
        ((Email) existingEmail).setBody(email.getBody());
        ((Email) existingEmail).setRecipients(email.getRecipients());
        return (Email) emailRepository.save(existingEmail);
    }

    public static Email toggleStarred(long id) 
    {
    	Email existingEmail = emailRepository.findAll(id)
    			.orElseThrow(() -> new EmailNotFoundException("Email with id " + id + " not found"));
    			existingEmail.setIsStarred(!existingEmail.getIsStarred());
    			return emailRepository.save(existingEmail);
    			}
    public static void deleteEmail(long id) {
        Email email = emailRepository.findAll(id)
                .orElseThrow(() -> new EmailNotFoundException("Email with id " + id + " not found"));
        emailRepository.deleteAll(email);
    }

	public List<Email> getUserEmails(String email, boolean starred);
    }
       
