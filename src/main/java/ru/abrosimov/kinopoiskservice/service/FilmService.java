package ru.abrosimov.kinopoiskservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.abrosimov.kinopoiskservice.dto.KinopoiskFilmDto;
import ru.abrosimov.kinopoiskservice.dto.KinopoiskResponseDto;
import ru.abrosimov.kinopoiskservice.entity.Film;
import ru.abrosimov.kinopoiskservice.repository.FilmRepository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmService {

    private final KinopoiskClientService kinopoiskClientService;
    private final FilmRepository filmRepository;

    /**
     * Получает фильмы из Кинопоиска, сохраняет отсутствующие в базу
     * и возвращает список обработанных фильмов.
     */
    @Transactional
    public List<Film> fetchAndSaveNewFilms(
            String keyword,
            Integer countries,
            Integer genres,
            String order,
            String type,
            Double ratingFrom,
            Double ratingTo,
            Integer yearFrom,
            Integer yearTo,
            Integer page
    ) {
        KinopoiskResponseDto response = kinopoiskClientService.fetchFilmsFromKinopoisk(
                keyword, countries, genres, order, type, ratingFrom, ratingTo, yearFrom, yearTo, page
        );

        if (response == null || response.getItems() == null || response.getItems().isEmpty()) {
            log.info("От Кинопоиска не получено фильмов по заданным критериям");
            return List.of();
        }

        List<Film> savedFilms = new ArrayList<>();

        for (KinopoiskFilmDto dto : response.getItems()) {
            if (dto.getFilmId() == null) {
                continue;
            }

            if (!filmRepository.existsByFilmId(dto.getFilmId())) {
                Film newFilm = mapToEntity(dto);
                Film saved = filmRepository.save(newFilm);
                savedFilms.add(saved);
                log.info("Сохранен новый фильм: {} (ID: {})", saved.getFilmName(), saved.getFilmId());
            } else {
                log.debug("Фильм с ID {} уже есть в базе, пропускаем", dto.getFilmId());
            }
        }

        return savedFilms;
    }

    // Вспомогательный метод маппинга
    private Film mapToEntity(KinopoiskFilmDto dto) {
        // Выбираем название: если нет русского, берем оригинальное
        String title = dto.getNameRu() != null ? dto.getNameRu() : dto.getNameOriginal();
        if (title == null) {
            title = "Без названия";
        }

        return Film.builder()
                .filmId(dto.getFilmId())
                .filmName(title)
                .releaseYear(dto.getYear())
                .rating(dto.getRating())
                .description(dto.getDescription())
                .build();
    }
}