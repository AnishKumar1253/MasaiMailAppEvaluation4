package com.masai.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.masai.app.entity.Email;
import com.masai.app.entity.User;
import com.masai.app.repository.EmailRepository;
import com.masai.app.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final EmailRepository emailRepository;

    public UserServiceImpl(UserRepository userRepository, EmailRepository emailRepository) {
        this.userRepository = userRepository;
        this.emailRepository = emailRepository;
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User loginUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user == null || !user.getPassword().equals(password)) {
            throw new BadCredentialsException("Invalid email or password");
        }
        return user;
    }

    @Override
    public User updateUser(String email, User user) {
        User existingUser = userRepository.findByEmail(email);
        if (existingUser == null) {
            throw new UserNotFoundException("User with email " + email + " not found");
        }
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setMobileNumber(user.getMobileNumber());
        existingUser.setDateOfBirth(user.getDateOfBirth());
        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("User with email " + email + " not found");
        }
        List<Email> emails = emailRepository.findBySenderOrRecipients(user, null);
        emailRepository.deleteAll(emails);
        userRepository.delete(user);
    }

    @Override
    public List<Email> getUserEmails(String email, boolean starred) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("User with email " + email + " not found");
        }
        List<Email> emails;
        if (starred) {
            emails = emailRepository.findBySenderOrRecipients(user, null);
        } else {
            emails = emailRepository.findBySenderOrRecipients(user, null);
        }
        return emails;
    }
}

