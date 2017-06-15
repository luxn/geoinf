package de.jadehs.trawell.models;

import com.orm.SugarRecord;

/**
 * Created by Christopher on 09.06.2017.
 */

public class Accommodation extends SugarRecord {

    private String bewertung;
    private String name;
    private String adresse;
    private String phoneNumber;
    private String url;
    private City city;

    public Accommodation(){

    }


    public Accommodation(String name, String bewertung, String adresse, String phoneNumber, String url, City city){
        this.name = name;
        this.bewertung = bewertung;
        this.adresse = adresse;
        this.phoneNumber = phoneNumber;
        this.url = url;
        this.city = city;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getBewertung(){
        return bewertung;
    }
    public void setBewertung(String bewertung){
        this.bewertung = bewertung;
    }

    public String getAdresse(){
        return adresse;
    }
    public void setAdresse(String adresse){
        this.adresse = adresse;
    }

}
