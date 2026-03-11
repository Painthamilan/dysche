package com.painthu.dynamictable.Service;

import com.painthu.dynamictable.Model.Subject;
import com.painthu.dynamictable.Repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectRepository subjectRepository;

    // Save a new subject (e.g., dsv with 2 slots)
    public Subject addSubject(Subject subject) {
        return subjectRepository.save(subject);
    }

    // This is the "Engine Room" method for your automation
    // It finds all subjects that need to be scheduled for B02
    public List<Subject> getSubjectsByBatch(String batchId) {
        return subjectRepository.findByBatchCode(batchId);
    }

    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    public void deleteSubject(String id) {
        subjectRepository.deleteById(id);
    }
}