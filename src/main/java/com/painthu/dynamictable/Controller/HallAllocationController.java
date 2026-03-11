package com.painthu.dynamictable.Controller;

import com.painthu.dynamictable.Model.HallAllocation;
import com.painthu.dynamictable.Model.TimeSlot;
import com.painthu.dynamictable.Service.HallAllocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hall-allocations")
@RequiredArgsConstructor
public class HallAllocationController {


    private final HallAllocationService hallAllocationService;

    @PostMapping
    public ResponseEntity<?> createOrUpdateHallAllocation(
            @RequestParam String batchId,
            @RequestBody HallAllocation allocation
    ) {
        try {
            HallAllocation saved = hallAllocationService.saveHallAllocation(allocation);
            return ResponseEntity.ok(saved);
        } catch (IllegalStateException e) {
            if ("SLOT_IS_FULL".equals(e.getMessage())) {
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body("SLOT_IS_FULL");
            }
            throw e; // propagate other errors
        }
    }

}

