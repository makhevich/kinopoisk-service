package ru.abrosimov.kinopoiskservice.repository;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import ru.abrosimov.kinopoiskservice.entity.Film;

import java.util.ArrayList;
import java.util.List;

public class FilmSpecification {

    public static Specification<Film> filterBy(
            String keyword,
            Integer yearFrom,
            Integer yearTo,
            Double ratingFrom,
            Double ratingTo
    ) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 1. Поиск по подстроке в названии (без учета регистра)
            if (keyword != null && !keyword.isBlank()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("filmName")),
                        "%" + keyword.toLowerCase() + "%"
                ));
            }

            // 2. Год от
            if (yearFrom != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("releaseYear"), yearFrom));
            }

            // 3. Год до
            if (yearTo != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("releaseYear"), yearTo));
            }

            // 4. Рейтинг от
            if (ratingFrom != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("rating"), ratingFrom));
            }

            // 5. Рейтинг до
            if (ratingTo != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("rating"), ratingTo));
            }

            // Склеиваем все активные условия через логическое "AND"
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}