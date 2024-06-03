package org.example.newmongoproject.controller;

import lombok.AllArgsConstructor;
import org.example.newmongoproject.components.TokenStore;
import org.example.newmongoproject.dto.request.StudentBySubjectDTO;
import org.example.newmongoproject.model.Student;
import org.example.newmongoproject.service.AggregationMongoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@RestController
@RequestMapping("/api/aggregate")
@AllArgsConstructor
public class AggregateController {

    private final AggregationMongoService aggregationMongoService;

    private TokenStore tokenStore;


    @GetMapping("/svg")
    public ResponseEntity<?> averageGrades(@RequestParam String subjectId, @RequestParam String studentId, @RequestHeader("Authorization") String token) {
        MongoTemplate mongoTemplate = tokenStore.getMongoTemplate(token);

        if (mongoTemplate == null) {
            return ResponseEntity.status(401).body("No authorized");
        }
        return ResponseEntity.status(200).body(aggregationMongoService.calculateAverageGrade(subjectId, studentId, mongoTemplate));
    }

    @GetMapping("/countDocuments/{collectionName}")
    public ResponseEntity<?> countDocuments(@PathVariable String collectionName, @RequestHeader("Authorization") String token) {
        MongoTemplate mongoTemplate = tokenStore.getMongoTemplate(token);

        if (mongoTemplate == null) {
            return ResponseEntity.status(401).body("No authorized");
        }
        return ResponseEntity.status(200).body(mongoTemplate.count(new Query(), collectionName));
    }
}
