package com.masai.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.masai.app.entity.User;
import com.masai.app.exception.UserNotFoundException;
import com.masai.app.repository.EmailRepository;
import com.masai.app.repository.UserRepository;
import com.masai.app.request.LoginRequest;

@Service
public interface UserService {
    public static final UserRepository userRepository = null;
    public static final EmailRepository emailRepository = null;


    public static User createUser(User user) {
        return userRepository.save(user);
    }

    public static User loginUser(LoginRequest loginRequest) {
        Optional<User> userOpt = Optional.ofNullable(userRepository.findByEmail(loginRequest.getEmail()));
        if (!userOpt.isPresent()) {
            throw new UserNotFoundException("User with email " + loginRequest.getEmail() + " not found");
        }
        User user = userOpt.get();
        if(!loginRequest.getPassword().equals(user.getPassword())) {
            throw new UserNotFoundException("Invalid Credentials");
        }
        return user;
    }



    public static User updateUser(String email, User user) {
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

    public static void deleteUser(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("User with email " + email + " not found");
        }
        List<jakarta.validation.constraints.Email> emails = emailRepository.findBySenderOrRecipients(user, null);
        emailRepository.deleteAll(emails);
        userRepository.delete(user);
    }

    public static List<jakarta.validation.constraints.Email> getUserEmails(String email, boolean starred) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("User with email " + email + " not found");
        }
        List<jakarta.validation.constraints.Email> emails;
        if (starred) {
            emails = emailRepository.findBySenderOrRecipients(user, null);
        } else {
            emails = emailRepository.findBySenderOrRecipients(user, null);
        }
        return emails;
    }
}
