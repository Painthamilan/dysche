package com.painthu.dynamictable.Controller;

import com.painthu.dynamictable.Model.Subject;
import com.painthu.dynamictable.Service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subjects")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService subjectService; // Injected Service

    @PostMapping
    public ResponseEntity<Subject> addSubject(@RequestBody Subject subject) {
        // Uses service to handle the save
        return ResponseEntity.ok(subjectService.addSubject(subject));
    }

    @GetMapping("/batch/{batchId}")
    public ResponseEntity<List<Subject>> getSubjectsByBatch(@PathVariable String batchId) {
        return ResponseEntity.ok(subjectService.getSubjectsByBatch(batchId));
    }
}