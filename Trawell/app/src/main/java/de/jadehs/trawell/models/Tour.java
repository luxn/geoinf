package de.jadehs.trawell.models;

import android.support.v7.util.SortedList;

import java.util.ArrayList;
import java.util.Date;

import de.jadehs.trawell.graph.Location;

/**
 * Created by Christopher on 28.05.2017.
 */

public class Tour {

    private String startCity, finalCity;
    private Date start, end;
    private int duration;
    private ArrayList<City> cities;

    public Tour(){
    }

    public Tour(String startCity, String finalCity, Date start, Date end, int duration){
        cities = new ArrayList<>();
        this.startCity = startCity;
        this.finalCity = finalCity;
        this.start = start;
        this.end = end;
        this.duration = duration;
    }

    public void addCity(City city){
        cities.add(city);
    }

    public ArrayList<City> getCities() {
        return cities;
    }
    public String getStartCity() {
        return startCity;
    }

    public String getFinalCity() {
        return finalCity;
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }

    public int getDuration() {
        return duration;
    }

    public void printTour(){
        for(int i = 0; i < cities.size(); i++){
            System.out.println("city: " + cities.get(i).getLocation().toString() + " duration: " + cities.get(i).getDuration());
        }
    }

}
