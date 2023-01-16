package com.masai.app.entity;

import java.time.LocalDate;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
	@Table(name = "user")
	public class User {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "user_id")
	    @jakarta.validation.constraints.Email
	    private String id;

	    @Column(name = "first_name", nullable = false)
	    @NotNull
	    @Pattern(regexp = "^[a-zA-Z]+$",message = "No numbers or specials characters")
	    private String firstName;

	    @Column(name = "last_name", nullable = false)
	    @NotNull
	    @Pattern(regexp = "^[a-zA-Z]+$",message = "No numbers or special characters")
	    private String lastName;

	    @Column(name = "email", unique = true, nullable = false)
	    @NotNull
	    private String email;

	    @Column(name = "mobile_number", unique = true, nullable = false)
	    @NotNull
	    @Size(min = 10, max = 10)
	    private String mobileNumber;

	    @Column(name = "date_of_birth", nullable = false)
	    @NotNull
	    @JsonFormat(pattern="yyyy-MM-dd")
	    @Past(message = "The date of birth should not be a future date")
	    private LocalDate dateOfBirth;

	    @OneToMany(mappedBy = "usert", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	    private Set<Email> emails;
	    
	    private String password;

	    public User() {}

		public User(String id, String firstName, String lastName, String email, String mobileNumber,
				LocalDate dateOfBirth, Set<Email> emails) {
			super();
			this.id = id;
			this.firstName = firstName;
			this.lastName = lastName;
			this.email = email;
			this.mobileNumber = mobileNumber;
			this.dateOfBirth = dateOfBirth;
			this.emails = emails;
			
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public User(String password) {
			super();
			this.password = password;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getMobileNumber() {
			return mobileNumber;
		}

		public void setMobileNumber(String mobileNumber) {
			this.mobileNumber = mobileNumber;
		}

		public LocalDate getDateOfBirth() {
			return dateOfBirth;
		}

		public void setDateOfBirth(LocalDate dateOfBirth) {
			this.dateOfBirth = dateOfBirth;
		}

		public Set<Email> getEmails() {
			return emails;
		}

		public void setEmails(Set<Email> emails) {
			this.emails = emails;
		}
	    
	    
	}

