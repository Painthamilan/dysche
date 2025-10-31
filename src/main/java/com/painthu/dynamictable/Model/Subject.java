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
@Document(collection = "modules")
public class Subject {

    @Id
    private String id;
    private String name;
    private String programCode;
    private String batchCode;
    private String hasCommonBatches;

    private List<String> commonBatches;
    private List<String> lectureInCharge;
    private List<String> practicalInCharge;
    private int lectureDuration;
    private int tutorialDuration;
    private int practicalDuration;
    private boolean hasPractical = true;
    private boolean isAlocated=false;

}
