package com.painthu.dynamictable.Repository;

import com.painthu.dynamictable.Model.Timetable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimetableRepository extends MongoRepository<Timetable, String> {
}
