package org.example.newmongoproject.service;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.example.newmongoproject.dto.response.AverageSvgGrade;
import org.example.newmongoproject.model.Student;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Accumulators.avg;
import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Projections.computed;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service
public class AggregationMongoService {

    public Double calculateAverageGrade(String subjectId, String studentId, MongoTemplate mongoTemplate) {
        Aggregation aggregation = newAggregation(
                match(Criteria.where("_id").is(new ObjectId(subjectId))),
                Aggregation.unwind("gradesStudents." + studentId),
                group().avg("gradesStudents." + studentId).as("averageGrade")
        );

        AggregationResults<Document> results = mongoTemplate.aggregate(aggregation, "subjects", Document.class);
        List<Document> mappedResults = results.getMappedResults();

        if (!mappedResults.isEmpty()) {
            return mappedResults.get(0).getDouble("averageGrade");
        } else {
            return null;
        }
    }

    public List<Student> searchStudent(String name, MongoTemplate mongoTemplate) {
        Aggregation aggregation = newAggregation(
                match(Criteria.where("name").regex(name)),
                project().andExclude("_id").andInclude("name")
        );

        AggregationResults<Student> results = mongoTemplate.aggregate(aggregation, "students", Student.class);
        List<Student> mappedResults = results.getMappedResults();

        return mappedResults;
    }

    public List<Document> searchSubject(String name, MongoTemplate mongoTemplate) {
        Aggregation aggregation = newAggregation(
                match(Criteria.where("name").regex(name)),
                project().andExclude("_id").andInclude("name")
        );

        AggregationResults<Document> results = mongoTemplate.aggregate(aggregation, "students", Document.class);
        List<Document> mappedResults = results.getMappedResults();

        return mappedResults;
    }

    public List<Document> searchTeacher(String name, MongoTemplate mongoTemplate) {
        Aggregation aggregation = newAggregation(
                match(Criteria.where("name").regex(name)),
                project().andExclude("_id").andInclude("name")
        );

        AggregationResults<Document> results = mongoTemplate.aggregate(aggregation, "students", Document.class);
        List<Document> mappedResults = results.getMappedResults();

        return mappedResults;
    }

    public long countStudents(MongoTemplate mongoTemplate) {
        Aggregation aggregation = newAggregation(
                group().count().as("count")
        );

        AggregationResults<Document> results = mongoTemplate.aggregate(aggregation, "students", Document.class);
        Document result = results.getUniqueMappedResult();

        if (result != null) {
            return result.getLong("count");
        } else {
            return 0;
        }
    }

//    public List<AverageSvgGrade> calculateAverageGrades(String subjectId, MongoTemplate mongoTemplate) {
//        MongoDatabase db = mongoTemplate.getDb();
//        MongoCollection<Document> collection = db.getCollection("subjects");
//        System.out.println("log aggreg");
//        List<Document> results = collection.aggregate(Arrays.asList(
//                match(eq("_id", new ObjectId(subjectId))),
//                project(fields(include("gradesStudents"), excludeId())),
//                unwind("$gradesStudents"),
//                unwind("$gradesStudents.v"),
//                group("$gradesStudents.k", avg("averageGrade", "$gradesStudents.v"))
//        )).into(new ArrayList<>());
//
//        results.forEach(document -> System.out.println(document.toJson()));
//
//
//        List<AverageSvgGrade> averageGrades = results.stream().map(document -> {
//            AverageSvgGrade avgGrade = new AverageSvgGrade();
//            avgGrade.setStudentId(document.getString("_id"));
//            avgGrade.setAverageGrade(document.getDouble("averageGrade"));
//            return avgGrade;
//        }).collect(Collectors.toList());
//
//        return averageGrades;
//    }

//    public List<AverageSvgGrade> calculateAverageGrades(String subjectId, MongoTemplate mongoTemplate) {
//        Query query = new Query();
//        query.addCriteria(Criteria.where("_id").is(subjectId));
//        Subject subject = mongoTemplate.findById(subjectId, Subject.class);
//
//        if (subject == null) {
//            return List.of();
//        }
//
//        Map<String, List<Integer>> gradesStudents = subject.getGradesStudents();
//
//        return gradesStudents.entrySet().stream()
//                .map(entry -> {
//                    double averageGrade = entry.getValue().stream()
//                            .mapToInt(Integer::intValue)
//                            .average()
//                            .orElse(0.0);
//                    AverageSvgGrade averageSvgGrade = new AverageSvgGrade();
//                    averageSvgGrade.setAverageGrade(averageGrade);
//                    averageSvgGrade.setStudentId(entry.getKey());
//                    return averageSvgGrade;
//                })
//                .collect(Collectors.toList());
//    }

    /*db.subjects.aggregate([
        { $match: { _id: ObjectId("6654f2acb7d8890cee94313f") } },
        { $project: { gradesStudents: { $objectToArray: "$gradesStudents" } } },
        { $unwind: "$gradesStudents" },
        { $unwind: "$gradesStudents.v" },
        { $group: { _id: "$gradesStudents.k", averageGrade: { $avg: "$gradesStudents.v" } } }
    ])*/


}
