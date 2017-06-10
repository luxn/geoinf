package de.jadehs.trawell.models;

import android.location.Location;

/**
 * Created by Lisa Haltermann on 06.06.2017.
 */

public class Accomodation {

    private Location location;
    private double bewertung;
    private String name;
    private String adresse;

    public Accomodation(String name, double bewertung, String adresse){
      //  this.location = location;
        this.name = name;
        this.bewertung = bewertung;
        this.adresse = adresse;
    }

 /*   public Location getLocation() {
        return location;
    }
    public void setLocation(Location location){
        this.location = location;
    } */

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public double getBewertung(){
        return bewertung;
    }
    public void setBewertung(int bewertung){
        this.bewertung = bewertung;
    }

    public String getAdresse(){
        return adresse;
    }
    public void setAdresse(String adresse){
        this.adresse = adresse;
    }

}
