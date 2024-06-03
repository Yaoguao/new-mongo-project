package org.example.newmongoproject.service;

import org.example.newmongoproject.dto.request.StudentBySubjectDTO;
import org.example.newmongoproject.dto.request.StudentDTO;
import org.example.newmongoproject.dto.request.SubjectDTO;
import org.example.newmongoproject.dto.response.StudentWithGrades;
import org.example.newmongoproject.exeption.NotFindStudent;
import org.example.newmongoproject.model.Student;
import org.example.newmongoproject.model.Subject;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SubjectService {

    public Subject createSybject(SubjectDTO subjectDTO, MongoTemplate mongoTemplate) {

        if (subjectDTO.getGradesStudents() == null) {
            subjectDTO.setGradesStudents(new HashMap<>());
        }

        Subject subject = Subject.builder()
                .name(subjectDTO.getName())
                .teacherId(subjectDTO.getTeacherId())
                .gradesStudents(subjectDTO.getGradesStudents())
                .build();
        return mongoTemplate.save(subject);
    }

    public Subject getSubject(String id, MongoTemplate mongoTemplate) {
        return mongoTemplate.findById(id, Subject.class);
    }

    public Subject updateSubject(String id, SubjectDTO subjectDTO, MongoTemplate mongoTemplate) {
        System.out.println(subjectDTO);
        Subject exSubject = mongoTemplate.findById(id, Subject.class);
        exSubject.setName(subjectDTO.getName());
        exSubject.setTeacherId(subjectDTO.getTeacherId());
        exSubject.setGradesStudents(subjectDTO.getGradesStudents());
        return mongoTemplate.save(exSubject);
    }

    public void deleteSubject(String id, MongoTemplate mongoTemplate) {
        Subject subject = mongoTemplate.findById(id, Subject.class);
        mongoTemplate.remove(subject);
    }

    public List<Subject> getAllSubjects(MongoTemplate mongoTemplate) {
        return mongoTemplate.findAll(Subject.class);
    }

    public List<Subject> getSubjectsByTeacherId(String teacherId, MongoTemplate mongoTemplate) {
        List<Subject> allSubject = mongoTemplate.findAll(Subject.class);
        List<Subject> subjectList = allSubject.stream()
                .filter(sub -> sub.getTeacherId().equals(teacherId))
                .collect(Collectors.toList());
        return subjectList;
    }

    public List<StudentWithGrades> findStudentsWithGradesForSubject(String subjectName, MongoTemplate mongoTemplate) {
        Query subjectQuery = new Query();
        subjectQuery.addCriteria(Criteria.where("name").is(subjectName));
        Subject subject = mongoTemplate.findOne(subjectQuery, Subject.class);

        if (subject == null) {
            return null;
        }

        List<StudentWithGrades> studentsWithGrades = new ArrayList<>();
        for (Map.Entry<String, List<Integer>> entry : subject.getGradesStudents().entrySet()) {
            String studentId = entry.getKey();
            List<Integer> grades = entry.getValue();

            Query studentQuery = new Query();
            studentQuery.addCriteria(Criteria.where("id").is(studentId));
            Student student = mongoTemplate.findOne(studentQuery, Student.class);

            if (student != null) {
                StudentWithGrades studentWithGrades = new StudentWithGrades();
                studentWithGrades.setStudent(student);
                studentWithGrades.setGrades(grades);
                studentsWithGrades.add(studentWithGrades);
            }
        }

        return studentsWithGrades;
    }

    public Subject addStudentBySubject(StudentBySubjectDTO studentBySubjectDTO, MongoTemplate mongoTemplate) {

        Student student = mongoTemplate.findById(studentBySubjectDTO.getStudentId(), Student.class);

        if (student == null) {
            throw new NotFindStudent();
        }

        Subject subject = mongoTemplate.findById(studentBySubjectDTO.getSubjectId(), Subject.class);

        Map<String, List<Integer>> gradesStudents = subject.getGradesStudents();

        gradesStudents.put(student.getId(), studentBySubjectDTO.getGrades());

        subject.setGradesStudents(gradesStudents);

        return mongoTemplate.save(subject);
    }


}
