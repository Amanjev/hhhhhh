package com.example.neonapp.repository;

import com.example.neonapp.model.Solution;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SolutionRepository extends JpaRepository<Solution, Long> {
}
