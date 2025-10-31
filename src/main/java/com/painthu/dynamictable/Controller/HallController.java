package com.painthu.dynamictable.Controller;


import com.painthu.dynamictable.Model.Hall;
import com.painthu.dynamictable.Service.HallService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/halls")
public class HallController {


    private final HallService service;

    public HallController(HallService service) {
        this.service = service;
    }

    @PostMapping
    public Hall createHall(@RequestBody Hall hall) {
        return service.createHall(hall);
    }

    @GetMapping
    public List<Hall> getAllHalls() {
        return service.getAllHalls();
    }

    @GetMapping("/{id}")
    public Hall getHallById(@PathVariable String id) {
        return service.getHallById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public Hall updateHall(@PathVariable String id, @RequestBody Hall hallDetails) {
        return service.updateHall(id, hallDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteHall(@PathVariable String id) {
        service.deleteHall(id);
    }

    /*
    @PatchMapping("/{hallId}/update-day")
    public ResponseEntity<?> updateDay(
            @PathVariable String hallId,
            @RequestParam String day,
            @RequestBody List<TimeSlot> slots) {

        Hall updated = service.updateDay(hallId, day, slots);
        return ResponseEntity.ok(updated);
    }

     */

}
