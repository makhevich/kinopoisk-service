package ru.abrosimov.kinopoiskservice.service;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username:noreply@bank.ru}")
    private String senderEmail;

    public void sendReportWithAttachment(String toEmail, byte[] fileData, String fileName) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            // true означает multipart (письмо с вложенным файлом)
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(senderEmail);
            helper.setTo(toEmail);
            helper.setSubject("Отчет по фильмам от Стримингового Сервиса");
            helper.setText("Здравствуйте! Во вложении направляем актуальный отчет по каталогу фильмов.");

            // Прикрепляем наш CSV отчет
            helper.addAttachment(fileName, new ByteArrayResource(fileData));

            mailSender.send(message);
            log.info("Отчет успешно отправлен на адрес: {}", toEmail);
        } catch (Exception e) {
            log.error("Не удалось отправить письмо на {}", toEmail, e);
            throw new RuntimeException("Ошибка при отправке письма: " + e.getMessage());
        }
    }
}