package com.painthu.dynamictable.Repository;

import com.painthu.dynamictable.Model.Hall;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface HallRepository extends MongoRepository<Hall, String> {

    /**
     * Finds halls based on whether they are laboratories or not.
     * This is used by the automation engine to separate
     * Theory (isLab = false) and Practical (isLab = true) sessions.
     */
    List<Hall> findByIsLab(boolean isLab);
}