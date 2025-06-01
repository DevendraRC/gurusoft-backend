package com.guruSoft.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.guruSoft.entities.Contact;
import com.guruSoft.repo.ContactRepository;
import com.guruSoft.services.EmailService;



@CrossOrigin(origins = "http://localhost:3000") // allow React dev server
@RestController
@RequestMapping("/api")
public class ContactController {

    @Autowired
    private ContactRepository contactRepository;
    
    @Autowired
    private EmailService emailService;
    
    @PostMapping
    public String submitContactForm(@RequestBody Contact contact) {
        contactRepository.save(contact);

        // Send email
        String subject = "New Contact Form Submission";
        String body = "Name: " + contact.getName() + "\n"
                    + "Email: " + contact.getEmail() + "\n"
                    + "Message:\n" + contact.getMessage();

        emailService.sendContactEmail("devendra.gurusoft@gmail.com", subject, body); // send to yourself

        return "Message received!";
    }


	 /* @PostMapping("/contact")
	    public String handleContact(@RequestBody Contact contact) {
	        System.out.println("Received contact form: " + contact);
	        return "Message received!";*/
    @PostMapping("/contact")
    public ResponseEntity<String> receiveContact(@RequestBody Contact contact) {
        contactRepository.save(contact);
        return ResponseEntity.ok("Message saved successfully.");
    }
}