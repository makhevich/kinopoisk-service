package ru.abrosimov.kinopoiskservice.service;

import org.springframework.stereotype.Service;
import ru.abrosimov.kinopoiskservice.entity.Film;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class CsvReportService {

    public byte[] generateCsvReport(List<Film> films) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try (PrintWriter writer = new PrintWriter(out, true, StandardCharsets.UTF_8)) {
            // BOM-метка, чтобы Excel понял кодировку UTF-8 (русские буквы)
            out.write(0xEF);
            out.write(0xBB);
            out.write(0xBF);

            // Шапка таблицы
            writer.println("ID;Kinopoisk ID;Название фильма;Год выпуска;Рейтинг");

            // Данные
            for (Film film : films) {
                // Преобразуем рейтинг: заменяем точку на запятую (8.5 -> 8,5),
                // чтобы русский Excel понял, что это число с дробью, а не дата!
                String ratingStr = "";
                if (film.getRating() != null) {
                    ratingStr = String.valueOf(film.getRating()).replace('.', ',');
                }

                String yearStr = film.getReleaseYear() != null ? String.valueOf(film.getReleaseYear()) : "";
                String cleanName = film.getFilmName() != null ? film.getFilmName().replace("\"", "\"\"") : "";

                // Записываем строго через точку с запятой
                writer.println(
                        film.getId() + ";" +
                                film.getFilmId() + ";" +
                                "\"" + cleanName + "\";" +
                                yearStr + ";" +
                                ratingStr
                );
            }
            writer.flush();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при формировании CSV отчета", e);
        }

        return out.toByteArray();
    }
}