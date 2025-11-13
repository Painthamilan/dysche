package com.painthu.dynamictable.Utils;



import com.painthu.dynamictable.Model.HallAllocation;
import com.painthu.dynamictable.Model.TimeSlot;

import java.util.*;
import java.util.stream.Collectors;
import java.util.Arrays;

public class HallUtil {

    private HallUtil() {
        // prevent instantiation
    }

    public static List<TimeSlot> getDaySlots(HallAllocation hall, String day) {
        return switch (day.toLowerCase()) {
            case "monday" -> hall.getMonday();
            case "tuesday" -> hall.getTuesday();
            case "wednesday" -> hall.getWednesday();
            case "thursday" -> hall.getThursday();
            case "friday" -> hall.getFriday();
            case "saturday" -> hall.getSaturday();
            case "sunday" -> hall.getSunday();
            default -> null;
        };
    }

    public static void setDaySlots(HallAllocation hall, String day, List<TimeSlot> slots) {
        switch (day.toLowerCase()) {
            case "monday" -> hall.setMonday(slots);
            case "tuesday" -> hall.setTuesday(slots);
            case "wednesday" -> hall.setWednesday(slots);
            case "thursday" -> hall.setThursday(slots);
            case "friday" -> hall.setFriday(slots);
            case "saturday" -> hall.setSaturday(slots);
            case "sunday" -> hall.setSunday(slots);
        }
    }

    public static int[] findAvailableSlots(HallAllocation hall, String day, int count) {
        Set<Integer> existingIds = getDaySlots(hall, day).stream()
                .flatMap(s -> Arrays.stream(s.getSlotId()).boxed())
                .collect(Collectors.toSet());

        int[] result = new int[count];
        int candidate = 1; // start from slot 1

        for (int i = 0; i < count; ) {
            if (!existingIds.contains(candidate)) {
                result[i] = candidate;
                existingIds.add(candidate); // mark as taken
                i++;
            }
            candidate++;
        }

        return result;
    }

}
