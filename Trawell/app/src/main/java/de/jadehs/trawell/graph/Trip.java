package de.jadehs.trawell.graph;

import java.util.List;

/**
 * Created by luxn on 09.05.2017.
 */

public class Trip {


    List<Stop> stops;

    String tripName;
    
    DayTime startTime;
    DayTime endTime;

    public Trip(String tripName, DayTime startTime, DayTime endTime) {
    	this.tripName = tripName;
        this.startTime = startTime;
        this.endTime = endTime;
    }

}
