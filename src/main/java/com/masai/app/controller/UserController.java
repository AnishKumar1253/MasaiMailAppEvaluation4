package com.masai.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.masai.app.entity.Email;
import com.masai.app.entity.User;
import com.masai.app.service.UserServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/masaimail")
public class UserController {

	@Autowired
	private UserServiceImpl userServiceImpl;
	
	@PostMapping("/register")
	public ResponseEntity<?> regUser(@Valid @RequestBody User user) throws Exception{
		System.out.println(user.getMobileNumber());
		System.out.println(user.getEmail());
		List<User> users=this.userServiceImpl.registerUser(user);
		if(users==null)
			throw new Exception("Something went wrong");
		return new ResponseEntity<List<User>>(users,HttpStatus.CREATED);
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> logUser(@Valid @RequestBody User user) throws Exception{
		boolean success=this.userServiceImpl.loginUser(user);
		if(success==false)
			throw new Exception("User does not exist first register");
		return new ResponseEntity<Boolean>(success,HttpStatus.OK);
	}
	
	@GetMapping("/mail/{loggedInUser}")
	public ResponseEntity<?> getAllMails(@PathVariable("loggedInUser") String email) throws Exception{
		List<Email> receivedMails=this.userServiceImpl.getAllMails(email);
		if(receivedMails==null)
			throw new Exception("User not logged in");
		return ResponseEntity.ok(receivedMails);
	}
	
	@GetMapping("/starred/{loggedInUser}")
	public ResponseEntity<?> starredEmails(@PathVariable("loggedInUser") String email) throws Exception{
		List<Email> starredMails=this.userServiceImpl.getAllStarredMails(email);
		if(starredMails==null)
			throw new Exception("User not logged in");
		return ResponseEntity.ok(starredMails);
	}
	
	@PutMapping("/user")
	public ResponseEntity<?> updtUser(@Valid @RequestBody User user) throws Exception{
		User user1=this.userServiceImpl.updateUser(user);
		if(user1==null)
			throw new Exception("User does not exist");
		return ResponseEntity.ok(user1);
	}
}