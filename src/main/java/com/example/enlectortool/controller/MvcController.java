package com.example.enlectortool.controller;


import com.example.enlectortool.emailConfiguration.EmailVerificationService;
import com.example.enlectortool.model.DTO.StudentDto;
import com.example.enlectortool.service.DateService;
import com.example.enlectortool.service.LectionService;
import com.example.enlectortool.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
@RequiredArgsConstructor
public class MvcController {

    private final StudentService studentService;
    private final LectionService lectionService;
    private final EmailVerificationService emailVerificationService;
    private final DateService dateService;

    private final String tokenEnv = System.getenv("token");

    @GetMapping("/")
    public String mainPage(Model model) {
        model.addAttribute("studentDto", new StudentDto());
        model.addAttribute("lections",lectionService.getAllLections());
        return "index";
    }

    @GetMapping("/admin/{token}")
    public String lectorPanel(Model model, @PathVariable String token) {
        if (token.equals(tokenEnv)){
            model.addAttribute("lections",lectionService.getAllLections());
            return "lectorPanel";
        }
        return "redirect:/";
    }


    @GetMapping("/table/{token}")
    public String table(Model model, @PathVariable String token) {
        if (token.equals(tokenEnv)){
            model.addAttribute("lections",lectionService.getAllLections());
            return "table";
        }
        return "redirect:/";
    }

    @PostMapping("/addStudent")
    public String addStudent(@ModelAttribute("studentDto") StudentDto studentDto) {
        studentService.registerNewUser(studentDto);
        return "redirect:/";
    }

    @PostMapping("/createLection")
    public String createLection(@RequestParam String title, String level, String date){

        Date dateConverted = dateService.convertStringToDate(date);
        lectionService.createLection(title,level,dateConverted);
        return "redirect:/admin/"+tokenEnv;
    }

    @PostMapping("/sendInvoices")
    public String sendInvoices(@RequestParam Long lectionId){
        emailVerificationService.sendInvoiceByLection(lectionService.getLectionById(lectionId));
        return "redirect:/admin/"+tokenEnv;
    }

    @PostMapping("/addStudentToLection")
    public String addStudentToLection(@RequestParam Long lectionId,Long studentId){
       studentService.addStudentToLection(lectionId,studentId);
        return "redirect:/";
    }
}
