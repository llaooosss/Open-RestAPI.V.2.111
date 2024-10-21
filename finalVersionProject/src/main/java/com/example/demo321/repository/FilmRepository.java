package com.example.demo321.repository;

import com.example.demo321.DTO.PostFilmDTO;
import com.example.demo321.entity.Film;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;


@Repository
public interface FilmRepository extends JpaRepository<Film, Long>, JpaSpecificationExecutor<Film> {

    PostFilmDTO findFilmByKinopoiskId(Integer kinopoiskId);

    PostFilmDTO findFilmByNameRu(String nameRu);

    PostFilmDTO findFilmByGenre(String genre);

}
