package com.example.enlectortool.emailConfiguration;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;



@Service
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {

//    @Autowired
    private final JavaMailSender javaMailSender;
    @Override
    public String sendSimpleMail(String to, String link) {

        try {
            //creating simple mail message
            SimpleMailMessage mailMessage = new SimpleMailMessage();

            //setting up
            mailMessage.setFrom("email@email.com");
            mailMessage.setTo(to);
            mailMessage.setText(link);
            mailMessage.setSubject("Confirm your email");

            //sending mail
            javaMailSender.send(mailMessage);
            return "Mail Sent Successfully...";
        }
        //handle exceptions
        catch (Exception e) {
            return "Error while Sending Mail";
        }
    }

    @Override
    public String sendMailWithHtml(String to, String link) {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;

        try {
            mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setFrom("enclass@gmail.com");
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setText(link, true);
            mimeMessageHelper.setSubject("Confirm your email");

            //sending mail
            javaMailSender.send(mimeMessage);
            return "Mail Sent Successfully";
        }
        //handle exception
        catch (MessagingException e) {
            return "Error while sending Mail";
        }
    }
}
