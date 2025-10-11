package com.example.neonapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.neonapp.model.Student;
import com.example.neonapp.repository.StudentRepository;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentRepository.save(student);
    }

    @GetMapping
    public java.util.List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
}
