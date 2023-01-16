package com.masai.app.service;
import java.util.List;

import com.masai.app.entity.Email;
import com.masai.app.entity.User;

public interface UserService {

	public List<User> registerUser(User user);
	public boolean loginUser(User user);
	public List<Email> getAllMails(String userEmail);
	public List<Email> getAllStarredMails(String userEmail);
	public User updateUser(User user);
	
	
}