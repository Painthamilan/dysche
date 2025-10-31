package com.painthu.dynamictable.Controller;

import com.painthu.dynamictable.Model.Timetable;
import com.painthu.dynamictable.Service.TimetableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/timetables")
public class TimetableController {

    @Autowired
    private TimetableService timetableService;

    @GetMapping
    public List<Timetable> getAll() {
        return timetableService.getAll();
    }

    @GetMapping("/{id}")
    public Timetable getById(@PathVariable String id) {
        return timetableService.getById(id);
    }

    @PostMapping
    public Timetable create(@RequestBody Timetable timetable) {
        return timetableService.create(timetable);
    }

    @PutMapping("/{id}")
    public Timetable update(@PathVariable String id, @RequestBody Timetable timetable) {
        return timetableService.update(id, timetable);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        timetableService.delete(id);
    }

    /*
    @PatchMapping("/{id}/monday")
    public ResponseEntity<?> updateMonday(
            @PathVariable String id,
            @RequestBody List<TimeSlot> mondaySlots) {

        Timetable updated = timetableService.updateMonday(id, mondaySlots);
        return ResponseEntity.ok(updated);
    }

     */


}
