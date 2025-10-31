package com.painthu.dynamictable.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "staff_timetables")
public class StaffTimetable {

    @Id
    private String staffId;
    private String staffName;

    // Map<day, list of slots>
    private Map<String, List<StaffTimeSlot>> schedule;
}