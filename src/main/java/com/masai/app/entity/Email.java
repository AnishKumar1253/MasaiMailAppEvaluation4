package com.masai.app.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "email")
public class Email {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "email_id")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "User_id", nullable = false)
    private User User;

    @Column(name = "recipients", nullable = false)
    @NotNull
    @Size(min = 1)
    private String[] recipients;

    @Column(name = "subject", nullable = false)
    @NotNull
    private String subject;

    @Column(name = "body", nullable = false)
    @NotNull
    private String body;

    @Column(name = "date_sent", nullable = false)
    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateSent;

    @Column(name = "is_starred")
    private Boolean isStarred;

    public Email() {}

	public Email(String id, User User, @NotNull @Size(min = 1) String[] recipients, @NotNull String subject,
			@NotNull String body, @NotNull LocalDateTime dateSent, Boolean isStarred) {
		super();
		this.id = id;
		this.User = User;
		this.recipients = recipients;
		this.subject = subject;
		this.body = body;
		this.dateSent = dateSent;
		this.isStarred = isStarred;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public User getUser() {
		return User;
	}

	public void setUser(User User) {
		this.User = User;
	}

	public String[] getRecipients() {
		return recipients;
	}

	public void setRecipients(String[] recipients) {
		this.recipients = recipients;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public LocalDateTime getDateSent() {
		return dateSent;
	}

	public void setDateSent(LocalDateTime dateSent) {
		this.dateSent = dateSent;
	}

	public Boolean getIsStarred() {
		return isStarred;
	}

	public void setIsStarred(Boolean isStarred) {
		this.isStarred = isStarred;
	}
    
    
}
