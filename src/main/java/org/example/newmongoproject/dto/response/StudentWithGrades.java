package org.example.newmongoproject.dto.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.newmongoproject.model.Student;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;

import java.util.List;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentWithGrades {

    Student student;

    List<Integer> grades;
}
