package com.painthu.dynamictable.Model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Timetable {

    @Id
    private String id;          // optional, or you can use hallId as id
    private String hallId;      // reference to Hall
    private String day;         // e.g. "monday"
    private List<TimeSlot> slots;
}