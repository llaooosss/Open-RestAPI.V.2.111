package com.example.demo.repository;

import com.example.demo.entity.Subscriptions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubscriptionsRepository extends JpaRepository<Subscriptions, Long> {

    List<Subscriptions> findByGenresContaining(List<String> genre);

    List<String> findEmailsByGenres(@Param("genre") String genre);

}
