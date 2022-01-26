package com.thunderfinesse.thunderback.mongodb.configurations;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import java.util.*;

public enum ThunderfinessePlayerMongoDB {
    INSTANCE;

    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;

    private String dbName;
    private String clientURI;

    ThunderfinessePlayerMongoDB(){
        setDefaultVals();

        mongoClient = MongoClients.create(clientURI);
        mongoDatabase = mongoClient.getDatabase(dbName);
    }

    private void setDefaultVals(){
        this.clientURI = "mongodb://localhost:27017";
        this.dbName = "tf_thunderfinesse_players";
    }

    public static ThunderfinessePlayerMongoDB getInstance(){
        return INSTANCE;
    }


    public Set<String> getCollectionNames() {
        Set<String> ret = new HashSet<>();
        Iterable<String> iterable =  mongoDatabase.listCollectionNames();
        Iterator<String> iterator = iterable.iterator();
        iterator.forEachRemaining(ret::add);
        return ret;
    }

    public void createCollection(String collectionName) {
        mongoDatabase.createCollection(collectionName);
    }
}
