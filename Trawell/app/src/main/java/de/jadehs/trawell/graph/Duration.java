package de.jadehs.trawell.graph;

/**
 * Created by luxn on 12.05.2017.
 */

public class Duration {
    private int hours, minutes;

    public Duration(double hours) {
    	this.hours = (int) hours;
    	this.minutes = (int) ((hours - Math.rint(hours)) * 60);
    }

    public Duration(int hours, int minutes) {
    	this.hours = hours;
    	this.minutes = minutes;
    }

    public int getDurationInMinutes() {
        return hours * 60 + minutes;
    }
}
