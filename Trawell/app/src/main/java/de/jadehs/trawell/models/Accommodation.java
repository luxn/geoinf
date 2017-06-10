package de.jadehs.trawell.models;

import com.orm.SugarRecord;

/**
 * Created by Christopher on 09.06.2017.
 */

public class Accommodation extends SugarRecord {

    private Long cityId;
    private String bewertung;
    private String name;
    private String adresse;

    public Accommodation(){

    }


    public Accommodation(Long id, String name, String bewertung, String adresse){
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
