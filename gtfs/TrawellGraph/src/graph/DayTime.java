package graph;


/**
 * Created by luxn on 09.05.2017.
 */

public class DayTime {



    public enum AmPm {
        AM,
        PM
    }

    private int hours;
    private int minutes;

    private AmPm amPm;

    public DayTime(String s) {
        String[] splitted = s.split(":");
        int[] time = new int[] {Integer.parseInt(splitted[0]), Integer.parseInt(splitted[1])};

        if (time[0] > 11) {
            this.amPm = AmPm.PM;
        } else {
            this.amPm = AmPm.AM;
        }

        this.hours = time[0];
        this.minutes = time[1];
    }

    public DayTime(int hours, int minutes) {
        this.hours = hours;
        this.minutes = minutes;

        if (hours > 11) {
            this.amPm = AmPm.PM;
        } else {
            this.amPm = AmPm.AM;
        }
    }

    public DayTime(int hours, int minutes, AmPm amPm) {
        this.hours = hours;
        this.minutes = minutes;
        this.amPm = amPm;
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

	public AmPm getAmPm() {
		return amPm;
	}

	void setAmPm(AmPm amPm) {
		this.amPm = amPm;
	}

	public Duration substract(DayTime time) {
		// TODO Auto-generated method stub
		return null;
	}
    
    
}
