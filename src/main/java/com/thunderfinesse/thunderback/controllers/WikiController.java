package com.thunderfinesse.thunderback.controllers;

import com.thunderfinesse.thunderback.daos.WikiWarthunderDao;
import com.thunderfinesse.thunderback.data.graphs.WikiOverallData;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wiki")
@CrossOrigin(origins = "http://localhost:4200")
public class WikiController {

    @GetMapping("/")
    public WikiOverallData getSummary(){
        WikiOverallData data = getWikiOverallData();
        return data;
    }

    private WikiOverallData getWikiOverallData() {
        WikiWarthunderDao wikiWarthunderDao = new WikiWarthunderDao();


        return null;
    }

}
