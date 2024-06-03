package org.example.newmongoproject.dto.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentBySubjectDTO {
    String subjectId;
    String studentId;
    List<Integer> grades;
}
