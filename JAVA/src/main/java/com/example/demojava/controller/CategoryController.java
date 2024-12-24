package com.example.demojava.controller;

import com.example.demojava.models.Category;
import com.example.demojava.repository.CategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@RequestMapping("api/category")
@RestController
@CrossOrigin(origins = "http://localhost:5173") // הגדרה מדויקת למקור (CORS)
public class CategoryController {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // הוספת קטגוריה
    @PostMapping("/addCategory")
    public ResponseEntity<Category> addCategory(@RequestBody Category category) {
        Category newCategory = categoryRepository.save(category);
        return new ResponseEntity<>(newCategory, HttpStatus.CREATED);
    }

    // החזרת כל הקטגוריות
    @GetMapping("/getCategories")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    // החזרת קטגוריה לפי ID
    @GetMapping("/getCategory/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(category.get(), HttpStatus.OK);
    }

    // עדכון קטגוריה לפי ID
    @PutMapping("/updateCategory/{id}")
    public ResponseEntity<Category> updateCategory(@RequestBody Category category, @PathVariable Long id) {
        if (!categoryRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        category.setId(id); // מבטיחים שה-ID תואם את הנתיב
        Category updatedCategory = categoryRepository.save(category);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

    // מחיקת קטגוריה לפי ID
    @DeleteMapping("/deleteCategory/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        if (!categoryRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        categoryRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
