package com.painthu.dynamictable.Utils;

import com.painthu.dynamictable.Model.HallAllocation;
import com.painthu.dynamictable.Model.TimeSlot;
import java.util.ArrayList;
import java.util.List;

public class HallUtil {

    private HallUtil() {}

    /**
     * Safely retrieves the list of slots for a given day.
     * Ensures an empty ArrayList is returned instead of null to prevent crashes.
     */
    public static List<TimeSlot> getDaySlots(HallAllocation hall, String day) {
        List<TimeSlot> slots = switch (day.toLowerCase()) {
            case "monday" -> hall.getMonday();
            case "tuesday" -> hall.getTuesday();
            case "wednesday" -> hall.getWednesday();
            case "thursday" -> hall.getThursday();
            case "friday" -> hall.getFriday();
            default -> new ArrayList<>(); // Return empty list for safety
        };

        // Extra safety: If the database field itself was null
        return (slots == null) ? new ArrayList<>() : slots;
    }

    /**
     * Checks if a range of slots is free in a specific day's list.
     */
    public static boolean isRangeFree(List<TimeSlot> existing, int start, int duration) {
        int end = start + duration - 1;

        for (TimeSlot ts : existing) {
            if (ts.getSlotId() == null) continue;

            for (int occupied : ts.getSlotId()) {
                // Collision check: does any occupied slot overlap with our range?
                if (occupied >= start && occupied <= end) {
                    return false;
                }
            }
        }
        return true;
    }
}