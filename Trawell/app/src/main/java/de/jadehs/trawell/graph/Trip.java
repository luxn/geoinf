package de.jadehs.trawell.graph;

import java.util.List;

/**
 * Created by luxn on 09.05.2017.
 */

public class Trip {


    String tripName;
    Route route;
    
    DayTime startTime;
    DayTime endTime;

    public Trip(String tripName, Route route, DayTime startTime, DayTime endTime) {
    	this.tripName = tripName;
        this.route = route;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public DayTime getStartTime() {
        return startTime;
    }

    public void setStartTime(DayTime startTime) {
        this.startTime = startTime;
    }

    public DayTime getEndTime() {
        return endTime;
    }

    public void setEndTime(DayTime endTime) {
        this.endTime = endTime;
    }
}
