package com.example.demo321.entity;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import com.example.demo321.DTO.PostFilmDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FilmResponseEntityFilter {

    @PersistenceContext
    private EntityManager entityManager;

    public Page<Film> filterFilms(PostFilmDTO postFilmDTO, int page, int size) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Film> criteriaQuery = criteriaBuilder.createQuery(Film.class);
        Root<Film> filmRoot = criteriaQuery.from(Film.class);

        List<Predicate> predicates = new ArrayList<>();

        if (postFilmDTO.getKinopoiskId() > 0) {
            predicates.add(criteriaBuilder.equal(filmRoot.get("kinopoiskId"), postFilmDTO.getKinopoiskId()));
        }
        if (postFilmDTO.getNameRu() != null && !postFilmDTO.getNameRu().isEmpty()) {
            predicates.add(criteriaBuilder.like(filmRoot.get("nameRu"), "%" + postFilmDTO.getNameRu() + "%"));
        }
        if (postFilmDTO.getRatingKinopoisk() > 0) {
            predicates.add(criteriaBuilder.equal(filmRoot.get("ratingKinopoisk"), postFilmDTO.getRatingKinopoisk()));
        }
        if (postFilmDTO.getGenre() != null && !postFilmDTO.getGenre().isEmpty()) {
            predicates.add(criteriaBuilder.like(filmRoot.get("genre"), "%" + postFilmDTO.getGenre() + "%"));
        }

        criteriaQuery.select(filmRoot).where(predicates.toArray(new Predicate[0]));

        long totalRecords = entityManager.createQuery(criteriaQuery).getResultList().size();

        Pageable pageable = PageRequest.of(page, size);
        List<Film> films = entityManager.createQuery(criteriaQuery)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
        return new PageImpl<>(films, pageable, totalRecords);
    }
}
