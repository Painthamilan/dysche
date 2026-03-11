package com.painthu.dynamictable.Service;

import com.painthu.dynamictable.Model.HallAllocation;
import com.painthu.dynamictable.Model.TimeSlot;
import com.painthu.dynamictable.Repository.BatchRepository;
import com.painthu.dynamictable.Repository.HallAllocationRepository;
import com.painthu.dynamictable.Utils.HallUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HallAllocationService {

    private final HallAllocationRepository hallAllocationRepository;
    private final BatchRepository batchRepository;

    public HallAllocation updateTable(HallAllocation hallAllocation, String batchId) {
        if (!isValidated(hallAllocation, batchId)) {
            throw new IllegalStateException("VALIDATION_FAILED");
        }
        return saveHallAllocation(hallAllocation);
    }

    private boolean isValidated(HallAllocation hallAllocation, String batchId) {
        return true;
    }

    public HallAllocation saveHallAllocation(HallAllocation allocation) {

        HallAllocation hall = hallAllocationRepository
                .findById(allocation.getHallId())
                .orElseGet(() -> initializeHall(allocation.getHallId()));

        addSlots(hall, "monday", allocation.getMonday());
        addSlots(hall, "tuesday", allocation.getTuesday());
        addSlots(hall, "wednesday", allocation.getWednesday());
        addSlots(hall, "thursday", allocation.getThursday());
        addSlots(hall, "friday", allocation.getFriday());
        addSlots(hall, "saturday", allocation.getSaturday());
        addSlots(hall, "sunday", allocation.getSunday());

        return hallAllocationRepository.save(hall);
    }

    private HallAllocation initializeHall(String hallId) {
        HallAllocation hall = new HallAllocation();
        hall.setHallId(hallId);

        hall.setMonday(new ArrayList<>());
        hall.setTuesday(new ArrayList<>());
        hall.setWednesday(new ArrayList<>());
        hall.setThursday(new ArrayList<>());
        hall.setFriday(new ArrayList<>());
        hall.setSaturday(new ArrayList<>());
        hall.setSunday(new ArrayList<>());

        return hall;
    }

    private void addSlots(HallAllocation hall, String day, List<TimeSlot> newSlots) {
        if (newSlots == null || newSlots.isEmpty()) return;

        List<TimeSlot> existing = HallUtil.getDaySlots(hall, day);
        if (existing == null) {
            existing = new ArrayList<>();
            HallUtil.setDaySlots(hall, day, existing);
        }

        boolean isWeekendDay = HallUtil.isWeekendDay(day);

        for (TimeSlot slot : newSlots) {

            int numOfSlots = slot.getNumOfSlots();

                boolean isWeekendBatch = isWeekendBatch(slot.getBatches());

            int startFrom = isWeekendDay ? 1 : 2;

            int[] allocated = HallUtil.findAvailableSlots(
                    hall,
                    day,
                    numOfSlots,
                    startFrom,
                    isWeekendDay
            );

            slot.setSlotId(allocated);
            existing.add(slot);
        }
    }

    private boolean isWeekendBatch(List<String> batches) {
        if (batches == null) return false;

        return batches.stream()
                .anyMatch(batchId ->
                        batchRepository.findById(batchId)
                                .map(b -> "WE".equalsIgnoreCase(b.getType()))
                                .orElse(false)
                );
    }
}
