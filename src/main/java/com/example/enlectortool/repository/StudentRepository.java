package com.example.enlectortool.repository;

import com.example.enlectortool.model.Lection;
import com.example.enlectortool.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {
    Student findByEmail(String email);

    List<Student> findAllByLectionAndIsActive(Lection lection, boolean isActive);
}
