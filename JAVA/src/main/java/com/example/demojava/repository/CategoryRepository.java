package com.example.demojava.repository;

import com.example.demojava.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository <Category, Long>{
}
