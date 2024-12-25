package com.example.demojava.DTO;

import java.util.Date;
import java.util.List;

public class CalendarDayDTO {
    private Date date; // תאריך
    private List<TimeSlotDTO> slots; // רשימה של משבצות זמן

    // קונסטרקטור
    public CalendarDayDTO(Date date, List<TimeSlotDTO> slots) {
        this.date = date;
        this.slots = slots;
    }

    // Getters and setters
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<TimeSlotDTO> getSlots() {
        return slots;
    }

    public void setSlots(List<TimeSlotDTO> slots) {
        this.slots = slots;
    }
}
