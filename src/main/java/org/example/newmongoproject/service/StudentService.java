package org.example.newmongoproject.service;

import com.mongodb.MongoCommandException;
import org.example.newmongoproject.dto.request.StudentDTO;
import org.example.newmongoproject.dto.response.StudentWithGrades;
import org.example.newmongoproject.exeption.NotAuthorizedExeption;
import org.example.newmongoproject.model.Student;
import org.example.newmongoproject.model.Subject;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class StudentService {

    public Student createStudent(StudentDTO studentDTO, MongoTemplate mongoTemplate) {
        Student student = Student.builder()
                .name(studentDTO.getName())
                .email(studentDTO.getEmail())
                .build();
        return mongoTemplate.save(student);
    }

    public Student getStudent(String id, MongoTemplate mongoTemplate) {
        return mongoTemplate.findById(id, Student.class);
    }

    public Student updateStudent(String id, StudentDTO studentDTO, MongoTemplate mongoTemplate) {
        Student exStudent = mongoTemplate.findById(id, Student.class);
        exStudent.setName(studentDTO.getName());
        exStudent.setEmail(studentDTO.getEmail());
        return mongoTemplate.save(exStudent);
    }

    public void deleteStudent(String id, MongoTemplate mongoTemplate) {
        Student student = mongoTemplate.findById(id, Student.class);
        mongoTemplate.remove(student);
    }

    public List<Student> getAllStudents(MongoTemplate mongoTemplate) {
        return mongoTemplate.findAll(Student.class);
    }




}
