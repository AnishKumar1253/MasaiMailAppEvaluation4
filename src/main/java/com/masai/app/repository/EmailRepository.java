package com.masai.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.masai.app.entity.Email;
import com.masai.app.entity.User;

@Repository
public interface EmailRepository extends JpaRepository<Email, Integer> {

	public List<Email> findByTo(User user);
	public List<Email> findByToAndStarred(User user,boolean starred);
}