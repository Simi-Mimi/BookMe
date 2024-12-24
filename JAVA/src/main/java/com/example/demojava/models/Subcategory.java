package com.example.demojava.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class Subcategory {
    @Id
    @GeneratedValue
    private long id;
    private String name; // שם תת הקטגוריה

    @ManyToOne
    private Category category; // הקטגוריה הראשית שאליה שייכת תת הקטגוריה

    public Subcategory() {}

    public Subcategory(long id, String name, Category category) {
        this.id = id;
        this.name = name;
        this.category = category;
    }

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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
