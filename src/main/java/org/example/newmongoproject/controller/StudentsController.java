package org.example.newmongoproject.controller;

import com.mongodb.MongoCommandException;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.example.newmongoproject.components.TokenStore;
import org.example.newmongoproject.dto.request.StudentDTO;
import org.example.newmongoproject.exeption.NotAuthorizedExeption;
import org.example.newmongoproject.model.Student;
import org.example.newmongoproject.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "student_methods")
@RestController
@RequestMapping("/api/students")
@AllArgsConstructor
public class StudentsController {

    @Autowired
    private TokenStore tokenStore;

    private StudentService studentService;

    @PostMapping
    public ResponseEntity<?> createStudent(@RequestBody StudentDTO studentDTO,@RequestHeader("Authorization") String token, HttpSession session) {
        MongoTemplate mongoTemplate = tokenStore.getMongoTemplate(token);
        System.out.println(session);
        if (mongoTemplate == null) {
            return ResponseEntity.status(401).body("No authorized");
        }
        try {
            Student student = studentService.createStudent(studentDTO, mongoTemplate);
            return ResponseEntity.ok(student);
        } catch (MongoCommandException e) {
            return ResponseEntity.status(401).body("There is no access");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable String id, @RequestBody StudentDTO studentDTO,@RequestHeader("Authorization") String token) {
        MongoTemplate mongoTemplate = tokenStore.getMongoTemplate(token);

        if (mongoTemplate == null) {
            return ResponseEntity.status(401).body("No authorized");
        }

        return ResponseEntity.ok(studentService.updateStudent(id,studentDTO,mongoTemplate));
    }

    @GetMapping
    public ResponseEntity<?> getAllStudents(@RequestHeader("Authorization") String token, HttpSession session) {
        MongoTemplate mongoTemplate = tokenStore.getMongoTemplate(token);

        if (mongoTemplate == null) {
            return ResponseEntity.status(401).body("No authorized");
        }

        return ResponseEntity.ok(studentService.getAllStudents(mongoTemplate));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStudent(@PathVariable String id, @RequestHeader("Authorization") String token, HttpSession session) {
        MongoTemplate mongoTemplate = tokenStore.getMongoTemplate(token);

        if (mongoTemplate == null) {
            return ResponseEntity.status(401).body("No authorized");
        }

        return ResponseEntity.ok(studentService.getStudent(id, mongoTemplate));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSrudent(@PathVariable String id, @RequestHeader("Authorization") String token, HttpSession session) {
        MongoTemplate mongoTemplate = tokenStore.getMongoTemplate(token);

        if (mongoTemplate == null) {
            return ResponseEntity.status(401).body("No authorized");
        }

        studentService.deleteStudent(id, mongoTemplate);

        return ResponseEntity.ok("Delete student");
    }

}
