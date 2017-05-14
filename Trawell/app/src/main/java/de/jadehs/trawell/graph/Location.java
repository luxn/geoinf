package de.jadehs.trawell.graph;

import java.util.ArrayList;

/**
 * Created by luxn on 09.05.2017.
 */

public class Location {
    protected ArrayList<Route> routes;

    private String name;
    private String country;
    private Coordinate position;

    public Location(String name, String country, Coordinate position) {
        this.name = name;
        this.country = country;
        this.position = position;
        this.routes = new ArrayList<>();
    }
    
    public Location(String name, String country, double lat, double lon) {
        this(name, country, new Coordinate(lat, lon));       
    }
    
    public void addRoute(Route r) {
    	this.routes.add(r);
    }
    
    public Coordinate getPosition() {
    	return position;
    }
    
    public double getLatitude() {
    	return position.getLatitude();
    }
    
    public double getLongitude() {
    	return position.getLongitude();
    }

    @Override
    public String toString() {
        return this.name;
    }
}
