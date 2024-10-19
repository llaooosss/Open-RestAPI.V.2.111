package com.example.demo321.repository;

import com.example.demo321.DTO.PostFilmDTO;
import com.example.demo321.entity.Film;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface FilmRepository extends JpaRepository<Film, Long>, JpaSpecificationExecutor<Film> {

    Page<Film> findAll(PostFilmDTO postFilmDTO, int page, int size);

    PostFilmDTO findFilmByKinopoiskId(int kinopoiskId);

    PostFilmDTO findFilmByNameRu(String nameRu);

    PostFilmDTO findFilmByRatingKinopoisk(double ratingKinopoisk);

    PostFilmDTO findFilmByGenre(String genre);

}
