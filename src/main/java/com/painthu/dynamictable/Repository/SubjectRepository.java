package com.painthu.dynamictable.Repository;

import com.painthu.dynamictable.Model.Subject;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface SubjectRepository extends MongoRepository<Subject, String> {

    // This allows the automation engine to find all modules for "B02"
    List<Subject> findByBatchCode(String batchCode);
}