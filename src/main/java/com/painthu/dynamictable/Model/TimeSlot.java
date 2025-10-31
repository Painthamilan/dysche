package com.painthu.dynamictable.Model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeSlot {
    private int[] slotId;
    private String subject;
    private String[] staffs;
    private String[] batches;
    private boolean isOccupied;
}
