package de.jadehs.trawell.graph;

import java.util.ArrayList;

/**
 * Created by Lux on 09.05.2017.
 */

public class Location {
    protected ArrayList<Route> routes;

    private String name;
    private String country;
    private Coordinate position;

    public  Location(String name, String country, Coordinate position) {
        this.name = name;
        this.country = country;
        this.position = position;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
