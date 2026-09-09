package ru.abrosimov.kinopoiskservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.abrosimov.kinopoiskservice.entity.Film;

import java.util.Optional;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long>, JpaSpecificationExecutor<Film> {

    boolean existsByFilmId(Long filmId);

    Optional<Film> findByFilmId(Long filmId);
}