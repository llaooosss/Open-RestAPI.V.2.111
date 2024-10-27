package com.example.demo.entity;

import com.example.demo.DTO.FilmDto;
import jakarta.persistence.criteria.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FilmSpecification {

//    @Bean
//    public Specification<Film> filterFilms(FilmDto filmDTO) {
//        return ((root, query, criteriaBuilder) -> {
//            List<Predicate> predicates = new ArrayList<>();
//
//            if (filmDTO.getKinopoiskId() != null) {
//                predicates.add(criteriaBuilder.equal(root.get("kinopoiskId"), filmDTO.getKinopoiskId()));
//            }
//
//            if (filmDTO.getNameRu() != null) {
//                predicates.add(criteriaBuilder.equal(root.get("nameRu"), "%" + filmDTO.getNameRu() + "%"));
//            }
//
//            if (filmDTO.getGenre() != null) {
//                predicates.add(criteriaBuilder.like(root.get("genre"), "%" + filmDTO.getGenre() + "%"));
//            }
//
//            if (filmDTO.getRatingKinopoisk() != null) {
//                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("ratingKinopoisk"), filmDTO.getRatingKinopoisk()));
//            }
//
//            if (filmDTO.getRatingKinopoisk() != null) {
//                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("ratingKinopoisk"), filmDTO.getRatingKinopoisk()));
//            }
//
//            if (filmDTO.getYear() != null) {
//                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("year"), filmDTO.getYear()));
//            }
//
//            if (filmDTO.getYear() != null) {
//                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("year"), filmDTO.getYear()));
//            }
//
//            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
//        });
//    }


    public static Specification<Film> ratingBetween(Double min, Double max) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (min != null && max != null) {
                return criteriaBuilder.between(root.get("rating"), min, max);
            }
            return criteriaBuilder.conjunction();
        };
    }

    public static Specification<Film> yearBetween(Integer startYear, Integer endYear) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (startYear != null && endYear != null) {
                return criteriaBuilder.between(root.get("year"), startYear, endYear);
            }
            return criteriaBuilder.conjunction();
        };
    }
}
