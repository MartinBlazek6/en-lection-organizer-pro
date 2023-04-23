package com.example.enlectortool.repository;

import com.example.enlectortool.model.Lection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LectionRepository extends JpaRepository<Lection,Long> {
}
