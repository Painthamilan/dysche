package com.painthu.dynamictable.Repository;

import com.painthu.dynamictable.Model.HallAllocation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface HallAllocationRepository extends MongoRepository<HallAllocation, String> {
    Optional<HallAllocation> findByHallId(String hallId);
}