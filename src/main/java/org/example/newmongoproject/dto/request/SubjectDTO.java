package org.example.newmongoproject.dto.request;

import lombok.Data;
import org.example.newmongoproject.model.Student;
import org.example.newmongoproject.model.Teacher;

import java.util.List;
import java.util.Map;

@Data
public class SubjectDTO {

    String name;

    String teacherId;

    Map<String, List<Integer>> gradesStudents;

}
