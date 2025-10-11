package com.example.neonapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.neonapp.model.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}
