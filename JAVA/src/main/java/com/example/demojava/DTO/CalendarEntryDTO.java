package com.example.demojava.DTO;

import java.sql.Date;
import java.sql.Time;

public class CalendarEntryDTO {
    private Long id; // מזהה של המשבצת
    private Date date; // תאריך של המשבצת
    private Time startTime; // שעת התחלה
    private Time endTime; // שעת סיום

    // קונסטרקטור
    public CalendarEntryDTO(Long id, Date date, Time startTime, Time endTime) {
        this.id = id;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

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
//
//    // toString עבור הדפסה נוחה
//    @Override
//    public String toString() {
//        return "CalendarEntryDTO{" +
//                "id=" + id +
//                ", date=" + date +
//                ", startTime=" + startTime +
//                ", endTime=" + endTime +
//                '}';
//    }
}
