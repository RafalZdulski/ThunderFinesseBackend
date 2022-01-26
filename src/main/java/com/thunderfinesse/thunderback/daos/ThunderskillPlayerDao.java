package com.thunderfinesse.thunderback.daos;

import com.mongodb.client.MongoCollection;
import com.thunderfinesse.thunderback.data.enums.Mode;
import com.thunderfinesse.thunderback.data.enums.VehicleType;
import com.thunderfinesse.thunderback.mongodb.configurations.ThunderskillPlayerMongoDB;
import org.bson.Document;

public class ThunderskillPlayerDao{

    private final ThunderskillPlayerMongoDB thunderskillPlayerMongoDB;
    private final String collectionNameTemplate = "%login%_%type%_%mode%";

    public ThunderskillPlayerDao(){
        thunderskillPlayerMongoDB = ThunderskillPlayerMongoDB.getInstance();
    }

    public MongoCollection<Document> getCollection(String login, VehicleType type, Mode mode) {
        String collectionName = getCollectionName(login,type,mode);
        return thunderskillPlayerMongoDB.getCollection(collectionName);
    }

    public String getCollectionName(String login, VehicleType type, Mode mode) {
        return this.collectionNameTemplate.replace("%login%",login)
                .replace("%type%",type.getAbbrev())
                .replace("%mode%",mode.getAbbrev());
    }
}
