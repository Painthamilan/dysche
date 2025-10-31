package com.painthu.dynamictable.Controller;

import com.painthu.dynamictable.Model.HallAllocation;
import com.painthu.dynamictable.Model.TimeSlot;
import com.painthu.dynamictable.Service.HallAllocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hall-allocations")
@RequiredArgsConstructor
public class HallAllocationController {


    private final HallAllocationService hallAllocationService;

    @PostMapping
    public ResponseEntity<HallAllocation> createOrUpdateHallAllocation(
            @RequestParam String batchId,           // ✅ from URL param
            @RequestBody HallAllocation allocation  // ✅ full hall allocation JSON
    ) {
        HallAllocation saved = hallAllocationService.updateTable(allocation, batchId);
        return ResponseEntity.ok(saved);
    }
}

