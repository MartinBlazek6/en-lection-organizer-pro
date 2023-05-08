package com.example.enlectortool.emailConfiguration;


import com.example.enlectortool.model.Lection;
import com.example.enlectortool.model.Student;
import com.example.enlectortool.repository.LectionRepository;
import com.example.enlectortool.repository.StudentRepository;
import com.example.enlectortool.service.LectionService;
import com.example.enlectortool.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class EmailVerificationService {

    private final EmailService emailService;
    private final StudentRepository studentRepository;
    private final LectionService lectionService;
    private final String SERVER = System.getenv("SERVER");

    public void createEmailToken(Student user) {
        studentRepository.saveAndFlush(user);

        String secret = user.getFirstName() + user.getEmail() + "Đ";
        int secretInt = secret.hashCode();
        String link = SERVER + "/api/v1/login/" + user.getStudentId() + "/" + secretInt;
        emailService.sendMailWithHtml(user.getEmail(), buildEmail(user.getFirstName(), link));
    }

    public void sendInvoiceByLection(Lection lection) {
        lectionService.getAllStudentsByLectionAndStatus(lection, false).forEach(student -> {
            String secret = student.getFirstName() + student.getEmail() + "Đ";
            int secretInt = secret.hashCode();
            String link = SERVER + "/api/v1/login/" + student.getStudentId() + "/" + secretInt;
            emailService.sendMailWithHtml(student.getEmail(), sendInvoiceEmail(student.getFirstName(), link));
        });
    }

    public int controlEmailToken(Student user) {
        String secret = user.getFirstName() + user.getEmail() + "Đ";
        return secret.hashCode();
    }

    private String buildEmail(String name, String link) {

        return "<body style=\"background-color: #ffffff; color: #000000; font-family: Arial, sans-serif; font-size: 16px; line-height: 1.5; margin: 0; padding: 0;\">\n" +
                "\t<div class=\"container\" style=\"margin: 0 auto; max-width: 600px; padding: 20px;\">\n" +
                "\t\t<img class=\"logo\" src=\"https://img.freepik.com/free-vector/hand-drawn-english-school-logo-template_23-2149496656.jpg?w=2000\" alt=\"Logo\" style=\"display: block; margin: 0 auto; max-width: 200px;\">\n" +
                "\t\t<h1 style=\"color: #000000; font-size: 28px; font-weight: normal; margin: 40px 0; text-align: center; text-transform: uppercase;\">Welcome to English Lessons</h1>\n" +
                "\t\t<p style=\"margin-bottom: 20px; text-align: justify;\">Dear " + name + ",</p>\n" +
                "\t\t<p style=\"margin-bottom: 20px; text-align: justify;\">Thank you for choosing to learn English with us! We're excited to have you join our community of English learners who are passionate about improving their language skills.</p>\n" +
                "\t\t<p style=\"margin-bottom: 20px; text-align: justify;\">To start your English lessons, please click the button below:</p>\n" +
                "\t\t<a class=\"btn\" href=" + link + " style=\"background-color: #007bff; border-radius: 5px; color: #ffffff; display: inline-block; font-size: 16px; font-weight: bold; margin-top: 20px; padding: 10px 20px; text-align: center; text-decoration: none; text-transform: uppercase;\">Start Your Lessons</a>\n" +
                "\t\t<p style=\"margin-bottom: 20px; text-align: justify;\">If you have any questions about your lessons or our program, please don't hesitate to contact us at info@englishlessons.com.</p>\n" +
                "\t\t<p style=\"margin-bottom: 20px; text-align: justify;\">Best regards,</p>\n" +
                "\t\t<p style=\"margin-bottom: 20px; text-align: justify;\">The English Lessons Team</p>\n" +
                "\t</div>\n" +
                "</body>";

    }

    private String sendInvoiceEmail(String name, String link) {

        return "<body style=\"background-color: #ffffff; color: #000000; font-family: Arial, sans-serif; font-size: 16px; line-height: 1.5; margin: 0; padding: 0;\">\n" +
                "\t<div class=\"container\" style=\"margin: 0 auto; max-width: 600px; padding: 20px; border: 1px solid #ddd; border-radius: 5px;\">\n" +
                "\t\t<img class=\"logo\" src=\"https://images.lifesizecustomcutouts.com/image/cache/catalog/00000products/page3/H79507-500x500.PNG\" alt=\"Logo\" style=\"display: block; margin: 0 auto; max-width: 200px;\">\n" +
                "\t\t<h1 style=\"color: #000000; font-size: 28px; font-weight: normal; margin: 40px 0; text-align: center; text-transform: uppercase;\">Invoice</h1>\n" +
                "\t\t<p style=\"margin-bottom: 20px; text-align: justify;\">Dear "+name+", where is my money!!!</p>\n" +
                "\t\t<table style=\"width: 100%; border-collapse: collapse;\">\n" +
                "\t\t\t<thead>\n" +
                "\t\t\t\t<tr>\n" +
                "\t\t\t\t\t<th style=\"text-align: left; padding: 10px; background-color: #f2f2f2; border: 1px solid #ddd;\">Item</th>\n" +
                "\t\t\t\t\t<th style=\"text-align: left; padding: 10px; background-color: #f2f2f2; border: 1px solid #ddd;\">Description</th>\n" +
                "\t\t\t\t\t<th style=\"text-align: left; padding: 10px; background-color: #f2f2f2; border: 1px solid #ddd;\">Amount</th>\n" +
                "\t\t\t\t</tr>\n" +
                "\t\t\t</thead>\n" +
                "\t\t\t<tbody>\n" +
                "\t\t\t\t<tr>\n" +
                "\t\t\t\t\t<td style=\"text-align: left; padding: 10px; border: 1px solid #ddd;\">English Lessons</td>\n" +
                "\t\t\t\t\t<td style=\"text-align: left; padding: 10px; border: 1px solid #ddd;\">Payment for English lessons</td>\n" +
                "\t\t\t\t\t<td style=\"text-align: left; padding: 10px; border: 1px solid #ddd;\">[Amount]</td>\n" +
                "\t\t\t\t</tr>\n" +
                "\t\t\t</tbody>\n" +
                "\t\t\t<tfoot>\n" +
                "\t\t\t\t<tr>\n" +
                "\t\t\t\t\t<td style=\"text-align: left; padding: 10px; border: 1px solid #ddd;\"></td>\n" +
                "\t\t\t\t\t<td style=\"text-align: left; padding: 10px; border: 1px solid #ddd;\">Total</td>\n" +
                "\t\t\t\t\t<td style=\"text-align: left; padding: 10px; border: 1px solid #ddd;\">[Amount]</td>\n" +
                "\t\t\t\t</tr>\n" +
                "\t\t\t</tfoot>\n" +
                "\t\t<a class=\"btn\" href=" + link + " style=\"background-color: #007bff; border-radius: 5px; color: #ffffff; display: inline-block; font-size: 16px; font-weight: bold; margin-top: 20px; padding: 10px 20px; text-align: center; text-decoration: none; text-transform: uppercase;\">provide payment</a>\n" +
                "\t\t</table>\n" +
                "\t\t<p style=\"margin-top: 20px; margin-bottom: 20px; text-align: justify;\">Thank you for your payment. If you have any questions or concerns, please don't hesitate to contact us at info@englishlessons.com.</p>\n" +
                "\t\t<p style=\"margin-bottom: 20px; text-align: justify;\">Best regards,</p>\n" +
                "\t\t<p style=\"margin-bottom: 20px; text-align: justify;\">The English Lessons Team</p>\n" +
                "\t</div>\n" +
                "</body>\n";


    }
}
