package com.painthu.dynamictable.Model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeSlot {
    private int[] slotId;
    private int numOfSlots;
    private String subject;
    private String[] staffs;
    private List<String> batches;
    private boolean isOccupied;
}
