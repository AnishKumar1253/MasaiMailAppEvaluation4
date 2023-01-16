package com.masai.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.masai.app.entity.Email;
import com.masai.app.service.EmailServiceImpl;

@RestController
@RequestMapping("/masaimail")
public class EmailController {

	@Autowired
	private EmailServiceImpl emailServiceImpl;
	
	@PostMapping("/mail")
	public ResponseEntity<?> sendMail(@RequestBody Email email) throws Exception{
		boolean success=this.emailServiceImpl.sendMail(email);
		if(success==false)
			throw new Exception("The from or to email does not exist in the database");
		return ResponseEntity.ok(success);
	}
	
	@PostMapping("/starred/{id}")
	public ResponseEntity<?> starrMail(@PathVariable("id") int id){
		boolean success=this.emailServiceImpl.starredMail(id);
		return ResponseEntity.ok(success);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteMail(@PathVariable("id") int id){
		boolean success=this.emailServiceImpl.deleteMail(id);
		return ResponseEntity.ok(success);
	}
}