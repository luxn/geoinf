package de.jadehs.trawell.models;

import com.orm.SugarRecord;

import java.util.ArrayList;

/**
 * Created by David on 06.06.2017.
 */

public class City extends SugarRecord {
    private String name;
    private int duration;
    private Tour tour;

    public City() {
    }

    public City(String name, Tour tour, int duration) {
        this.name = name;
        this.tour = tour;
        this.duration = duration;
    }

    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
