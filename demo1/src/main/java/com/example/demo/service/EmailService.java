package com.example.demo.service;

import com.example.demo.entity.Film;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService {

    public void sendMessage(String to, List<Film> films, String from) {
        String subject = "Список фильмов";
        String body = createEmailBody(films);

        System.out.println("Отправка письма на адрес: " + to);
        System.out.println("Отправка письма от неофициального сайта Кинопоиск: " + from);
        System.out.println("Тема: " + subject);
        System.out.println("Сообщение:\n" + body);
    }

    public String createEmailBody(List<Film> films) {
        StringBuilder message = new StringBuilder();
        message.append("Добрый день! Ваша персональная подборка фильмов для вас:\n\n");
        for (Film film:films) {
            message.append("- ").append(film).append("\n");
        }
        return message.toString();
    }
}
