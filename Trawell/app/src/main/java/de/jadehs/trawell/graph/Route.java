package de.jadehs.trawell.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by luxn on 09.05.2017.
 */

public class Route implements Comparable<Route> {

    private Location source, destination;
    private List<Trip> trips;
    private String name;
    private double distance;
    private Duration duration;
    private int calculatedWeight = 1;  
    
    public Route(String name, Location from, Location to) {
    	this.name = name;
        this.trips = new ArrayList<>();
    	this.source = from;
    	this.destination = to;
    	
    	this.distance = getDistanceFromLatLonInKm(
    			from.getLatitude(), 
    			from.getLongitude(), 
    			to.getLatitude(), 
    			to.getLongitude()
    		);
    			
    	this.duration = new Duration(distance / 220.0 ); // a typical Train will be at 220kmh
    	recalulateWeight();    	
    }
       
    
    private double getDistanceFromLatLonInKm(double lat1, double lon1, double lat2, double lon2) {
    	  final double r = 6371; // Radius of the earth in km
    	  double dLat = deg2rad(lat2-lat1);  // deg2rad
    	  double dLon = deg2rad(lon2-lon1); 
    	  double a = 
    	    Math.sin(dLat/2) * Math.sin(dLat/2) +
    	    Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * 
    	    Math.sin(dLon/2) * Math.sin(dLon/2)
    	    ; 
    	  double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
    	  return r * c; // Distance in km    	  
    }

    private double deg2rad(double deg) {
    	 return deg * (Math.PI/180);
    }


    public void addTrip(Trip t) {
        this.trips.add(t);
    }

    public void addTrips(Collection<Trip> trips) {
        this.trips.addAll(trips);
    }

    public void removeTrip(Trip t) {
        this.trips.remove(t);
    }
   

    public void recalulateWeight() {
        this.calculatedWeight = (int) ((duration.getDurationInMinutes() / distance) * 10_000);
    }

    public Location getSource() {
        return source;
    }

    public Location getDestination() {
        return destination;
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public int getWeight() {
        return calculatedWeight;
    }
   

    @Override
    public int compareTo(Route o) {
        return this.name.compareTo(o.name);
    }
}
