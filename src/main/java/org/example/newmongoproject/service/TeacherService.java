package org.example.newmongoproject.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.newmongoproject.dto.request.TeacherDTO;
import org.example.newmongoproject.model.Student;
import org.example.newmongoproject.model.Subject;
import org.example.newmongoproject.model.Teacher;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class TeacherService {

    private SubjectService subjectService;

    public Teacher createTeacher(TeacherDTO teacherDTO, MongoTemplate mongoTemplate) {

        if (teacherDTO.getSubjectsId() == null) {
            teacherDTO.setSubjectsId(new ArrayList<>());
        }

        Teacher teacher = Teacher.builder()
                .name(teacherDTO.getName())
                .email(teacherDTO.getEmail())
                .subjects(teacherDTO.getSubjectsId())
                .build();
        return mongoTemplate.insert(teacher);
    }

    public Teacher getTeacher(String id, MongoTemplate mongoTemplate) {
        return mongoTemplate.findById(id, Teacher.class);
    }

    public Teacher updateTeacher(String id, TeacherDTO teacherDTO, MongoTemplate mongoTemplate) {
        log.info(teacherDTO.toString());
        Teacher teacher = mongoTemplate.findById(id, Teacher.class);
        teacher.setId(id);
        teacher.setName(teacherDTO.getName());
        teacher.setEmail(teacherDTO.getEmail());
        teacher.setSubjects(teacherDTO.getSubjectsId());
        return mongoTemplate.save(teacher);
    }

    public void deleteTeacher(String id, MongoTemplate mongoTemplate) {
        log.info("id teacher: " + id);
        Teacher teacher = mongoTemplate.findById(id, Teacher.class);
        log.info("Teacher: " + teacher);
        mongoTemplate.remove(teacher);
    }

    public List<Teacher> getAllTeachers(MongoTemplate mongoTemplate) {
        return mongoTemplate.findAll(Teacher.class);
    }

    public void updateStudentGradeInSubjects(
            String teacherId,
            String studentId,
            String subjectName,
            List<Integer> newGrades,
            MongoTemplate mongoTemplate) {

        Query teacherQuery = new Query();
        teacherQuery.addCriteria(Criteria.where("id").is(teacherId));
        Teacher teacher = mongoTemplate.findOne(teacherQuery, Teacher.class);

        if (teacher == null) {
            throw new IllegalArgumentException("Учитель не найден или не преподает этот предмет");
        }

        Query subjectQuery = new Query();
        subjectQuery.addCriteria(Criteria.where("name").is(subjectName));
        Subject subject = mongoTemplate.findOne(subjectQuery, Subject.class);

        if (subject == null) {
            throw new IllegalArgumentException("Предмет не найден");
        }

        subject.getGradesStudents().put(studentId, newGrades);
        mongoTemplate.save(subject);
    }


}
