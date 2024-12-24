package com.example.demojava.controller;

import com.example.demojava.models.Subcategory;
import com.example.demojava.repository.SubcategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/subcategories") // שם קונבנציונלי לכתובת URL
@CrossOrigin(origins = "http://localhost:5173") // הגדרה מדויקת למקור (CORS)
public class SubcategoryController {

    private final SubcategoryRepository subcategoryRepository;

    @Autowired
    public SubcategoryController(SubcategoryRepository subcategoryRepository) {
        this.subcategoryRepository = subcategoryRepository;
    }

    // מחזיר את כל תתי-הקטגוריות
    @GetMapping("/getSubategories")
    public ResponseEntity<List<Subcategory>> getAllSubcategories() {
        List<Subcategory> subcategories = subcategoryRepository.findAll();
        return new ResponseEntity<>(subcategories, HttpStatus.OK); // מחזיר 200 OK עם הרשימה
    }

    // מחזיר תת-קטגוריה לפי מזהה (id)
    @GetMapping("/getSubategories/{id}")
    public ResponseEntity<Subcategory> getSubcategoryById(@PathVariable Long id) {
        Optional<Subcategory> subcategory = subcategoryRepository.findById(id);

        if (subcategory.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND); // מחזיר 404 אם לא נמצא
        }

        return new ResponseEntity<>(subcategory.get(), HttpStatus.OK); // מחזיר 200 OK אם נמצא
    }

    // הוספת תת-קטגוריה חדשה
    @PostMapping
    public ResponseEntity<Subcategory> addSubcategory(@RequestBody Subcategory subcategory) {
        Subcategory newSubcategory = subcategoryRepository.save(subcategory);
        return new ResponseEntity<>(newSubcategory, HttpStatus.CREATED); // מחזיר 201 CREATED
    }

    // עדכון תת-קטגוריה לפי מזהה (id)
    @PutMapping("/{id}")
    public ResponseEntity<Subcategory> updateSubcategory(@RequestBody Subcategory subcategory, @PathVariable Long id) {
        if (!subcategoryRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // מחזיר 404 אם ה-ID לא קיים
        }

        subcategory.setId(id); // מבטיחים שה-ID תואם
        Subcategory updatedSubcategory = subcategoryRepository.save(subcategory);
        return new ResponseEntity<>(updatedSubcategory, HttpStatus.OK); // מחזיר 200 OK עם הנתון המעודכן
    }

    // מחיקת תת-קטגוריה לפי מזהה (id)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubcategory(@PathVariable Long id) {
        if (!subcategoryRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // מחזיר 404 אם ה-ID לא קיים
        }

        subcategoryRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // מחזיר 204 NO CONTENT לאחר מחיקה
    }
}
