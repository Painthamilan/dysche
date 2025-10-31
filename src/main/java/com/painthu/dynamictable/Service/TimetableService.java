package com.painthu.dynamictable.Service;

import com.painthu.dynamictable.Model.Timetable;
import com.painthu.dynamictable.Repository.TimetableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimetableService {

    @Autowired
    private TimetableRepository timetableRepository;

    public List<Timetable> getAll() {
        return timetableRepository.findAll();
    }

    public Timetable getById(String id) {
        return timetableRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Timetable not found"));
    }

    public Timetable create(Timetable timetable) {
        return timetableRepository.save(timetable);
    }

    public Timetable update(String id, Timetable timetable) {
        Timetable existing = getById(id);
        timetable.setId(existing.getId());
        return timetableRepository.save(timetable);
    }

    public void delete(String id) {
        timetableRepository.deleteById(id);
    }
}

