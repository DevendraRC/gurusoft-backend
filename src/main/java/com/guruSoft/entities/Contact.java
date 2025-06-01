package com.guruSoft.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Contact {
	
	
		 
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public Contact(String name, String email, String message) {
			super();
			this.name = name;
			this.email = email;
			this.message = message;
		}
		
		@Override
		public String toString() {
			return "Contact [name=" + name + ", email=" + email + ", message=" + message + "]";
		}
		
		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
		private String name;
		private String email;
		 @Column(length = 2000)
	    private String message;

	    
	

}
