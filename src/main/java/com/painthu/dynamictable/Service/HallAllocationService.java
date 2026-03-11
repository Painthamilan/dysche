package com.painthu.dynamictable.Service;

import com.painthu.dynamictable.Model.Hall;
import com.painthu.dynamictable.Model.HallAllocation;
import com.painthu.dynamictable.Model.Subject;
import com.painthu.dynamictable.Model.TimeSlot;
import com.painthu.dynamictable.Repository.HallAllocationRepository;
import com.painthu.dynamictable.Repository.HallRepository;
import com.painthu.dynamictable.Repository.SubjectRepository;
import com.painthu.dynamictable.Utils.HallUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HallAllocationService {

    private final HallAllocationRepository hallAllocationRepository;
    private final SubjectRepository subjectRepository;
    private final HallRepository hallRepository;

    /**
     * Automates the timetable generation for a specific batch.
     */
    public void automateTable(String batchId) {
        List<Subject> subjects = subjectRepository.findByBatchCode(batchId);

        for (Subject sub : subjects) {
            // 1. Theory Allocation (Only in non-lab halls)
            allocateToCorrectHall(sub, sub.getLectureInCharge(), 3, " (Theory)", false, batchId);

            // 2. Practical Allocation (Only in lab halls)
            if (sub.isHasPractical()) {
                allocateToCorrectHall(sub, sub.getPracticalInCharge(), 2, " (Practical)", true, batchId);
            }

            sub.setAlocated(true);
            subjectRepository.save(sub);
        }
    }

    private void allocateToCorrectHall(Subject sub, List<String> staff,
                                       int duration, String suffix, boolean needsLab, String batchId) {

        String[] weekDays = {"monday", "tuesday", "wednesday", "thursday", "friday"};

        // Find IDs of halls that match the Lab/Theory requirement
        List<String> validHallIds = hallRepository.findByIsLab(needsLab)
                .stream()
                .map(Hall::getId)
                .collect(Collectors.toList());

        boolean placed = false;

        // Loop through valid hall IDs instead of just existing allocations
        for (String hallId : validHallIds) {
            if (placed) break;

            // Fetch existing allocation OR initialize a new one if it doesn't exist in DB
            HallAllocation alloc = hallAllocationRepository.findById(hallId)
                    .orElseGet(() -> initializeNewHallAllocation(hallId));

            for (String day : weekDays) {
                if (placed) break;

                // Check constraints across ALL halls for this specific day
                if (countBatchClassesAcrossHalls(day, batchId) >= 3) continue;

                // Attempt to find a free slot block
                int[] foundSlots = findSlots(alloc, day, duration, 8);

                if (foundSlots != null) {
                    TimeSlot newSlot = new TimeSlot();
                    newSlot.setSubject(sub.getName() + suffix);
                    newSlot.setNumOfSlots(duration);
                    newSlot.setStaffs(staff);
                    newSlot.setBatches(List.of(batchId));
                    newSlot.setSlotId(foundSlots);

                    // Add the slot and save to DB
                    HallUtil.getDaySlots(alloc, day).add(newSlot);
                    hallAllocationRepository.save(alloc);
                    placed = true;
                }
            }
        }
    }

    private HallAllocation initializeNewHallAllocation(String hallId) {
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

    private int countBatchClassesAcrossHalls(String day, String batchId) {
        // Fetch fresh data from DB to ensure accurate counting
        List<HallAllocation> allAllocations = hallAllocationRepository.findAll();
        return (int) allAllocations.stream()
                .flatMap(h -> HallUtil.getDaySlots(h, day).stream())
                .filter(s -> s.getBatches().contains(batchId))
                .count();
    }

    private int[] findSlots(HallAllocation hall, String day, int duration, int limit) {
        List<TimeSlot> existingSlots = HallUtil.getDaySlots(hall, day);

        for (int start = 2; start <= limit; start++) {
            int end = start + duration - 1;

            if (end > limit) break;

            if (HallUtil.isRangeFree(existingSlots, start, duration)) {
                int[] result = new int[duration];
                for (int i = 0; i < duration; i++) {
                    result[i] = start + i;
                }
                return result;
            }
        }
        return null;
    }
}