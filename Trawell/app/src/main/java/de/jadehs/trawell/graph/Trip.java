package de.jadehs.trawell.graph;

import java.sql.Time;
import java.util.Date;
import java.util.List;

/**
 * Created by Lux on 09.05.2017.
 */

public class Trip {

    public enum DayOfWeek {
        MONDAY,
        TUESDAY,
        WEDNESDAY,
        THURSDAY,
        FRIDAY,
        SATURDAY,
        SUNDAY,
        DAILY
    }

    List<Stop> stops;

    DayTime startTime;
    DayTime endTime;
    DayOfWeek day;

    public Trip(DayTime startTime, DayTime endTime, DayOfWeek day) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.day = day;
    }

}
