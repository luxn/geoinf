package de.jadehs.trawell.database;

import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.Date;

import de.jadehs.trawell.models.City;

/**
 * Created by David on 06.06.2017.
 */


public class DBTour extends SugarRecord<DBTour> {
    private String startCity, finalCity;
    private Date start, end;
    private int duration;
    private ArrayList<City> cities;

    public DBTour(){
    }

    public DBTour(String startCity, String finalCity, Date start, Date end, int duration, ArrayList<City> cities){
        this.startCity = startCity;
        this.finalCity = finalCity;
        this.start = start;
        this.end = end;
        this.duration = duration;
        this.cities = cities;
    }
}


