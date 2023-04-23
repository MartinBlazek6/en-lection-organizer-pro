package com.example.enlectortool.emailConfiguration;

public interface EmailService {
    String sendSimpleMail(String to, String link);

    String sendMailWithHtml(String to, String link);
}
