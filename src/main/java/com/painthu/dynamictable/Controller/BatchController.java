package com.painthu.dynamictable.Controller;


import com.painthu.dynamictable.Model.Batch;
import com.painthu.dynamictable.Service.BatchService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/batches")
public class BatchController {
    private final BatchService service;

    public BatchController(BatchService service) {
        this.service = service;
    }


    @PostMapping
    public Batch createBatch(@RequestBody Batch batch) {
        return service.createBatch(batch);
    }

    @GetMapping
    public List<Batch> getAllBatches(){
        return service.getAllBatches();
    }
}
