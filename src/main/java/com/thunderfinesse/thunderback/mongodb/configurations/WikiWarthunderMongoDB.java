package com.thunderfinesse.thunderback.mongodb.configurations;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public enum WikiWarthunderMongoDB {
    INSTANCE;

    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;

    private String dbName;
    private String clientURI;

    WikiWarthunderMongoDB(){
        setDefaultValues();

        mongoClient = MongoClients.create(clientURI);
        mongoDatabase = mongoClient.getDatabase(dbName);
    }

    private void setDefaultValues(){
        this.clientURI = "mongodb://localhost:27017";
        this.dbName = "tf_wiki";
    }

    public static WikiWarthunderMongoDB getInstance(){
        return INSTANCE;
    }

    public MongoCollection<Document> getCollection(String collectionName) {
        return mongoDatabase.getCollection(collectionName);
    }
}
