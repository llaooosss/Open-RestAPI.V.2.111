package com.example.demo.kafka;

import org.springframework.kafka.annotation.KafkaListeners;
import org.springframework.stereotype.Component;

@Component
public class KafkaListener {

    @Kaf
    @org.springframework.kafka.annotation.KafkaListener(topics = "")
    @KafkaListener(topics = "movie-recommendations", groupId = "email-group")
    public void listen(MessagePayload payload) {
        emailService.sendEmail(payload.getEmails(), payload.getMovies());
    }
}
