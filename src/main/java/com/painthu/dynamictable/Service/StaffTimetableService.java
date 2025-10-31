package com.painthu.dynamictable.Service;



import com.painthu.dynamictable.Model.StaffTimeSlot;
import com.painthu.dynamictable.Model.StaffTimetable;
import com.painthu.dynamictable.Repository.StaffTimetableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class StaffTimetableService {

    @Autowired
    private StaffTimetableRepository repository;

    public StaffTimetable create(StaffTimetable timetable) {
        return repository.save(timetable);
    }

    public List<StaffTimetable> getAll() {
        return repository.findAll();
    }

    public Optional<StaffTimetable> getById(String id) {
        return repository.findById(id);
    }

    public StaffTimetable update(String id, StaffTimetable updated) {
        StaffTimetable existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Staff timetable not found"));
        existing.setStaffName(updated.getStaffName());
        existing.setSchedule(updated.getSchedule());
        return repository.save(existing);
    }

    public void delete(String id) {
        repository.deleteById(id);
    }

    public StaffTimetable updateDay(String staffId, String day, List<StaffTimeSlot> newSlots) {
        StaffTimetable existing = repository.findById(staffId)
                .orElseThrow(() -> new RuntimeException("Staff timetable not found"));

        if (existing.getSchedule() == null) {
            existing.setSchedule(new HashMap<>());
        }

        List<StaffTimeSlot> daySlots = existing.getSchedule()
                .getOrDefault(day, new ArrayList<>());

        // ✅ Append new slots (you can also check for duplicates)
        daySlots.addAll(newSlots);
        existing.getSchedule().put(day, daySlots);

        return repository.save(existing);
    }

}
