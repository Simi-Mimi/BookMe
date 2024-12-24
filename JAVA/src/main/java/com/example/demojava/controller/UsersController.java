package com.example.demojava.controller;

import com.example.demojava.models.Professional;
import com.example.demojava.models.Users;
import com.example.demojava.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("api/users")
@RestController
//מאפשר לשרת לקבוע מי רשאי לבצע בקשות מכתובות URL שונות
//ולכן מטעמי אבטחה כדאי להגדיר את המקור
@CrossOrigin (origins = "http://localhost:5173") // הגדרה מדויקת למקור (CORS)
public class UsersController {
    @Autowired
    private UsersRepository usersRepository; // הוספת @Autowired כאן
    public UsersController(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

//שולחים json
// זה מוסיף משתמש
//מחזיר סטטוס ואת פרטי המשתמש החדש
    @PostMapping("/addUsers")
    public ResponseEntity<?> addUsers(@RequestBody Users users) {
// בדיקה אם משתמש עם כתובת האימייל כבר קיים
        Optional<Users> existingUser = usersRepository.findByEmail(users.getEmail());
        if (existingUser.isPresent()) {
// אם המשתמש קיים, מחזירים שגיאה מתאימה
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
// אם המשתמש לא קיים, מוסיפים אותו
        Users newUsers = usersRepository.save(users);
        return new ResponseEntity<>(newUsers, HttpStatus.CREATED);
    }

//לא מקבל כלום
//מחזיר רשימת משתמשים
    @GetMapping("/getUsers")
    public List<Users> getUsersById() {
        return  usersRepository.findAll();
    }

//מקבל id
//מחזיר את האובייקט לפי id שלו
    @GetMapping("/getUsers/{id}")
    public ResponseEntity <Users> getUsersById(@PathVariable Long id) {
        Users U=usersRepository.findById(id).get();
        if(U==null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(U,HttpStatus.OK);
    }

//מקבל id
//מעדכן משתמשים לפי id
    @PutMapping("/updateUsers/{id}")
    public ResponseEntity <Users> updateUsers(@RequestBody Users users, @PathVariable Long id) {
        if (id != users.getId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        Users u=usersRepository.save(users);
        return new ResponseEntity<>(u,HttpStatus.NO_CONTENT);
    }

//מקבל id
//מוחק משתמש לפי id
    @DeleteMapping("/deleteUsers/{id}")
    public ResponseEntity <Users> deleteUsers(@PathVariable Long id){
        usersRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody Users users) {
        if (users.getEmail() == null || users.getEmail().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("חסר שם");
        }
        List<Users> usersList = usersRepository.findAll(); // Use the injected instance here
        for (Users u : usersList) {
            if (u.getEmail().equals(users.getEmail())) { // Compare email correctly
                if (u.getPassword().equals(users.getPassword())) { // Compare password correctly
                    return ResponseEntity.ok(u);
                } else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("הסיסמא שגויה");
                }
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("המשתמש לא נמצא");
    }
}
