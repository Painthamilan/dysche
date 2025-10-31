package com.painthu.dynamictable.Repository;

import com.painthu.dynamictable.Model.Hall;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HallRepository extends MongoRepository<Hall,String> {

}
