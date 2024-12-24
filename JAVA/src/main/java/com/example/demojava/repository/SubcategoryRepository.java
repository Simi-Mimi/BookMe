package com.example.demojava.repository;

import com.example.demojava.models.Subcategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubcategoryRepository  extends JpaRepository<Subcategory, Long> {
}

