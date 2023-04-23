package com.example.enlectortool.controller;


import com.example.enlectortool.model.DTO.StudentDto;
import com.example.enlectortool.service.LectionService;
import com.example.enlectortool.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class MvcController {

    private final StudentService studentService;
    private final LectionService lectionService;

    @GetMapping("/")
    public String mainPage(Model model) {
        model.addAttribute("studentDto", new StudentDto());
        model.addAttribute("lections",lectionService.getAllLections());
        return "index";
    }

    @PostMapping("/addStudent")
    public String addStudent(@ModelAttribute("studentDto") StudentDto studentDto) {
        studentService.registerNewUser(studentDto);
        return "redirect:/";
    }

    @PostMapping("/createLection")
    public String createLection(@RequestParam String title, String level){
        lectionService.createLection(title,level);
        return "redirect:/";
    }

    @PostMapping("/addStudentToLection")
    public String addStudentToLection(@RequestParam Long lectionId,Long studentId){
       studentService.addStudentToLection(lectionId,studentId);
        return "redirect:/";
    }
}
