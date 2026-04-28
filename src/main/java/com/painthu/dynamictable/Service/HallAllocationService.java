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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class HallAllocationService {

    private final HallAllocationRepository hallAllocationRepository;
    private final SubjectRepository subjectRepository;
    private final HallRepository hallRepository;

    public void automateTable(String batchId) {
        List<Subject> subjects = subjectRepository.findByBatchCode(batchId);
        log.info("Starting automation for Batch: {}", batchId);

        for (Subject sub : subjects) {
            // 1. THEORY ALLOCATION (Now forced to 3 slots)
            int lectureDur = 3;
            allocateToCorrectHall(sub, sub.getLectureInCharge(), lectureDur, " (Theory)", false, batchId);

            // 2. PRACTICAL ALLOCATION (Still 2 slots as per previous requirement)
            if (sub.isHasPractical()) {
                int pracDur = 2;
                allocateToCorrectHall(sub, sub.getPracticalInCharge(), pracDur, " (Practical)", true, batchId);
            }

            sub.setAlocated(true);
            subjectRepository.save(sub);
        }
    }

    private void allocateToCorrectHall(Subject sub, List<String> staff, int duration,
                                       String suffix, boolean needsLab, String batchId) {

        String[] weekDays = {"monday", "tuesday", "wednesday", "thursday", "friday"};
        List<Hall> validHalls = hallRepository.findByIsLab(needsLab);

        if (validHalls.isEmpty()) {
            log.warn("No halls found for isLab={}. Skipping {}", needsLab, suffix);
            return;
        }

        for (String day : weekDays) {
            // Rule 1: Batch cannot have more than 3 classes per day
            if (countBatchClassesAcrossHalls(day, batchId) >= 3) continue;

            for (Hall hall : validHalls) {
                HallAllocation alloc = hallAllocationRepository.findById(hall.getId())
                        .orElseGet(() -> initializeNewHallAllocation(hall.getId()));

                // Rule 2: Find free slots in the ROOM
                int[] foundSlots = findSlots(alloc, day, duration, 8);

                if (foundSlots != null) {
                    // Rule 3: Ensure BATCH and STAFF are free during these specific slots
                    boolean batchFree = isBatchFreeAtThisTime(day, foundSlots, batchId);
                    boolean staffFree = isStaffFreeAtThisTime(day, foundSlots, staff);

                    if (batchFree && staffFree) {
                        TimeSlot newSlot = new TimeSlot();
                        newSlot.setSubject(sub.getName() + suffix);
                        newSlot.setNumOfSlots(duration);
                        newSlot.setStaffs(staff != null ? staff : new ArrayList<>());
                        newSlot.setBatches(List.of(batchId));
                        newSlot.setSlotId(foundSlots);

                        HallUtil.getDaySlots(alloc, day).add(newSlot);
                        hallAllocationRepository.save(alloc);

                        log.info("Saved: {} | Day: {} | Hall: {} | Slots: {}",
                                sub.getName() + suffix, day, hall.getId(), foundSlots);
                        return; // Successfully placed, move to next task
                    }
                }
            }
        }
    }

    private boolean isBatchFreeAtThisTime(String day, int[] proposedSlots, String batchId) {
        List<HallAllocation> all = hallAllocationRepository.findAll();
        for (HallAllocation ha : all) {
            for (TimeSlot ts : HallUtil.getDaySlots(ha, day)) {
                if (ts.getBatches().contains(batchId)) {
                    if (hasOverlap(proposedSlots, ts.getSlotId())) return false;
                }
            }
        }
        return true;
    }

    private boolean isStaffFreeAtThisTime(String day, int[] proposedSlots, List<String> staffIds) {
        if (staffIds == null || staffIds.isEmpty()) return true;
        List<HallAllocation> all = hallAllocationRepository.findAll();
        for (HallAllocation ha : all) {
            for (TimeSlot ts : HallUtil.getDaySlots(ha, day)) {
                boolean conflict = ts.getStaffs().stream().anyMatch(staffIds::contains);
                if (conflict) {
                    if (hasOverlap(proposedSlots, ts.getSlotId())) return false;
                }
            }
        }
        return true;
    }

    private boolean hasOverlap(int[] slotsA, int[] slotsB) {
        for (int a : slotsA) {
            for (int b : slotsB) {
                if (a == b) return true;
            }
        }
        return false;
    }

    private int countBatchClassesAcrossHalls(String day, String batchId) {
        return (int) hallAllocationRepository.findAll().stream()
                .flatMap(h -> HallUtil.getDaySlots(h, day).stream())
                .filter(s -> s.getBatches() != null && s.getBatches().contains(batchId))
                .count();
    }

    private int[] findSlots(HallAllocation hall, String day, int duration, int limit) {
        List<TimeSlot> existingSlots = HallUtil.getDaySlots(hall, day);
        for (int start = 2; start <= (limit - duration + 1); start++) {
            if (HallUtil.isRangeFree(existingSlots, start, duration)) {
                int[] result = new int[duration];
                for (int i = 0; i < duration; i++) result[i] = start + i;
                return result;
            }
        }
        return null;
    }

    private HallAllocation initializeNewHallAllocation(String hallId) {
        HallAllocation hall = new HallAllocation();
        hall.setHallId(hallId);
        hall.setMonday(new ArrayList<>()); hall.setTuesday(new ArrayList<>());
        hall.setWednesday(new ArrayList<>()); hall.setThursday(new ArrayList<>());
        hall.setFriday(new ArrayList<>()); hall.setSaturday(new ArrayList<>());
        hall.setSunday(new ArrayList<>());
        return hall;
    }
}