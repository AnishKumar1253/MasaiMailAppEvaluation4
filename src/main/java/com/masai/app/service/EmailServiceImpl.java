package com.masai.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.masai.app.entity.Email;
import com.masai.app.entity.User;
import com.masai.app.repository.EmailRepository;
import com.masai.app.repository.UserRepository;

@Service
public class EmailServiceImpl implements EmailService {
    private final EmailRepository emailRepository;
    private final UserRepository userRepository;

    public EmailServiceImpl(EmailRepository emailRepository, UserRepository userRepository) {
        this.emailRepository = emailRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Email sendEmail(Email email) {
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

    @Override
    public List<Email> getEmails(User sender, List<User> recipients, boolean starred) {
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

    @Override
    public Email updateEmail(long id, Email email) {
        Email existingEmail = emailRepository.findById(id)
                .orElseThrow(() -> new EmailNotFoundException("Email with id " + id + " not found"));
        existingEmail.setSubject(email.getSubject());
        existingEmail.setBody(email.getBody());
        existingEmail.setRecipients(email.getRecipients());
        return emailRepository.save(existingEmail);
    }

    @Override
    public Email toggleStarred(long id) {
        Email existingEmail = emailRepository.findById(id)
                .orElseThrow(() -> new EmailNotFoundException("Email with id " + id + " not found"));
        existingEmail.setStarred(!existingEmail.isStarred());
        return emailRepository.save(existingEmail);
    }

    @Override
    public void deleteEmail(String id) {
        Email email = emailRepository.findById(id)
                .orElseThrow(() -> new EmailNotFoundException("Email with id " + id + " not found"));
        emailRepository.delete(email);
    }

	@Override
	public void deleteEmail(long id) {
		// TODO Auto-generated method stub
		
	}
}

