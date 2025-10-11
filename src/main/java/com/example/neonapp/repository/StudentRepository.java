package com.example.neonapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.neonapp.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
