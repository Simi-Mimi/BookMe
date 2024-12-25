package com.example.demojava.DTO;

import java.sql.Time;

public class TimeSlotDTO {
    private Time startTime; // שעת התחלה
    private Time endTime; // שעת סיום

    // קונסטרקטור
    public TimeSlotDTO(Time startTime, Time endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Getters and Setters
    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

}
