package org.example.newmongoproject.dto.request;

import lombok.Data;


import java.util.List;

@Data
public class TeacherDTO {

    String name;

    String email;

    List<String> subjectsId;
}
