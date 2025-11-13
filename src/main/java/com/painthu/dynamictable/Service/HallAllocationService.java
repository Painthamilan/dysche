package com.painthu.dynamictable.Service;

import com.painthu.dynamictable.Model.HallAllocation;
import com.painthu.dynamictable.Model.TimeSlot;
import com.painthu.dynamictable.Repository.BatchRepository;
import com.painthu.dynamictable.Repository.HallAllocationRepository;
import com.painthu.dynamictable.Repository.HallRepository;
import com.painthu.dynamictable.Repository.StaffRepository;
import com.painthu.dynamictable.Utils.HallUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.painthu.dynamictable.Utils.HallUtil.setDaySlots;

@Service
@RequiredArgsConstructor
public class HallAllocationService {

    private final HallAllocationRepository hallAllocationRepository;
    private final HallRepository hallRepository;

    private final BatchRepository batchRepository;
    private final StaffRepository staffRepository;
   // private final HallAllocationController hallAllocationController;


    public HallAllocation updateTable(HallAllocation hallAllocation, String batchId) {

        if (isValidated())
            return saveHallAllocation(hallAllocation, batchId);
        else
            return null;
    }


    private boolean isValidated() {

        //if (HallUtil.isSlotExist(hall))

        return true;

    }

    public HallAllocation saveHallAllocation(HallAllocation allocation, String batchId) {

        // Load existing hall allocation or create a new one
        HallAllocation hall = hallAllocationRepository.findById(allocation.getHallId())
                .orElseGet(() -> {
                    HallAllocation newHall = new HallAllocation();
                    newHall.setHallId(allocation.getHallId());
                    return newHall;
                });

        // Add slots day by day
        addSlots(hall, "monday", allocation.getMonday());
        addSlots(hall, "tuesday", allocation.getTuesday());
        addSlots(hall, "wednesday", allocation.getWednesday());
        addSlots(hall, "thursday", allocation.getThursday());
        addSlots(hall, "friday", allocation.getFriday());
        addSlots(hall, "saturday", allocation.getSaturday());
        addSlots(hall, "sunday", allocation.getSunday());

        // Save updated hall allocation
        return hallAllocationRepository.save(hall);
    }

    private void addSlots(HallAllocation hall, String day, List<TimeSlot> newSlots) {
        if (newSlots == null || newSlots.isEmpty()) return;

        List<TimeSlot> existing = HallUtil.getDaySlots(hall, day);
        if (existing == null) existing = new ArrayList<>();

        for (TimeSlot slot : newSlots) {
            // Find first available slots instead of just incrementing
            int count = slot.getSlotId().length;
            int[] available = HallUtil.findAvailableSlots(hall, day, count);
            slot.setSlotId(available);

            existing.add(slot);
        }

        setDaySlots(hall, day, existing);
    }



    /*
    public HallAllocation saveHallAllocation(HallAllocation allocation, String batcId) {
        return hallAllocationRepository.findById(allocation.getHallId())
                .map(existing -> {
                    // Monday
                    if (allocation.getMonday() != null && !allocation.getMonday().isEmpty()) {
                        if (existing.getMonday() == null) existing.setMonday(new ArrayList<>());
                        existing.getMonday().addAll(allocation.getMonday());
                    }

                    // Tuesday
                    if (allocation.getTuesday() != null && !allocation.getTuesday().isEmpty()) {
                        if (existing.getTuesday() == null) existing.setTuesday(new ArrayList<>());
                        existing.getTuesday().addAll(allocation.getTuesday());
                    }

                    // Same for other days
                    if (allocation.getWednesday() != null && !allocation.getWednesday().isEmpty()) {
                        if (existing.getWednesday() == null) existing.setWednesday(new ArrayList<>());
                        existing.getWednesday().addAll(allocation.getWednesday());
                    }
                    if (allocation.getThursday() != null && !allocation.getThursday().isEmpty()) {
                        if (existing.getThursday() == null) existing.setThursday(new ArrayList<>());
                        existing.getThursday().addAll(allocation.getThursday());
                    }
                    if (allocation.getFriday() != null && !allocation.getFriday().isEmpty()) {
                        if (existing.getFriday() == null) existing.setFriday(new ArrayList<>());
                        existing.getFriday().addAll(allocation.getFriday());
                    }
                    if (allocation.getSaturday() != null && !allocation.getSaturday().isEmpty()) {
                        if (existing.getSaturday() == null) existing.setSaturday(new ArrayList<>());
                        existing.getSaturday().addAll(allocation.getSaturday());
                    }
                    if (allocation.getSunday() != null && !allocation.getSunday().isEmpty()) {
                        if (existing.getSunday() == null) existing.setSunday(new ArrayList<>());
                        existing.getSunday().addAll(allocation.getSunday());
                    }

                    return hallAllocationRepository.save(existing);
                })
                .orElseGet(() -> hallAllocationRepository.save(allocation));
    }


     */


    /*
    public HallAllocation addSlot(String hallId, String day, TimeSlot slot) {
        HallAllocation allocation = hallAllocationRepository.findById(hallId)
                .orElseThrow(() -> new RuntimeException("Hall not found"));

        List<TimeSlot> daySlots = switch (day.toLowerCase()) {
            case "monday" -> allocation.getMonday();
            case "tuesday" -> allocation.getTuesday();
            case "wednesday" -> allocation.getWednesday();
            case "thursday" -> allocation.getThursday();
            case "friday" -> allocation.getFriday();
            case "saturday" -> allocation.getSaturday();
            case "sunday" -> allocation.getSunday();
            default -> throw new RuntimeException("Invalid day: " + day);
        };

        if (daySlots == null) daySlots = new ArrayList<>();
        daySlots.add(slot);

        switch (day.toLowerCase()) {
            case "monday" -> allocation.setMonday(daySlots);
            case "tuesday" -> allocation.setTuesday(daySlots);
            case "wednesday" -> allocation.setWednesday(daySlots);
            case "thursday" -> allocation.setThursday(daySlots);
            case "friday" -> allocation.setFriday(daySlots);
            case "saturday" -> allocation.setSaturday(daySlots);
            case "sunday" -> allocation.setSunday(daySlots);
        }

        return hallAllocationRepository.save(allocation);
    }


 */

}

