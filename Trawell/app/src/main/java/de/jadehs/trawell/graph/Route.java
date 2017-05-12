package de.jadehs.trawell.graph;

import android.icu.util.DateInterval;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by Lux on 09.05.2017.
 */

public class Route implements Comparable<Route> {

    private Location origin, destination;
    private List<Trip> trips;
    private String name;
    private String provider;
    private double distance;
    private Duration duration;
    private double calculatedWeight = 1.0;

    public Route(String name, String provider, double distance, Duration duration) {
        this.name = name;
        this.provider = provider;
        this.distance = distance;
        this.duration = duration;

        this.trips = new ArrayList<>();
    }


    public void addTrip(Trip t) {
        this.trips.add(t);
    }

    public void addTrips(Collection<Trip> trips) {
        this.trips.addAll(trips);
    }

    public void removeTrip(Trip t) {
        this.trips.remove(t);
    }

    public void setOrigin(Location l) {
        this.origin = l;
    }

    public void setDestination(Location l) {
        this.destination = l;
    }

    public void recalulateWeight() {
        this.calculatedWeight = duration.getDurationInMinutes() / distance;
    }

    public Location getOrigin() {
        return origin;
    }

    public Location getDestination() {
        return destination;
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public double getCalculatedWeight() {
        return calculatedWeight;
    }

    public void setCalculatedWeight(double calculatedWeight) {
        this.calculatedWeight = calculatedWeight;
    }

    @Override
    public int compareTo(@NonNull Route o) {
        return this.name.compareTo(o.name);
    }
}
