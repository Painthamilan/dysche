package com.painthu.dynamictable.Service;

import com.painthu.dynamictable.Model.Batch;
import com.painthu.dynamictable.Repository.BatchRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BatchService {

    private final BatchRepository repository;

    public BatchService(BatchRepository repository) {
        this.repository = repository;
    }

    public Batch createBatch(Batch batch) {
        return repository.save(batch);
    }

    public List<Batch> getAllBatches(){
        return repository.findAll();
    }


    public Batch updateBatch(String id, Batch batchDetails){
        return repository.findById(id)
                .map(batch -> {
                    batch.setName(batchDetails.getName());
                    batch.setProgramCode(batch.getProgramCode());
                    batch.setType(batch.getType());
                    batch.setStudentCount(batch.getStudentCount());
                    return repository.save(batch);
                })
                .orElse(null);
    }

    public void deleteBatch(String id) {
        repository.deleteById(id);
    }

}
