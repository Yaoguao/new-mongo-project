package org.example.newmongoproject.dto.request;

import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;

@Data
public class StudentDTO {

    String name;

    String email;
}
