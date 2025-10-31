package com.painthu.dynamictable.Service;

import com.painthu.dynamictable.Model.Subject;
import com.painthu.dynamictable.Repository.SubjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {

    private final SubjectRepository repository;

    public SubjectService(SubjectRepository repository) {
        this.repository = repository;
    }

    public  Subject createSubject(Subject subject){
        return repository.save(subject);
    }

    public List<Subject> getAllSubjects(){
        return repository.findAll();
    }

    public Subject updateStaff(String id, Subject module){
        return repository.findById(id)
                .map(subject -> {
                    subject.setName(module.getName());
                    subject.setProgramCode(module.getProgramCode());
                    subject.setBatchCode(module.getBatchCode());
                    subject.setHasCommonBatches(module.getHasCommonBatches());
                    subject.setCommonBatches(module.getCommonBatches());
                    subject.setHasPractical(module.isHasPractical());
                    subject.setLectureDuration(module.getLectureDuration());
                    subject.setLectureInCharge(module.getLectureInCharge());
                    subject.setLectureDuration(module.getLectureDuration());
                    subject.setTutorialDuration(module.getTutorialDuration());
                    subject.setPracticalDuration(module.getPracticalDuration());

                    return repository.save(subject);
                })
                .orElse(null);
    }

    public void deleteStaff(String id) {
        repository.deleteById(id);
    }
}
