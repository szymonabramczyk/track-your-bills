package com.example.trackyourbills.repositories;

import com.example.trackyourbills.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
