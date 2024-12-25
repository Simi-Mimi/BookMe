package com.example.demojava.service;

import com.example.demojava.DTO.CalendarDayDTO;
import com.example.demojava.DTO.TimeSlotDTO;

import com.example.demojava.models.CalendarEntry;
import com.example.demojava.models.Professional;
import com.example.demojava.models.Users;
import com.example.demojava.repository.CalendarEntryRepository;
import com.example.demojava.repository.ProfessionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demojava.repository.UsersRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class CalendarService {

    @Autowired
    private CalendarEntryRepository calendarEntryRepository;

    @Autowired
    private ProfessionalRepository professionalRepository;

    @Autowired
    private UsersRepository usersRepository;  // הוספת הזרקת UsersRepository

    // פונקציה להמיר משבצת זמן ל-DTO
    private TimeSlotDTO convertToTimeSlotDTO(CalendarEntry entry) {
        return new TimeSlotDTO(
                entry.getStartTime(),
                entry.getEndTime()
        );
    }

    // פונקציה להמיר את כל המשבצות ל-CalendarDayDTO
    private CalendarDayDTO convertToCalendarDayDTO(CalendarEntry entry) {
        TimeSlotDTO timeSlotDTO = convertToTimeSlotDTO(entry);
        return new CalendarDayDTO(
                entry.getDate(), // תאריך נשמר כ-Date
                List.of(timeSlotDTO) // הוספת משבצת זמן לרשימה
        );
    }

//GET
    // משיכת כל המשבצות של בעל מקצוע
    public List<CalendarDayDTO> getCalendarEntriesByProfessional(Long professionalId) {
        List<CalendarEntry> entries = calendarEntryRepository.findByProfessionalId(professionalId);
        return entries.stream()
                .map(this::convertToCalendarDayDTO) // המרה ל-DTO
                .collect(Collectors.toList());
    }
    // משיכת משבצות זמן פנויות בלבד (אם המשתמש הוא null)
    public List<CalendarDayDTO> getAvailableSlots(Long professionalId) {
        List<CalendarEntry> entries = calendarEntryRepository.findByProfessionalId(professionalId).stream()
                .filter(entry -> entry.getUsers() == null) // אם המשתמש לא קיים, אז פנוי
                .collect(Collectors.toList());

        // המרה ל-DTO
        return entries.stream()
                .map(this::convertToCalendarDayDTO)
                .collect(Collectors.toList());
    }

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

    // עדכון זמינות של משבצת זמן
    public CalendarEntry updateAvailability(Long calendarEntryId, Long userId) {
        // חיפוש משבצת הזמן לפי מזהה
        CalendarEntry calendarEntry = calendarEntryRepository.findById(calendarEntryId)
                .orElseThrow(() -> new IllegalArgumentException("Calendar entry not found with ID: " + calendarEntryId));

        // אם המשבצת פנויה (אין משתמש), נוסיף את המשתמש
        if (calendarEntry.getUsers() == null) {
            // חיפוש המשתמש לפי מזהה
            Users user = usersRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
            calendarEntry.setUsers(user);  // הגדרת המשתמש בתור המשתמש שיתפוס את המשבצת
        } else {
            // אם המשבצת תפוסה, נהפוך אותה לפנויה
            calendarEntry.setUsers(null); // הפיכת המשבצת לפנויה
        }

        // שמירת המשבצת לאחר עדכון
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
