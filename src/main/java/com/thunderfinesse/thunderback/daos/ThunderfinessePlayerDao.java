package com.thunderfinesse.thunderback.daos;

import com.thunderfinesse.thunderback.mongodb.configurations.ThunderfinessePlayerMongoDB;
import com.thunderfinesse.thunderback.subprograms.executors.ThunderSkillPlayerExecutor;

import java.util.Set;

//TODO: when updating should calculate overall stats and insert them to appropriate document
public class ThunderfinessePlayerDao {

    private final ThunderfinessePlayerMongoDB thunderfinessePlayerMongoDB;
    private final String collectionNameTemplate = "%login%";


    public ThunderfinessePlayerDao(){
        thunderfinessePlayerMongoDB = ThunderfinessePlayerMongoDB.getInstance();
    }

    public boolean checkAndUpdate(String login) {
        if (doesDbContainPlayer(login)){
            //for now - do not update if Player is in database
            //TODO: update if wasn't updated within last 24h
            return true;
        }else {
            return update(login);
        }
    }

    private boolean update(String login){
        new ThunderSkillPlayerExecutor().executeWithUpdate(login);
        thunderfinessePlayerMongoDB.createCollection(getCollectionName(login));
        return true;
    }

    private boolean doesDbContainPlayer(String login) {
        Set<String> names = thunderfinessePlayerMongoDB.getCollectionNames();
        return names.contains(getCollectionName(login));
    }

    public String getCollectionName(String login) {
        return this.collectionNameTemplate.replace("%login%",login);
    }
}
