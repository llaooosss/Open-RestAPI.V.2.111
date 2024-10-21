package com.example.demo321.entity;

import jakarta.persistence.criteria.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class FilmSpecification {

    public Specification<Film> filterFilm(String nameRu,
                                                 BigDecimal ratingKinopoiskTo,
                                                 BigDecimal ratingKinopoiskFrom,
                                                 Integer yearFrom,
                                                 Integer yearTo) {
        return (root, query, criteriaBuilder) -> {


            if (!Objects.equals(ratingKinopoiskTo, null)) {
                Predicate ratingPredicate = criteriaBuilder.greaterThanOrEqualTo(root.get("ratingKinopoisk"), ratingKinopoiskTo);
                if (!Objects.equals(ratingKinopoiskFrom, null)) {
                    ratingPredicate = criteriaBuilder.and(ratingPredicate, criteriaBuilder.lessThanOrEqualTo(root.get("ratingKinopoisk"), ratingKinopoiskFrom));
                }

                Predicate nameRuPredicate = criteriaBuilder.like(root.get("nameRu"), StringUtils.isBlank(nameRu) ? likePattern("") : nameRu);


                Predicate yearsPredicate = criteriaBuilder.between(root.get("yearProduction"), yearFrom, yearTo);


                return criteriaBuilder.and(ratingPredicate, nameRuPredicate, yearsPredicate);
            } else {
                return criteriaBuilder.conjunction();
            }
        };
    }

    private static String likePattern(String value) {
        return "%" + value + "%";
    }

}
