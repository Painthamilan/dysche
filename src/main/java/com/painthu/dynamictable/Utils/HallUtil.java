package com.painthu.dynamictable.Utils;

import com.painthu.dynamictable.Model.HallAllocation;
import com.painthu.dynamictable.Model.TimeSlot;

import java.util.*;
import java.util.stream.Collectors;
import java.util.Arrays;

public class HallUtil {

    private HallUtil() {}

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

    public static int[] findAvailableSlots(
            HallAllocation hall,
            String day,
            int count,
            int startFrom,
            boolean isWeekendDay
    ) {
        int maxSlot = isWeekendDay ? 13 : 10;

        List<TimeSlot> daySlots = getDaySlots(hall, day);
        if (daySlots == null) daySlots = Collections.emptyList();

        Set<Integer> occupied = daySlots.stream()
                .filter(s -> s.getSlotId() != null) // 🔥 CRITICAL FIX
                .flatMap(s -> Arrays.stream(s.getSlotId()).boxed())
                .collect(Collectors.toSet());

        for (int start = startFrom; start + count - 1 <= maxSlot; start++) {

            boolean canFit = true;
            for (int i = 0; i < count; i++) {
                if (occupied.contains(start + i)) {
                    canFit = false;
                    break;
                }
            }

            if (canFit) {
                int[] result = new int[count];
                for (int i = 0; i < count; i++) {
                    result[i] = start + i;
                }
                return result;
            }
        }

        throw new IllegalStateException("SLOT_IS_FULL");
    }

    public static boolean isWeekendDay(String day) {
        return "saturday".equalsIgnoreCase(day)
                || "sunday".equalsIgnoreCase(day);
    }
}
