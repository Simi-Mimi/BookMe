package com.example.demojava.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Professional {

    @Id
    @GeneratedValue
    private Long id;
    protected String name;
    protected String password;
    protected String email;
    private String gender;

    @ManyToOne
    @JoinColumn(name = "subcategory_id", nullable = false) // שייך לתת קטגוריה
    private Subcategory subcategory;

    @OneToMany(mappedBy = "professional", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<CalendarEntry> calendarEntries;

    public Professional() {
    }

    public Professional(Long id, String name, String password, String email, String gender, Subcategory subcategory, List<CalendarEntry> calendarEntries) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.gender = gender;
        this.subcategory = subcategory;
        this.calendarEntries = calendarEntries;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Subcategory getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(Subcategory subcategory) {
        this.subcategory = subcategory;
    }

    public List<CalendarEntry> getCalendarEntries() {
        return calendarEntries;
    }

    public void setCalendarEntries(List<CalendarEntry> calendarEntries) {
        this.calendarEntries = calendarEntries;
    }
}
