package com.thunderfinesse.thunderback.mongodb.configurations;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public enum ThunderskillPlayerMongoDB {
    INSTANCE;

    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;

    private String dbName;
    private String clientURI;

    ThunderskillPlayerMongoDB(){
        setDefaultVals();

        mongoClient = MongoClients.create(clientURI);
        mongoDatabase = mongoClient.getDatabase(dbName);
    }

    private void setDefaultVals(){
        this.clientURI = "mongodb://localhost:27017";
        this.dbName = "tf_thunderskill_players";
    }

    public static ThunderskillPlayerMongoDB getInstance(){
        return INSTANCE;
    }

    public MongoCollection<Document> getCollection(String collectionName) {
        return mongoDatabase.getCollection(collectionName);
    }
}
