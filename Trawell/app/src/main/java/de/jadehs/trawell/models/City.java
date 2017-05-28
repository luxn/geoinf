package de.jadehs.trawell.models;

import de.jadehs.trawell.graph.Location;

/**
 * Created by Christopher on 28.05.2017.
 */

public class City {

    private Location location;
    private int duration;
    private String accommodation;

    public City(Location location, int duration){
        this.location = location;
        this.duration = duration;
    }

    public Location getLocation() {
        return location;
    }

    public int getDuration(){
        return duration;
    }

    public void setDuration(int duration){
        this.duration = duration;
    }

}
