package com.example.demojava.service;

import com.example.demojava.models.CalendarEntry;
import com.example.demojava.models.Professional;
import com.example.demojava.repository.CalendarEntryRepository;
import com.example.demojava.repository.ProfessionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class CalendarService {

    @Autowired
    private CalendarEntryRepository calendarEntryRepository;

    @Autowired
    private ProfessionalRepository professionalRepository;

    // שמירת משבצת זמן
    public CalendarEntry save(CalendarEntry calendarEntry) {
        return calendarEntryRepository.save(calendarEntry);
    }

    // הוספת משבצת זמן פנויה לבעל מקצוע
    public CalendarEntry addTimeSlot(Long professionalId, CalendarEntry calendarEntry) {
        Optional<Professional> professionalOptional = professionalRepository.findById(professionalId);
        if (professionalOptional.isEmpty()) {
            throw new IllegalArgumentException("Professional not found with ID: " + professionalId);
        }
        Professional professional = professionalOptional.get();
        calendarEntry.setProfessional(professional);
        return calendarEntryRepository.save(calendarEntry);
    }

    // משיכת כל המשבצות של בעל מקצוע
    public List<CalendarEntry> getCalendarEntriesByProfessional(Long professionalId) {
        return calendarEntryRepository.findByProfessionalId(professionalId);
    }

    // משיכת משבצות זמן פנויות בלבד (אם המשתמש הוא null)
    public List <CalendarEntry> getAvailableSlots(Long professionalId) {
        return calendarEntryRepository.findByProfessionalId(professionalId).stream()
                .filter(entry -> entry.getUsers() == null) // אם המשתמש לא קיים, אז פנוי
                .collect(Collectors.toList());
    }

    // עדכון זמינות של משבצת זמן
    public CalendarEntry updateAvailability(Long calendarEntryId, boolean isAvailable) {
        CalendarEntry calendarEntry = calendarEntryRepository.findById(calendarEntryId)
                .orElseThrow(() -> new IllegalArgumentException("Calendar entry not found with ID: " + calendarEntryId));

        if (isAvailable) {
            calendarEntry.setUsers(null); // אם פנוי, אז אין משתמש
        } else {
            // במקרה של תפוס, כאן נוסיף את המשתמש (אם הוא נמסר דרך בקשה)
            // לדוגמה:
            // calendarEntry.setUsers(someUser);
        }

        return calendarEntryRepository.save(calendarEntry);
    }

    // מחיקת משבצת זמן
    public void deleteTimeSlot(Long calendarEntryId) {
        if (!calendarEntryRepository.existsById(calendarEntryId)) {
            throw new IllegalArgumentException("Calendar entry not found with ID: " + calendarEntryId);
        }
        calendarEntryRepository.deleteById(calendarEntryId);
    }
}
