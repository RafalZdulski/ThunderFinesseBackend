package com.thunderfinesse.thunderback.services;

import com.thunderfinesse.thunderback.daos.WikiWarthunderDao;
import com.thunderfinesse.thunderback.data.VehicleBase;

import java.util.List;

public class WikiService {

    public List<VehicleBase> getAllVehicles() {
        WikiWarthunderDao wikiWarthunderDao = new WikiWarthunderDao();
        return wikiWarthunderDao.getAllVehicles();
    }
}
