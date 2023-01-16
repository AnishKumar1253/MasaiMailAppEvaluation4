package com.masai.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.app.entity.Email;
import com.masai.app.entity.User;
import com.masai.app.repository.EmailRepository;
import com.masai.app.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EmailRepository emailRepository;
	
	@Override
	public List<User> registerUser(User user) {
		System.out.print("hello");
		//User u=this.userRepository.findById(user.getEmail()).get();
		//if(u!=null) {
		//	System.out.println("user already exists");
		//	return null;
		//}
		this.userRepository.save(user);
		return this.userRepository.findAll();
	}

	@Override
	public boolean loginUser(User user) {
		User u=this.userRepository.findById(user.getEmail()).get();
		if(u==null) {
			return false;
		}
		
		return true;
	}

	/** Get all the email where in the email entity the to attribute is associated with the 
	 * current user
	 */
	@Override
	public List<Email> getAllMails(String userEmail) {
		User u=this.userRepository.findById(userEmail).get();
		if(u!=null) {
			return this.emailRepository.findByTo(u);
		}
		return null;
	}

	@Override
	public List<Email> getAllStarredMails(String userEmail) {
		User u=this.userRepository.findById(userEmail).get();
		if(u!=null) {
			List<Email> mails=this.emailRepository.findByToAndStarred(u,true);
			return mails;
		}
		return null;
	}

	@Override
	public User updateUser(User user) {
		User u=this.userRepository.findById(user.getEmail()).get();
		if(u==null) {
			return null;
		}
		this.userRepository.save(user);
		return user;
	}

	
}