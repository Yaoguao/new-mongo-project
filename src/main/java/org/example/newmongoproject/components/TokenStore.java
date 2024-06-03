package org.example.newmongoproject.components;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TokenStore {

    private Map<String, MongoTemplate> tokens = new ConcurrentHashMap<>();

    public void storeToken(String token, MongoTemplate mongoTemplate) {
        tokens.put(token, mongoTemplate);
    }

    public MongoTemplate getMongoTemplate(String token) {
        return tokens.get(token);
    }

    public void removeToken(String token) {
        tokens.remove(token);
    }

}
