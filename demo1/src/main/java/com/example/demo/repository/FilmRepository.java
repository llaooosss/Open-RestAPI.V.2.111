package com.example.demo.repository;

import com.example.demo.DTO.GenreDto;
import com.example.demo.entity.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long>, JpaSpecificationExecutor<Film> {

    List<Film> findTop10ByGenres(String genres);

    Film deleteFilmByFilmId(Long filmId);

    Optional<Film> findFilmsByFilmName(String filmName);

    Optional<Film> findFilmsByRating(Double rating);

    Optional<Film> findFilmsById(Long id);

    Optional<Film> findByFilmId(Long filmId);
}
