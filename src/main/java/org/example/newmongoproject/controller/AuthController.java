package org.example.newmongoproject.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.newmongoproject.components.TokenStore;
import org.example.newmongoproject.dto.response.LoginDTO;
import org.example.newmongoproject.service.MongoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "auth_methods")
@RestController
@Slf4j
@RequestMapping("/api")
@AllArgsConstructor
public class AuthController {

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    public MongoService mongoService;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO, HttpSession session) {
        System.out.println(session);

        String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();

        MongoTemplate mongoTemplate = mongoService.mongoTemplate(username, password);
        System.out.println(mongoTemplate);
        if (mongoTemplate == null) {
            return ResponseEntity.status(401).body("No auth");
        }

        String token = UUID.randomUUID().toString();
        tokenStore.storeToken(token, mongoTemplate);

        System.out.println(mongoTemplate.getExceptionTranslator());
        return ResponseEntity.status(201).body(token);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody String token) {
        tokenStore.removeToken(token);
        return ResponseEntity.ok("Logged out successfully");
    }

   /* @PostMapping("/api/students")
    public ResponseEntity<?> createStudent(@RequestBody StudentDTO studentDTO) {
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

    @PutMapping("/api/students/{id}")
    public ResponseEntity<?> updateStudent(String id, @RequestBody StudentDTO studentDTO) {
        if (mongoTemplate == null) {
            return ResponseEntity.status(401).body("No authorized");
        }

        return ResponseEntity.ok(studentService.updateStudent(id,studentDTO,mongoTemplate));
    }

    @GetMapping("/api/students")
    public ResponseEntity<?> getAllStudents() {
        if (mongoTemplate == null) {
            return ResponseEntity.status(401).body("No authorized");
        }

        return ResponseEntity.ok(studentService.getAllStudents(mongoTemplate));
    }

    @GetMapping("/api/students/{id}")
    public ResponseEntity<?> getStudent(@PathVariable String id) {
        if (mongoTemplate == null) {
            return ResponseEntity.status(401).body("No authorized");
        }

        return ResponseEntity.ok(studentService.getStudent(id, mongoTemplate));
    }

    @DeleteMapping("/api/students/{id}")
    public ResponseEntity<?> deleteSrudent(@PathVariable String id) {
        if (mongoTemplate == null) {
            return ResponseEntity.status(401).body("No authorized");
        }

        studentService.deleteStudent(id, mongoTemplate);

        return ResponseEntity.ok("Delete student");
    }

    @PostMapping("/api/subjects")
    public ResponseEntity<?> createSubject(@RequestBody SubjectDTO subjectDTO) {
        if (mongoTemplate == null) {
            return ResponseEntity.status(401).body("No authorized");
        }
        try {
            return ResponseEntity.ok(subjectService.createSybject(subjectDTO, mongoTemplate));
        } catch (NotAuthorizedExeption e) {
            return ResponseEntity.status(401).body(e.toString());
        }

    }

    @PutMapping("/api/subjects/{id}")
    public ResponseEntity<?> updateSubject(@PathVariable String id, @RequestBody SubjectDTO subjectDTO) {
        if (mongoTemplate == null) {
            return ResponseEntity.status(401).body("No authorized");
        }

        return ResponseEntity.ok(subjectService.updateSubject(id, subjectDTO, mongoTemplate));
    }

    @GetMapping("/api/subjects")
    public ResponseEntity<?> getAllSubjects() {
        if (mongoTemplate == null) {
            return ResponseEntity.status(401).body("No authorized");
        }

        return ResponseEntity.ok(subjectService.getAllSubjects(mongoTemplate));
    }

    @GetMapping("/api/subjects/{id}")
    public ResponseEntity<?> getSubject(@PathVariable String id) {
        if (mongoTemplate == null) {
            return ResponseEntity.status(401).body("No authorized");
        }

        return ResponseEntity.ok(subjectService.getSubject(id, mongoTemplate));
    }

    @GetMapping("/api/subjects/subjectsByTeacher/{teacherId}")
    public ResponseEntity<?> getSubjectsByTeacherId(@PathVariable String teacherId) {
        if (mongoTemplate == null) {
            return ResponseEntity.status(401).body("No authorized");
        }

        return ResponseEntity.ok(subjectService.getSubjectsByTeacherId(teacherId, mongoTemplate));
    }

    @DeleteMapping("/api/subjects/{id}")
    public ResponseEntity<?> deleteSubject(@PathVariable String id) {
        if (mongoTemplate == null) {
            return ResponseEntity.status(401).body("No authorized");
        }

        subjectService.deleteSubject(id, mongoTemplate);

        return ResponseEntity.ok("Delete subject");
    }

    @GetMapping("/api/subjects/findStudents/{subjectName}")
    public ResponseEntity<?> findStudentsWithGradesForSubject(@PathVariable String subjectName) {
        if (mongoTemplate == null) {
            return ResponseEntity.status(401).body("No authorized");
        }

        List<StudentWithGrades> studentsWithGradesForSubject =
                subjectService.findStudentsWithGradesForSubject(subjectName, mongoTemplate);

        return ResponseEntity.ok(studentsWithGradesForSubject);

    }

    @PostMapping("/api/subjects/addGradesStudent")
    public ResponseEntity<?> addStudentBySubject(@RequestBody StudentBySubjectDTO studentBySubjectDTO) {
        if (mongoTemplate == null) {
            return ResponseEntity.status(401).body("No authorized");
        }

        try {
            return ResponseEntity.ok(subjectService.addStudentBySubject(studentBySubjectDTO, mongoTemplate));
        } catch (NotAuthorizedExeption e) {
            return ResponseEntity.status(401).body(e.toString());
        }

    }

    @PostMapping("/api/teachers")
    public ResponseEntity<?> createTeacher(@RequestBody TeacherDTO teacherDTO) {
        if (mongoTemplate == null) {
            return ResponseEntity.status(401).body("No authorized");
        }

        return ResponseEntity.ok(teacherService.createTeacher(teacherDTO, mongoTemplate));
    }

    @PutMapping("/api/teachers/{id}")
    public ResponseEntity<?> updateTeacher(@PathVariable String id, @RequestBody TeacherDTO teacherDTO) {
        if (mongoTemplate == null) {
            return ResponseEntity.status(401).body("No authorized");
        }

        return ResponseEntity.ok(teacherService.updateTeacher(id, teacherDTO, mongoTemplate));
    }

    @GetMapping("/api/teachers")
    public ResponseEntity<?> getAllTeachers() {
        if (mongoTemplate == null) {
            return ResponseEntity.status(401).body("No authorized");
        }

        return ResponseEntity.ok(teacherService.getAllTeachers(mongoTemplate));
    }

    @GetMapping("/api/teachers/{id}")
    public ResponseEntity<?> getTeacher(@PathVariable String id) {
        if (mongoTemplate == null) {
            return ResponseEntity.status(401).body("No authorized");
        }

        return ResponseEntity.ok(teacherService.getTeacher(id, mongoTemplate));
    }

    @PutMapping("/api/teachers/updateGrades/{id}")
    public ResponseEntity<?> updateStudentGradeInSubjects(
            @PathVariable String id,
            @RequestParam String studentId,
            @RequestParam String subjectName,
            @RequestBody List<Integer> newGrades) {
        if (mongoTemplate == null) {
            return ResponseEntity.status(401).body("No authorized");
        }

        teacherService.updateStudentGradeInSubjects(id,studentId,subjectName,newGrades, mongoTemplate);

        return ResponseEntity.ok("Update");
    }*/


}
