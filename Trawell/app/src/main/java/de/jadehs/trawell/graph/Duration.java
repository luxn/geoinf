package de.jadehs.trawell.graph;

/**
 * Created by luxn on 12.05.2017.
 */

public class Duration {
    private int hours, minutes;

    Duration(double hours) {
    	this.hours = (int) hours;    
    	this.minutes = (int) ((hours - Math.floor(hours)) * 60);    	
    }

    Duration(int hours, int minutes) {
    	this.hours = hours;
    	if (minutes > 60) {
    		this.minutes = minutes % 60;
    		this.hours += minutes / 60;
    	} else {
    		this.minutes = minutes;
    	}
    }

    Duration(DayTime startTime, DayTime endTime) {
    	int h = 0, m = 0;
    	
    	if (startTime.getMinutes() > endTime.getMinutes()) {
    		if (startTime.getHours() > endTime.getHours())  {
    			h = 24 - startTime.getHours() + endTime.getHours();
    		} else {
    			h = endTime.getHours() - startTime.getHours();
    		}
    		h--;
    	} else {
    		if (startTime.getHours() > endTime.getHours())  {
    			h = 24 - startTime.getHours() + endTime.getHours();
    		} else {
    			h = endTime.getHours() - startTime.getHours();
    		}
    	}
    	
    	
    	if (startTime.getMinutes() > endTime.getMinutes()) {
    		m = 60 - startTime.getMinutes() + endTime.getMinutes();    		
    	} else {    		
    		m = endTime.getMinutes() - startTime.getMinutes();
    	}
    	
    	if (h >= 24) {
    		h -= 24;
    	}
    	
    	if (h < 0) {
    		h = 24-h;
    	}
    	
    	this.hours = h;
    	this.minutes = m;   	
    	
    	if (hours < 0 ) {
    		System.err.println();
    	}
    	
	}

	public int getDurationInMinutes() {
        return hours * 60 + minutes;
    }
	
	public double getDurationInHoursFloating() {
		return (double) hours + (double) minutes / 60.0;
	}

	public boolean positive() {
		return minutes > 0 && hours > 0;
	}
	
	@Override
	public String toString() {
		return getDurationInMinutes() + "min";
	}
}
