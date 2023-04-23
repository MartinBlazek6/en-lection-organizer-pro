package com.example.enlectortool.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class Lection {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long lectionId;
    private String title;
    private String level;

    public Lection(String title, String level) {
        this.title = title;
        this.level = level;
    }

    @OneToOne(mappedBy = "lection", cascade = CascadeType.ALL)
    private Student student;
}
