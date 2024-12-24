package com.example.demojava.controller;

import com.example.demojava.models.CalendarEntry;
import com.example.demojava.models.Professional;
import com.example.demojava.repository.ProfessionalRepository;
import com.example.demojava.service.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/professionals") // שם URL קונבנציונלי
@CrossOrigin (origins = "http://localhost:5173")// הגדרה מדויקת למקור (CORS)
public class ProfessionalController {

    private final ProfessionalRepository professionalRepository;
    private final CalendarService calendarService;

    @Autowired
    public ProfessionalController(ProfessionalRepository professionalRepository, CalendarService calendarService) {
        this.professionalRepository = professionalRepository;
        this.calendarService = calendarService;
    }
//GET============================================================================================================
// GET - רשימת כל בעלי המקצוע
@GetMapping("/getProfessional")
public ResponseEntity<List<Professional>> getAllProfessionals() {
    List<Professional> professionals = professionalRepository.findAll();
    return ResponseEntity.ok(professionals); // 200 OK
}

// GET - החזרת בעל מקצוע לפי ID
    @GetMapping("/getProfessional/{id}")
    public ResponseEntity<?> getProfessionalById(@PathVariable Long id) {
        Optional<Professional> professional = professionalRepository.findById(id);
        if (!professional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("בעל המקצוע לא נמצא");
        }
        return ResponseEntity.ok(professional.get()); // 200 OK
    }

//// GET - יומן לפי ID של בעל מקצוע
//    @GetMapping("/{id}/calendar")
//    public ResponseEntity<?> getCalendar(@PathVariable Long id) {
//        Optional<Professional> professional = professionalRepository.findById(id);
//        if (!professional.isPresent()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("בעל המקצוע לא נמצא");
//        }
//        List<CalendarEntry> calendarEntries = professional.get().getCalendarEntries();
//        return ResponseEntity.ok(calendarEntries); // 200 OK
//    }
//POST===========================================================================================================
// POST - הוספת בעל מקצוע חדש
@PostMapping("/addProfessional")
public ResponseEntity<?> addProfessional(@RequestBody Professional professional) {
    // בדיקה אם בעל מקצוע עם אותו אימייל כבר קיים
    if (professionalRepository.findByEmail(professional.getEmail()).isPresent()) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("בעל מקצוע עם האימייל הזה כבר קיים");
    }
    Professional newProfessional = professionalRepository.save(professional);
    return ResponseEntity.status(HttpStatus.CREATED).body(newProfessional); // 201 CREATED
}

    // POST - התחברות בעל מקצוע
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Professional professional) {
        if (professional.getEmail() == null || professional.getEmail().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("האימייל חסר");
        }
        Optional<Professional> existingProfessional = professionalRepository.findByEmail(professional.getEmail());
        if (existingProfessional.isPresent()) {
            Professional foundProfessional = existingProfessional.get();
            if (foundProfessional.getPassword().equals(professional.getPassword())) {
                return ResponseEntity.ok(foundProfessional); // 200 OK
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("הסיסמה שגויה"); // 401 UNAUTHORIZED
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("המשתמש לא נמצא"); // 404 NOT FOUND
    }

//    // POST - הוספת תאריך פנוי ליומן
//    @PostMapping("/{id}/available-time")
//    public ResponseEntity<?> addAvailableTime(@PathVariable Long id, @RequestBody CalendarEntry calendarEntry) {
//        Optional<Professional> professional = professionalRepository.findById(id);
//        if (!professional.isPresent()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("בעל המקצוע לא נמצא");
//        }
//        calendarEntry.setProfessional(professional.get());
//        calendarEntry.setProfAvailable(true); // מגדירים שהתאריך פנוי
//        calendarService.save(calendarEntry);
//        return ResponseEntity.status(HttpStatus.CREATED).body(calendarEntry); // 201 CREATED
//    }


//PUT============================================================================================================

    // PUT - עדכון בעל מקצוע לפי ID
    @PutMapping("/updateProfessional/{id}")
    public ResponseEntity<?> updateProfessional(@PathVariable Long id, @RequestBody Professional professional) {
        if (!professionalRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("בעל המקצוע לא נמצא");
        }
        professional.setId(id); // מוודאים שה-ID תואם לנתון שמתעדכן
        Professional updatedProfessional = professionalRepository.save(professional);
        return ResponseEntity.ok(updatedProfessional); // 200 OK
    }
//DELETE=========================================================================================================

    // DELETE - מחיקת בעל מקצוע לפי ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProfessional(@PathVariable Long id) {
        if (!professionalRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("בעל המקצוע לא נמצא");
        }
        professionalRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // 204 NO CONTENT
    }
}
