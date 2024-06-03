package org.example.newmongoproject.dto.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AverageSvgGrade {

    String studentId;

    Double averageGrade;

}
