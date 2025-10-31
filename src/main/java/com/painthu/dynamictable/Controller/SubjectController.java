package com.painthu.dynamictable.Controller;

import com.painthu.dynamictable.Model.Subject;
import com.painthu.dynamictable.Service.SubjectService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subjects")
public class SubjectController {

    private final SubjectService service;


    public SubjectController(SubjectService service) {
        this.service = service;
    }

    @PostMapping
    public Subject createSubject(@RequestBody Subject subject){
        return service.createSubject(subject);
    }

    @GetMapping
    public List<Subject> getAllSubjects(){
        return  service.getAllSubjects();
    }
}
