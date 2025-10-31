package com.painthu.dynamictable.Controller;



import com.painthu.dynamictable.Model.StaffTimeSlot;
import com.painthu.dynamictable.Model.StaffTimetable;
import com.painthu.dynamictable.Service.StaffTimetableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/staff-timetables")
public class StaffTimetableController {

    @Autowired
    private StaffTimetableService service;

    @PostMapping
    public ResponseEntity<StaffTimetable> create(@RequestBody StaffTimetable timetable) {
        return ResponseEntity.ok(service.create(timetable));
    }

    @GetMapping
    public ResponseEntity<List<StaffTimetable>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StaffTimetable> getById(@PathVariable String id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<StaffTimetable> update(@PathVariable String id, @RequestBody StaffTimetable timetable) {
        return ResponseEntity.ok(service.update(id, timetable));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{staffId}/update-day")
    public ResponseEntity<?> updateDay(
            @PathVariable String staffId,
            @RequestParam String day,
            @RequestBody List<StaffTimeSlot> newSlots) {

        StaffTimetable updated = service.updateDay(staffId, day.toLowerCase(), newSlots);
        return ResponseEntity.ok(updated);
    }

}