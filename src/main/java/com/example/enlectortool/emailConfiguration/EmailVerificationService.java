package com.example.enlectortool.emailConfiguration;


import com.example.enlectortool.model.Student;
import com.example.enlectortool.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class EmailVerificationService {

    private final EmailService emailService;
    private final StudentRepository studentRepository;
    private final String SERVER = System.getenv("SERVER");

    public void createEmailToken(Student user) {
        studentRepository.saveAndFlush(user);

        String secret = user.getFirstName() + user.getEmail() + "Đ";
        int secretInt = secret.hashCode();
        String link = SERVER+"/api/v1/login/" + user.getStudentId() + "/" + secretInt;
        emailService.sendMailWithHtml(user.getEmail(), buildEmail(user.getFirstName(), link));
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
                "\t\t<p style=\"margin-bottom: 20px; text-align: justify;\">Dear "+name+",</p>\n" +
                "\t\t<p style=\"margin-bottom: 20px; text-align: justify;\">Thank you for choosing to learn English with us! We're excited to have you join our community of English learners who are passionate about improving their language skills.</p>\n" +
                "\t\t<p style=\"margin-bottom: 20px; text-align: justify;\">To start your English lessons, please click the button below:</p>\n" +
                "\t\t<a class=\"btn\" href="+link+" style=\"background-color: #007bff; border-radius: 5px; color: #ffffff; display: inline-block; font-size: 16px; font-weight: bold; margin-top: 20px; padding: 10px 20px; text-align: center; text-decoration: none; text-transform: uppercase;\">Start Your Lessons</a>\n" +
                "\t\t<p style=\"margin-bottom: 20px; text-align: justify;\">If you have any questions about your lessons or our program, please don't hesitate to contact us at info@englishlessons.com.</p>\n" +
                "\t\t<p style=\"margin-bottom: 20px; text-align: justify;\">Best regards,</p>\n" +
                "\t\t<p style=\"margin-bottom: 20px; text-align: justify;\">The English Lessons Team</p>\n" +
                "\t</div>\n" +
                "</body>";

//        return "<body style=\"background-color: #0f0f0f; color: #ffffff; font-family: Arial, sans-serif; font-size: 16px; line-height: 1.5; margin: 0; padding: 0;\">\n" +
//                "\t<div class=\"container\" style=\"margin: 0 auto; max-width: 600px; padding: 20px;\">\n" +
//                "\t\t<img class=\"logo\" src=\"https://dreamhubonline.com/wp-content/uploads/2018/11/Dream-Hub-Logo-White.png\" alt=\"Dreamhub Logo\" style=\"display: block; margin: 0 auto; max-width: 200px;\">\n" +
//                "\t\t<h1 style=\"color: #c1c1c1; font-size: 28px; font-weight: normal; margin: 40px 0; text-align: center; text-transform: uppercase;\">Registration Confirmation</h1>\n" +
//                "\t\t<p style=\"margin-bottom: 20px; text-align: justify;\">Dear "+name+",</p>\n" +
//                "\t\t<p style=\"margin-bottom: 20px; text-align: justify;\">Thank you for registering with Dreamhub! We're excited to have you join our community of like-minded individuals who share a passion for making dreams a reality.</p>\n" +
//                "\t\t<p style=\"margin-bottom: 20px; text-align: justify;\">To activate your account, please click the button below:</p>\n" +
//                "\t\t<a class=\"btn\" href=\""+link+"\" style=\"background-color: #ffcc00; border-radius: 5px; color: #0f0f0f; display: inline-block; font-size: 16px; font-weight: bold; margin-top: 20px; padding: 10px 20px; text-align: center; text-decoration: none; text-transform: uppercase;\">Activate Your Account</a>\n" +
//                "\t\t<p style=\"margin-bottom: 20px; text-align: justify;\">If you didn't register with Dreamhub or if you have any questions, please contact us at support@dreamhub.com.</p>\n" +
//                "\t\t<p style=\"margin-bottom: 20px; text-align: justify;\">Best regards,</p>\n" +
//                "\t\t<p style=\"margin-bottom: 20px; text-align: justify;\">The Dreamhub Team</p>\n" +
//                "\t</div>\n" +
//                "</body>";
    }
}
