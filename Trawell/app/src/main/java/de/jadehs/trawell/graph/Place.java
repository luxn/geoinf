package de.jadehs.trawell.graph;

/**
 * Created by Lisa Haltermann on 24.05.2017.
 */


public class Place {

    private String latitude, longitude;
    private String name;

    public Place(String lat, String lon, String name) {
        this.latitude = lat;
        this.longitude = lon;
        this.name = name;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}