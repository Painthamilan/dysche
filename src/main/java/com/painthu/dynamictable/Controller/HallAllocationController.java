package com.painthu.dynamictable.Controller;

import com.painthu.dynamictable.Service.HallAllocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hall-allocations")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Allows your frontend to call the API
public class HallAllocationController {

    private final HallAllocationService hallAllocationService;

    /**
     * Triggered by: GET http://localhost:8080/api/hall-allocations/automate?batchId=B02
     * * This starts the "Brain" of your system:
     * 1. Fetches modules from the 'modules' collection.
     * 2. Separates Theory (3 slots) and Practical (2 slots).
     * 3. Matches Practical to Halls where isLab = true.
     * 4. Enforces the Slot 8 boundary and 3-class-per-day batch limit.
     */
    @GetMapping("/automate")
    public ResponseEntity<String> automateBatch(@RequestParam String batchId) {
        try {
            hallAllocationService.automateTable(batchId);
            return ResponseEntity.ok("Automation successfully completed for Batch: " + batchId);
        } catch (IllegalStateException e) {
            // Handle cases where no slots are available
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred during automation: " + e.getMessage());
        }
    }
}