package de.jadehs.trawell.graph;

/**
 * Created by Lux on 12.05.2017.
 */

public class Duration {
    private int hours, minutes;

    public Duration(double hours) {

    }

    public Duration(int hours, int minutes) {

    }

    public int getDurationInMinutes() {
        return hours * 60 + minutes;
    }
}
