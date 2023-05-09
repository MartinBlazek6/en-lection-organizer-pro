package com.example.enlectortool.controller;

import com.example.enlectortool.emailConfiguration.EmailVerificationService;
import com.example.enlectortool.model.DTO.StudentDto;
import com.example.enlectortool.model.Lection;
import com.example.enlectortool.model.Student;
import com.example.enlectortool.repository.StudentRepository;
import com.example.enlectortool.service.DateService;
import com.example.enlectortool.service.LectionService;
import com.example.enlectortool.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class RestController {
    private final EmailVerificationService emailVerificationService;
    private final StudentRepository studentRepository;

    private final StudentService studentService;
    private final LectionService lectionService;
    private final DateService dateService;

    private final String tokenEnv = System.getenv("token");
    private final String SERVER = System.getenv("SERVER");


    @GetMapping("/login/{id}/{hashcode}")
    public ResponseEntity emailOk(@PathVariable Long id, @PathVariable int hashcode) {

        Student user = studentRepository.getReferenceById(id);

        if (emailVerificationService.controlEmailToken(user) == hashcode) {
            user.setIsActive(true);
            studentRepository.saveAndFlush(user);
            return ResponseEntity.ok("<body style=\"background-color: #f2f2f2; font-family: Arial, sans-serif; margin: 0; padding: 0; text-align: center;\">\n" +
                    "\t<h1 style=\"color: #0077cc; margin-top: 50px;\">Welcome to english class inc!</h1>\n" +
                    "\t<p style=\"font-size: 18px; margin-top: 20px;\">We are happy to see you "+user.getFirstName()+". With this Id you can assign to lection, Your ID:  "+user.getStudentId()+"</p>\n" +
                    "\t<a href=\""+SERVER+"\" target=\"_blank\" style=\"background-color: #0077cc; border: none; border-radius: 5px; color: #fff; font-size: 16px; padding: 10px 20px; margin-top: 20px; cursor: pointer; text-decoration: none;\" onmouseover=\"this.style.backgroundColor='#0066b3'\" onmouseout=\"this.style.backgroundColor='#0077cc'\">Go to page</a>\n" +
                    "</body>");


        }
        return ResponseEntity.status(400).body("Something went wrong");
    }

    @GetMapping("/lections")
    public ResponseEntity<List<Lection>> allLections() {
        return new ResponseEntity<>(lectionService.getAllLections(), HttpStatus.OK);
    }

    @PostMapping("/addStudent")
    public ResponseEntity<Student> addStudent(@RequestBody StudentDto studentDto) {
        return new ResponseEntity<>(studentService.registerNewUser(studentDto),HttpStatus.CREATED);
    }

    @PostMapping("/createLection")
    public ResponseEntity<String> createLection(@RequestParam String title, String level, String date){
        Date dateConverted = dateService.convertStringToDate(date);
        lectionService.createLection(title,level,dateConverted);
        return new ResponseEntity<>("Lection " + title + " created.",HttpStatus.CREATED);
    }

    @PostMapping("/sendInvoices")
    public ResponseEntity<String> sendInvoices(@RequestParam Long lectionId){
        emailVerificationService.sendInvoiceByLection(lectionService.getLectionById(lectionId));
        return new ResponseEntity<>("Invoices for: "+lectionService.getLectionById(lectionId).getTitle()+ " sent.",HttpStatus.OK);
    }

    @PostMapping("/addStudentToLection")
    public ResponseEntity<String> addStudentToLection(@RequestParam Long lectionId,Long studentId){
        studentService.addStudentToLection(lectionId,studentId);
        return new ResponseEntity<>("",HttpStatus.OK);
    }
}

