package com.painthu.dynamictable.Repository;

import com.painthu.dynamictable.Model.Staff;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StaffRepository extends MongoRepository<Staff,String> {

}
