package ru.abrosimov.kinopoiskservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.abrosimov.kinopoiskservice.entity.Film;

import java.util.Optional;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {

    // Spring Data JPA сам сгенерирует SQL-запрос по названию этого метода!
    boolean existsByFilmId(Long filmId);

    Optional<Film> findByFilmId(Long filmId);
}