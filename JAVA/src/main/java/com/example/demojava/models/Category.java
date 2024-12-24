package com.example.demojava.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Category {

    @Id
    @GeneratedValue
    private long id;
    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Subcategory> subcategories; // קשר עם תתי-קטגוריות

    public Category() {
    }

    public Category(long id, String name, List<Subcategory> subcategories) {
        this.id = id;
        this.name = name;
        this.subcategories = subcategories;
    }

    // Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Subcategory> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(List<Subcategory> subcategories) {
        this.subcategories = subcategories;
    }
}
