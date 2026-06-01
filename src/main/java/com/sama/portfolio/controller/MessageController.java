package com.sama.portfolio.controller;

import com.sama.portfolio.model.Message;
import com.sama.portfolio.repository.MessageRepository;
import com.sama.portfolio.service.EmailService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
@CrossOrigin(origins = "*") // allow frontend access
public class MessageController {

    private final MessageRepository messageRepository;
    private final EmailService emailService;

    // 🔹 Constructor Injection
    public MessageController(MessageRepository messageRepository, EmailService emailService) {
        this.messageRepository = messageRepository;
        this.emailService = emailService;
    }

    // 🔥 POST → Save message + send email
    @PostMapping
    public Message saveMessage(@RequestBody Message message) {

        // Save to DB
        Message savedMessage = messageRepository.save(message);

        // Send acknowledgement email
        try {
            emailService.sendAcknowledgement(
                    message.getEmail(),
                    message.getName()
            );
        } catch (Exception e) {
            System.out.println("Email sending failed: " + e.getMessage());
        }

        return savedMessage;
    }

    // 🔥 GET → Fetch all messages (for admin later)
    @GetMapping
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }
}