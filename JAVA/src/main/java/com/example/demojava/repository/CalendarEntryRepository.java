package com.example.demojava.repository;

import com.example.demojava.models. CalendarEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Date;

public interface CalendarEntryRepository extends JpaRepository< CalendarEntry, Long>{
    List<CalendarEntry> findByProfessionalId(Long professionalId);
    List<CalendarEntry> findByProfessionalIdAndDate(Long professionalId, Date date);
}
