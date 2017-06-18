package de.jadehs.trawell.graph;

import java.util.ArrayList;

/**
 * Created by luxn on 09.05.2017.
 */

public class Location implements Comparable<Location> {
    protected ArrayList<Route> routes;

    private String name;
    private String country;
    private Coordinate position;
    private String googleId;

    
    //Package scoped f�r Dijkstra
	int distance;
	boolean seen;
	Location previousLocation;
	Trip previousTrip;
	DayTime time;
	//---

	

    public Location(String name, String country, Coordinate position, String googleId) {
        this.name = name;
        this.country = country;
        this.position = position;
        this.routes = new ArrayList<>();
        this.googleId = googleId;
    }
    
    public Location(String name, String country, double lat, double lon, String googleId) {
        this(name, country, new Coordinate(lat, lon), googleId);
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

    public String getGoogleId() {
        return googleId;
    }

    @Override
    public String toString() {
        return this.name;
    }

	@Override
	public int compareTo(Location o) {		
		return this.name.compareTo(o.name);
	}
}
