package com.painthu.dynamictable.Service;

import com.painthu.dynamictable.Model.Hall;
import com.painthu.dynamictable.Repository.HallRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HallService {

    private final HallRepository repository;

    public HallService(HallRepository repository) {
        this.repository = repository;
    }

    public Hall createHall(Hall hall) {
        return repository.save(hall);
    }

    public List<Hall> getAllHalls() {
        return repository.findAll();
    }

    public Optional<Hall> getHallById(String id) {
        return repository.findById(id);
    }

    public Hall updateHall(String id, Hall hallDetails){
        return repository.findById(id)
                .map(hall -> {
                    hall.setName(hallDetails.getName());
                    hall.setCapacity(hallDetails.getCapacity());
                    return repository.save(hall);
                })
                .orElse(null);
    }

    public void deleteHall(String id) {
        repository.deleteById(id);
    }

    /*

    @Autowired
    private MongoTemplate mongoTemplate;

    public Hall updateDay(String hallId, String day, List<TimeSlot> newSlots) {
        Hall hall = repository.findById(hallId)
                .orElseThrow(() -> new RuntimeException("Hall not found"));

        switch (day.toLowerCase()) {
            case "monday" -> {
                if (hall.getMonday() == null) hall.setMonday(new ArrayList<>());
                hall.getMonday().addAll(newSlots);
            }
            case "tuesday" -> {
                if (hall.getTuesday() == null) hall.setTuesday(new ArrayList<>());
                hall.getTuesday().addAll(newSlots);
            }
            case "wednesday" -> {
                if (hall.getWednesday() == null) hall.setWednesday(new ArrayList<>());
                hall.getWednesday().addAll(newSlots);
            }
            case "thursday" -> {
                if (hall.getThursday() == null) hall.setThursday(new ArrayList<>());
                hall.getThursday().addAll(newSlots);
            }
            case "friday" -> {
                if (hall.getFriday() == null) hall.setFriday(new ArrayList<>());
                hall.getFriday().addAll(newSlots);
            }
            case "saturday" -> {
                if (hall.getSaturday() == null) hall.setSaturday(new ArrayList<>());
                hall.getSaturday().addAll(newSlots);
            }
            case "sunday" -> {
                if (hall.getSunday() == null) hall.setSunday(new ArrayList<>());
                hall.getSunday().addAll(newSlots);
            }
            default -> throw new IllegalArgumentException("Invalid day: " + day);
        }

        return repository.save(hall);
    }

     */

}




