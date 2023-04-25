package com.example.enlectortool.service;

import com.example.enlectortool.emailConfiguration.EmailVerificationService;
import com.example.enlectortool.model.Lection;
import com.example.enlectortool.model.Student;
import com.example.enlectortool.model.DTO.StudentDto;
import com.example.enlectortool.repository.LectionRepository;
import com.example.enlectortool.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final LectionRepository lectionRepository;
    private final EmailVerificationService emailVerificationService;


    public Student registerNewUser(StudentDto user) {
        Student student = new Student(user.getFirstName(), user.getLastName(), user.getEmail());
        emailVerificationService.createEmailToken(student);
        return studentRepository.save(student);
    }

    public void addStudentToLection(Long lectionId,Long studentId){
        Lection lection = lectionRepository.getReferenceById(lectionId);
        Student student = studentRepository.getReferenceById(studentId);

        var students = lection.getStudent();
        var lections = student.getLection();

        students.add(student);
        lections.add(lection);

        lection.setStudent(students);
        student.setLection(lections);

        studentRepository.saveAndFlush(student);
        lectionRepository.saveAndFlush(lection);

    }

}
