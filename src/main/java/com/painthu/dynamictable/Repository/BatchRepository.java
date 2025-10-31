package com.painthu.dynamictable.Repository;

import com.painthu.dynamictable.Model.Batch;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BatchRepository  extends MongoRepository<Batch, String> {

}
