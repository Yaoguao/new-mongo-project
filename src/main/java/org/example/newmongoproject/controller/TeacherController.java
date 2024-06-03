package org.example.newmongoproject.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.example.newmongoproject.components.TokenStore;
import org.example.newmongoproject.dto.request.SubjectDTO;
import org.example.newmongoproject.dto.request.TeacherDTO;
import org.example.newmongoproject.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "teacher_methods")
@RestController
@RequestMapping("/api/teachers")
@AllArgsConstructor
public class TeacherController {

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private TeacherService teacherService;


    @PostMapping
    public ResponseEntity<?> createTeacher(@RequestBody TeacherDTO teacherDTO, @RequestHeader("Authorization") String token, HttpSession session) {
        MongoTemplate mongoTemplate = tokenStore.getMongoTemplate(token);

        if (mongoTemplate == null) {
            return ResponseEntity.status(401).body("No authorized");
        }

        return ResponseEntity.ok(teacherService.createTeacher(teacherDTO, mongoTemplate));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTeacher(@PathVariable String id, @RequestBody TeacherDTO teacherDTO, @RequestHeader("Authorization") String token, HttpSession session) {
        MongoTemplate mongoTemplate = tokenStore.getMongoTemplate(token);

        if (mongoTemplate == null) {
            return ResponseEntity.status(401).body("No authorized");
        }

        return ResponseEntity.ok(teacherService.updateTeacher(id, teacherDTO, mongoTemplate));
    }

    @GetMapping
    public ResponseEntity<?> getAllTeachers(@RequestHeader("Authorization") String token, HttpSession session) {
        MongoTemplate mongoTemplate = tokenStore.getMongoTemplate(token);

        if (mongoTemplate == null) {
            return ResponseEntity.status(401).body("No authorized");
        }

        return ResponseEntity.ok(teacherService.getAllTeachers(mongoTemplate));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTeacher(@PathVariable String id, @RequestHeader("Authorization") String token) {
        MongoTemplate mongoTemplate = tokenStore.getMongoTemplate(token);

        if (mongoTemplate == null) {
            return ResponseEntity.status(401).body("No authorized");
        }

        return ResponseEntity.ok(teacherService.getTeacher(id, mongoTemplate));
    }

    @PutMapping("/updateGrades/{id}")
    public ResponseEntity<?> updateStudentGradeInSubjects(
            @PathVariable String id,
            @RequestParam String studentId,
            @RequestParam String subjectName,
            @RequestBody List<Integer> newGrades,
            @RequestHeader("Authorization") String token) {
        MongoTemplate mongoTemplate = tokenStore.getMongoTemplate(token);

        if (mongoTemplate == null) {
            return ResponseEntity.status(401).body("No authorized");
        }

        teacherService.updateStudentGradeInSubjects(id, studentId, subjectName, newGrades, mongoTemplate);

        return ResponseEntity.ok("Update");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTeacher(@PathVariable String id, @RequestHeader("Authorization") String token) {
        MongoTemplate mongoTemplate = tokenStore.getMongoTemplate(token);

        if (mongoTemplate == null) {
            return ResponseEntity.status(401).body("No authorized");
        }

        teacherService.deleteTeacher(id,mongoTemplate);
        return ResponseEntity.ok("Ok");
    }
}
