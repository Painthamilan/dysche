package com.painthu.dynamictable.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StaffTimeSlot {
    private int[] slotId;
    private String module;
    private String hall;
}