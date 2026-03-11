package com.painthu.dynamictable.Model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Document(collection = "staff_timetable")
public class StaffTimetable {

    @Id
    private String staffId;
    private String staffName;
    private Map<String, List<StaffTimeSlot>> schedule = new HashMap<>();
    private List<int[]> monday = new ArrayList<>();
    private List<int[]> tuesday = new ArrayList<>();
    private List<int[]> wednesday = new ArrayList<>();
    private List<int[]> thursday = new ArrayList<>();
    private List<int[]> friday = new ArrayList<>();
    private List<int[]> saturday = new ArrayList<>();
    private List<int[]> sunday = new ArrayList<>();
}
