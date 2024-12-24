package com.example.demojava.controller;
import com.example.demojava.service.CalendarService;
import com.example.demojava.models.CalendarEntry;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/calendarEntry")
@CrossOrigin(origins = "http://localhost:5173")
public class CalendarEntryController {

    @Autowired
    private CalendarService calendarService;

    //GET - החזרת כל המשבצות של בעל מקצוע
    @GetMapping("/getCalendarEntry/{professionalId}")
    public List<CalendarEntry> getCalendarEntries(@PathVariable Long professionalId) {
        return calendarService.getCalendarEntriesByProfessional(professionalId);
    }

    //POST - הוספת פגישה
    @PostMapping("/addEntry/{professionalId}")
    public CalendarEntry addTimeSlot(@PathVariable Long professionalId, @RequestBody CalendarEntry calendarEntry) {
        return calendarService.addTimeSlot(professionalId, calendarEntry);
    }

    //PUT - עדכון זמינות
    @PutMapping("/updateEntry/{calendarEntryId}")
    public CalendarEntry updateAvailability(@PathVariable Long calendarEntryId, @RequestParam boolean isAvailable) {
        return calendarService.updateAvailability(calendarEntryId, isAvailable);
    }

    //DELETE - מחיקת משבצת זמן
    @DeleteMapping("/deleteEntry/{calendarEntryId}")
    public String deleteTimeSlot(@PathVariable Long calendarEntryId) {
        calendarService.deleteTimeSlot(calendarEntryId);
        return "Time slot with ID " + calendarEntryId + " has been deleted successfully.";
    }
}
