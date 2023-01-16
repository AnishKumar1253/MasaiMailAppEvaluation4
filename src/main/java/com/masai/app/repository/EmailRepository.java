package com.masai.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.masai.app.entity.User;

import jakarta.validation.constraints.Email;

@Repository
public interface EmailRepository extends JpaRepository<Email, String> {
    List<com.masai.app.entity.Email> findBySenderOrRecipients(User sender, List<User> recipients);
    List<com.masai.app.entity.Email> findByStarred(boolean starred);
	com.masai.app.entity.Email save(com.masai.app.entity.Email email);
}
