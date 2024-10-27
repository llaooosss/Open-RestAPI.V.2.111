package com.example.demo.service;

import com.example.demo.entity.Subscriptions;
import com.example.demo.repository.SubscriptionsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SubscriptionsService {

    private final SubscriptionsRepository subscriptionsRepository;

    public void saveSubscribe(String email, List<String> genres) {
        Subscriptions subscriptions = new Subscriptions();
        subscriptions.setEmail(email);
        subscriptions.setGenres(genres);
        subscriptionsRepository.save(subscriptions);
    }

    public String getGenreForDay() {
        DayOfWeek today = LocalDate.now().getDayOfWeek();

        Map<DayOfWeek, String> genreByDay = new HashMap<>();
        genreByDay.put(DayOfWeek.MONDAY, "Экшн");
        genreByDay.put(DayOfWeek.TUESDAY, "Драма");
        genreByDay.put(DayOfWeek.WEDNESDAY, "Комедия");
        genreByDay.put(DayOfWeek.THURSDAY, "Ужасы");
        genreByDay.put(DayOfWeek.FRIDAY, "Детективы");
        genreByDay.put(DayOfWeek.SATURDAY, "Романтическик");
        genreByDay.put(DayOfWeek.SUNDAY, "Боевик");

        return genreByDay.get(today);
    }

    public List<String> findEmailsByGenres(String genre) {
        return subscriptionsRepository.findEmailsByGenres(genre);
    }
}
