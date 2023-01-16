package com.masai.app.service;

import com.masai.app.entity.Email;

public interface EmailService {

	public boolean sendMail(Email email);
	public boolean starredMail(int id);
	public boolean deleteMail(int id);
}