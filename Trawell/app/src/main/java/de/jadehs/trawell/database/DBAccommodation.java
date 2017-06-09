package de.jadehs.trawell.database;

import android.location.Location;

import com.orm.SugarRecord;

/**
 * Created by Christopher on 09.06.2017.
 */

public class DBAccommodation extends SugarRecord<DBAccommodation> {

    private Long cityId;
    private int bewertung;
    private String name;
    private String adresse;

    public DBAccommodation(){

    }
    public DBAccommodation(Long id, String name, int bewertung, String adresse){
        this.cityId = id;
        this.name = name;
        this.bewertung = bewertung;
        this.adresse = adresse;
    }

    public Long getCityId(){
        return cityId;
    }
    public void setCityId(Long id){
        this.cityId = id;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public int getBewertung(){
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
