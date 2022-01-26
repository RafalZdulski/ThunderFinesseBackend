package com.thunderfinesse.thunderback.subprograms.executors;

import com.thunderfinesse.thunderback.mongodb.configurations.ThunderskillPlayerMongoDB;

import java.util.ArrayList;
import java.util.List;

public class ThunderSkillPlayerExecutor {

    public int executeWithUpdate(String login){
        List<String> args = new ArrayList<>();
        args.add(0,login); //login must be first arg

        //TODO: (problem to resolve) for some reason ClassLoader has problem with:
        //Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception [Handler dispatch failed; nested exception is java.lang.NoClassDefFoundError: org/openqa/selenium/internal/FindsById] with root cause
        //args.add("--update");
        
        String dburi = ThunderskillPlayerMongoDB.getInstance().getClientURI();
        args.add(dburi);
        String dbname = ThunderskillPlayerMongoDB.getInstance().getDbName();
        args.add(dbname);

        //running ThunderSkillPlayer subprogram
        //TODO: (problem to resolve) what if i search for player that does not exist?
        com.thunderfinesse.thunderskillfetcher.player.Main.main(args.toArray(new String[0]));
        return 0;
    }
}
