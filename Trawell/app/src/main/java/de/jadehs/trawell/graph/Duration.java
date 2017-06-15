package de.jadehs.trawell.graph;

/**
 * Created by luxn on 12.05.2017.
 */

public class Duration {
    private int hours, minutes;

    Duration(double hours) {
    	this.hours = (int) hours;
    	this.minutes = (int) ((hours - Math.rint(hours)) * 60);
    }

    Duration(int hours, int minutes) {
    	this.hours = hours;
    	this.minutes = minutes;
    }

    Duration(DayTime startTime, DayTime endTime) {
		this.hours = endTime.getHours() - startTime.getHours();
		this.minutes = endTime.getMinutes() - startTime.getMinutes();
	}

	public int getDurationInMinutes() {
        return hours * 60 + minutes;
    }
	
	public double getDurationInHoursFloating() {
		return (double) hours + (double) minutes / 60;
	}
}
