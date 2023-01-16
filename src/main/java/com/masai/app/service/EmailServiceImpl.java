package com.masai.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.app.entity.Email;
import com.masai.app.entity.User;
import com.masai.app.repository.EmailRepository;
import com.masai.app.repository.UserRepository;

@Service
public class EmailServiceImpl implements EmailService {
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private EmailRepository emailRepository;

	@Override
	public boolean sendMail(Email email) {
		User u1=this.userRepository.findById(email.getFrom().getEmail()).get();
		User u2=this.userRepository.findById(email.getTo().getEmail()).get();
		
		if(u1==null || u2==null) return false;
		
		email.setFrom(u1);
		email.setTo(u2);
		this.emailRepository.save(email);
		
		return true;
	}

	@Override
	public boolean starredMail(int id) {
		Email email=this.emailRepository.findById(id).get();
		email.setStarred(true);
		return true;
	}

	@Override
	public boolean deleteMail(int id) {
	
		Email e=this.emailRepository.findById(id).get();
		this.emailRepository.delete(e);
		
		return true;
	}

}