package com.example.demojava.models;

import jakarta.persistence.*;

import java.sql.Time;
import java.util.Date;

@Entity
public class CalendarEntry {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "professional_id")
    private Professional professional;

    @OneToOne
    private Users users; // לא יהיה null אם המשבצת תפוסה

    @Temporal(TemporalType.DATE)
    @Column(name = "calenders_date")
    private Date date;  // תאריך

//    private Time timeSlot; // משבצת זמן
//
    @Column(name = "start_time", nullable = false)
    private Time startTime; // שעת התחלה

    @Column(name = "end_time", nullable = false)
    private Time endTime; // שעת סיום

    public CalendarEntry() {
    }

    public CalendarEntry(long id, Professional professional, Users users, Date date, Time startTime, Time endTime) {
        this.id = id;
        this.professional = professional;
        this.users = users;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Professional getProfessional() {
        return professional;
    }

    public void setProfessional(Professional professional) {
        this.professional = professional;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
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
}