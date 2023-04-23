package com.example.enlectortool.controller;

import com.example.enlectortool.emailConfiguration.EmailVerificationService;
import com.example.enlectortool.model.Student;
import com.example.enlectortool.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api/v1")
public class RestController {
    private final EmailVerificationService emailVerificationService;
    private final StudentRepository studentRepository;


    @GetMapping("/login/{id}/{hashcode}")
    public ResponseEntity emailOk(@PathVariable Long id, @PathVariable int hashcode) {

        Student user = studentRepository.getReferenceById(id);

        if (emailVerificationService.controlEmailToken(user) == hashcode) {
            user.setIsActive(true);
            studentRepository.saveAndFlush(user);
            return ResponseEntity.ok("<body style=\"background-color: #f2f2f2; font-family: Arial, sans-serif; margin: 0; padding: 0; text-align: center;\">\n" +
                    "\t<h1 style=\"color: #0077cc; margin-top: 50px;\">Welcome to english class inc!</h1>\n" +
                    "\t<p style=\"font-size: 18px; margin-top: 20px;\">We are happy to see you "+user.getFirstName()+". With this Id you can assign to lection "+user.getStudentId()+"</p>\n" +
                    "\t<button style=\"background-color: #0077cc; border: none; border-radius: 5px; color: #fff; font-size: 16px; padding: 10px 20px; margin-top: 20px; cursor: pointer;\" onmouseover=\"this.style.backgroundColor='#0066b3'\" onmouseout=\"this.style.backgroundColor='#0077cc'\" onclick=\"redirectToPage\">Go to page</button>\n" +
                    "\n" +
                    "\t<script>\n" +
                    "\t\tfunction redirectToPage() {\n" +
                    "\t\t\twindow.location.href = \"http://localhost:8080\";\n" +
                    "\t\t}\n" +
                    "\t</script>\n" +
                    "</body>");
        }
        return ResponseEntity.status(400).body("Something went wrong");
    }
}

