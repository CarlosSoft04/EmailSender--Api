package controllers;

import adapters.EmailSenderGateway;
import application.EmailSenderService;
import core.EmailRequest;
import core.exceptions.EmailServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class EmailSendController {
    private final EmailSenderService emailService;

    @Autowired
    public EmailSendController(EmailSenderService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/api")
    public ResponseEntity<String> sendmail(@RequestBody EmailRequest request) {
        try {
            this.emailService.sendEmail(request.to(), request.subject(), request.body());
            return ResponseEntity.ok("Email enviado com sucesso!");

        } catch (EmailServiceException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao enivar email");

        }
    }
}