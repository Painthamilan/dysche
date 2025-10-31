package com.painthu.dynamictable.Model;

import lombok.AllArgsConstructor;
import lombok.*;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "batches")
public class Batch {

    @Id
    private String id;
    private String name;
    private String programCode;
    private String type;
    private int studentCount;
    private String[] modules;
}
