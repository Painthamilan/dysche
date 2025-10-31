package com.painthu.dynamictable.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "hall_allocations")
public class HallAllocation {

    @Id
    private String hallId; // Unique identifier (used as MongoDB _id)

    private List<TimeSlot> monday;
    private List<TimeSlot> tuesday;
    private List<TimeSlot> wednesday;
    private List<TimeSlot> thursday;
    private List<TimeSlot> friday;
    private List<TimeSlot> saturday;
    private List<TimeSlot> sunday;
}
