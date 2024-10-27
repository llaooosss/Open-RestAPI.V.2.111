package com.example.demo.job;

import com.example.demo.entity.Film;
import com.example.demo.entity.Subscriptions;
import com.example.demo.repository.FilmRepository;
import com.example.demo.repository.SubscriptionsRepository;
import com.example.demo.service.SubscriptionsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@EnableScheduling
public class Job {

    private final SubscriptionsService subscriptionsService;
    private final SubscriptionsRepository subscriptionsRepository;
    private final FilmRepository filmRepository;

    public Job(SubscriptionsService subscriptionsService, SubscriptionsRepository subscriptionsRepository, FilmRepository filmRepository) {
        this.subscriptionsService = subscriptionsService;
        this.subscriptionsRepository = subscriptionsRepository;
        this.filmRepository = filmRepository;
    }

    @Scheduled(cron = "*/3 * * * * *")
    public void job() throws InterruptedException {
        String genre = subscriptionsService.getGenreForDay();
        List<Subscriptions> subscribtions = subscriptionsRepository.findAll();

        for(Subscriptions subscriber : subscribtions) {
            List<String> subscriberGenres = subscriber.getGenres();

            if (subscriberGenres.contains(genre)) {
                List<String> emails = subscriptionsRepository.findEmailsByGenres(genre);
                List<Film> films = filmRepository.findTop10ByGenres(genre);

                MessagePayload payload = new MessagePayload(emails, movies);
                kafkaProducer.send("movie-recommendations", payload);
            }
        }
    }
}
