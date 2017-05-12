package de.jadehs.trawell.graph;


/**
 * Created by Lux on 09.05.2017.
 */

public class DayTime {

    public enum AmPm {
        AM,
        PM
    }

    private int hours;
    private int minutes;

    private AmPm amPm;

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
}
