package org.example.newmongoproject.service;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.connection.ClusterDescription;
import com.mongodb.connection.ServerConnectionState;
import com.mongodb.connection.ServerDescription;
import com.mongodb.internal.connection.Cluster;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.example.newmongoproject.exeption.NotAuthorizedExeption;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;


@Service
@Slf4j
public class MongoService {

    public MongoTemplate mongoTemplate() {
        String connection = "mongodb://mongo:27017/TestDiary";
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connection))
                .build();

        MongoClient mongoClient = MongoClients.create(mongoClientSettings);

        log.info(
                mongoClient + ""
        );
        return new MongoTemplate(mongoClient, "TestDiary");
    }

//    public MongoTemplate mongoTemplate(String username, String password) {
//        String connection = "mongodb://" + username + ":" + password + "@localhost:27017/TestDiary";
//        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
//                .applyConnectionString(new ConnectionString(connection))
//                .build();
//
//        MongoClient mongoClient = MongoClients.create(mongoClientSettings);
//
//        log.info(
//                mongoClient + ""
//        );
//        return new MongoTemplate(mongoClient, "TestDiary");
//    }
}
