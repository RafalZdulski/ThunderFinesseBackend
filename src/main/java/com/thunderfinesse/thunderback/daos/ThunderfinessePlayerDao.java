package com.thunderfinesse.thunderback.daos;

import com.thunderfinesse.thunderback.subprograms.executors.JarExecutor;
import com.thunderfinesse.thunderback.subprograms.executors.JarExecutorException;
import com.thunderfinesse.thunderback.mongodb.configurations.ThunderfinessePlayerMongoDB;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
            try {
                return update(login);
            } catch (JarExecutorException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    private boolean update(String login) throws JarExecutorException {
        //TODO: try tu push update button if failed return false

        // Run a java app in a separate system process
        List<String> args = new ArrayList<>();
        args.add(0,login);
        //args.add("--update"); //TODO: does not work when used form jar file (possible problem with msedgedriver.exe or other components of selenium web driver) - for I wont use this function
        new JarExecutor().executeJar("src/main/resources/subprograms/ThunderSkillPlayer.jar",args);
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
