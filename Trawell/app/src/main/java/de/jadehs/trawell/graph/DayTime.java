package de.jadehs.trawell.graph;


/**
 * Created by luxn on 09.05.2017.
 */

public class DayTime {

    private int hours;
    private int minutes;


    public DayTime(String s) {
        String[] splitted = s.split(":");
        int[] time = new int[] {Integer.parseInt(splitted[0]), Integer.parseInt(splitted[1])};

        this.hours = time[0];
        this.minutes = time[1];
    }

    public DayTime(int hours, int minutes) {
        this.hours = hours;
        this.minutes = minutes;
     
    }


	public int getHours() {
		return hours;
	}

	void setHours(int hours) {
		this.hours = hours;
	}

	public int getMinutes() {
		return minutes;
	}

	void setMinutes(int minutes) {
		this.minutes = minutes;
	}


	public Duration substract(DayTime time) {		
		return new Duration(time, this);
	}
	
	@Override
	public String toString() {
		return String.format("%d:%02d", this.hours, this.minutes);
	}
    
    
}
