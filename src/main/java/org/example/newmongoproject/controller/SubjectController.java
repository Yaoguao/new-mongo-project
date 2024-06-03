package org.example.newmongoproject.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.example.newmongoproject.components.TokenStore;
import org.example.newmongoproject.dto.request.StudentBySubjectDTO;
import org.example.newmongoproject.dto.request.StudentDTO;
import org.example.newmongoproject.dto.request.SubjectDTO;
import org.example.newmongoproject.dto.response.AverageSvgGrade;
import org.example.newmongoproject.dto.response.StudentWithGrades;
import org.example.newmongoproject.exeption.NotAuthorizedExeption;
import org.example.newmongoproject.service.AggregationMongoService;
import org.example.newmongoproject.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "subject_methods")
@RestController
@RequestMapping("/api/subjects")
@AllArgsConstructor
public class SubjectController {

    @Autowired
    private TokenStore tokenStore;

    private SubjectService subjectService;

    private AggregationMongoService aggregationMongoService;

    @PostMapping
    public ResponseEntity<?> createSubject(@RequestBody SubjectDTO subjectDTO,@RequestHeader("Authorization") String token, HttpSession session) {
        MongoTemplate mongoTemplate = tokenStore.getMongoTemplate(token);

        if (mongoTemplate == null) {
            return ResponseEntity.status(401).body("No authorized");
        }
        try {
            return ResponseEntity.ok(subjectService.createSybject(subjectDTO, mongoTemplate));
        } catch (NotAuthorizedExeption e) {
            return ResponseEntity.status(401).body(e.toString());
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSubject(@PathVariable String id, @RequestBody SubjectDTO subjectDTO,@RequestHeader("Authorization") String token, HttpSession session) {
        MongoTemplate mongoTemplate = tokenStore.getMongoTemplate(token);

        if (mongoTemplate == null) {
            return ResponseEntity.status(401).body("No authorized");
        }

        return ResponseEntity.ok(subjectService.updateSubject(id, subjectDTO, mongoTemplate));
    }

    @GetMapping
    public ResponseEntity<?> getAllSubjects(@RequestHeader("Authorization") String token, HttpSession session) {
        MongoTemplate mongoTemplate = tokenStore.getMongoTemplate(token);
        System.out.println(session);
        if (mongoTemplate == null) {
            return ResponseEntity.status(401).body("No authorized");
        }

        return ResponseEntity.ok(subjectService.getAllSubjects(mongoTemplate));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSubject(@PathVariable String id,@RequestHeader("Authorization") String token, HttpSession session) {
        MongoTemplate mongoTemplate = tokenStore.getMongoTemplate(token);

        if (mongoTemplate == null) {
            return ResponseEntity.status(401).body("No authorized");
        }

        return ResponseEntity.ok(subjectService.getSubject(id, mongoTemplate));
    }

    @GetMapping("/subjectsByTeacher/{teacherId}")
    public ResponseEntity<?> getSubjectsByTeacherId(@PathVariable String teacherId,@RequestHeader("Authorization") String token, HttpSession session) {
        MongoTemplate mongoTemplate = tokenStore.getMongoTemplate(token);

        if (mongoTemplate == null) {
            return ResponseEntity.status(401).body("No authorized");
        }

        return ResponseEntity.ok(subjectService.getSubjectsByTeacherId(teacherId, mongoTemplate));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSubject(@PathVariable String id,@RequestHeader("Authorization") String token, HttpSession session) {
        MongoTemplate mongoTemplate = tokenStore.getMongoTemplate(token);

        if (mongoTemplate == null) {
            return ResponseEntity.status(401).body("No authorized");
        }

        subjectService.deleteSubject(id, mongoTemplate);

        return ResponseEntity.ok("Delete subject");
    }

    @GetMapping("findStudents/{subjectName}")
    public ResponseEntity<?> findStudentsWithGradesForSubject(
            @PathVariable String subjectName,
            @RequestHeader("Authorization") String token,
            HttpSession session) {
        MongoTemplate mongoTemplate = tokenStore.getMongoTemplate(token);

        if (mongoTemplate == null) {
            return ResponseEntity.status(401).body("No authorized");
        }

        List<StudentWithGrades> studentsWithGradesForSubject =
                subjectService.findStudentsWithGradesForSubject(subjectName, mongoTemplate);

        return ResponseEntity.ok(studentsWithGradesForSubject);

    }

    @PostMapping("/addGradesStudent")
    public ResponseEntity<?> addStudentBySubject(@RequestBody StudentBySubjectDTO studentBySubjectDTO,@RequestHeader("Authorization") String token) {
        MongoTemplate mongoTemplate = tokenStore.getMongoTemplate(token);

        if (mongoTemplate == null) {
            return ResponseEntity.status(401).body("No authorized");
        }

        return ResponseEntity.ok(subjectService.addStudentBySubject(studentBySubjectDTO, mongoTemplate));
    }



}
