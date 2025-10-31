package com.painthu.dynamictable.Repository;


import com.painthu.dynamictable.Model.StaffTimetable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StaffTimetableRepository extends MongoRepository<StaffTimetable, String> {
}