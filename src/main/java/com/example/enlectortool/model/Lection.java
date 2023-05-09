package com.example.enlectortool.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
public class Lection {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long lectionId;
    private String title;
    private String level;
    private Date date;

    public Lection(String title, String level) {
        this.title = title;
        this.level = level;
    }

    public Lection(String title, String level, Date date) {
        this.title = title;
        this.level = level;
        this.date = date;
    }

    @ManyToMany
    @JsonIgnore
    private List<Student> student;
}
