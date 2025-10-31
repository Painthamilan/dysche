package com.painthu.dynamictable.Repository;

import com.painthu.dynamictable.Model.Subject;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SubjectRepository extends MongoRepository<Subject,String> {
}
