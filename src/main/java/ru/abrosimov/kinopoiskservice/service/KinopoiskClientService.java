package ru.abrosimov.kinopoiskservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.abrosimov.kinopoiskservice.dto.KinopoiskResponseDto;

@Service
@RequiredArgsConstructor
public class KinopoiskClientService {

    private final RestTemplate restTemplate;

    @Value("${kinopoisk.api.token}")
    private String apiToken;

    private static final String KINOPOISK_API_URL = "https://kinopoiskapiunofficial.tech/api/v2.2/films";

    public KinopoiskResponseDto fetchFilmsFromKinopoisk(
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
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-API-KEY", apiToken);
        headers.set("Content-Type", "application/json");

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(KINOPOISK_API_URL);

        // Аккуратно навешиваем только те параметры, которые передал клиент
        if (keyword != null && !keyword.isBlank()) uriBuilder.queryParam("keyword", keyword);
        if (countries != null) uriBuilder.queryParam("countries", countries);
        if (genres != null) uriBuilder.queryParam("genres", genres);
        if (order != null && !order.isBlank()) uriBuilder.queryParam("order", order);
        if (type != null && !type.isBlank()) uriBuilder.queryParam("type", type);
        if (ratingFrom != null) uriBuilder.queryParam("ratingFrom", ratingFrom);
        if (ratingTo != null) uriBuilder.queryParam("ratingTo", ratingTo);
        if (yearFrom != null) uriBuilder.queryParam("yearFrom", yearFrom);
        if (yearTo != null) uriBuilder.queryParam("yearTo", yearTo);
        if (page != null) uriBuilder.queryParam("page", page);

        String finalUrl = uriBuilder.toUriString();

        ResponseEntity<KinopoiskResponseDto> response = restTemplate.exchange(
                finalUrl,
                HttpMethod.GET,
                requestEntity,
                KinopoiskResponseDto.class
        );

        return response.getBody();
    }
}