package de.jadehs.trawell.database;

import com.orm.SugarRecord;

import java.util.Date;

/**
 * Created by David on 06.06.2017.
 */


public class DBTour extends SugarRecord<DBTour> {

    private String startCity, finalCity;
    private Date start, end;
    private int duration;

    public DBTour(){
    }

//    public DBTour (Tour tour) {
//        this.startCity = tour.getStartCity();
//        this.finalCity = tour.getFinalCity();
//        this.start = tour.getStart();
//        this.end = tour.getEnd();
//        this.duration = tour.getDuration();
//        for (int i = 0; i < tour.getCities().size(); i++) {
//            new DBCity(tour.getCities().get(i).toString(), this);
//        }
//    }

    /*public DBTour(String startCity, String finalCity, Date start, Date end, int duration){
        this.startCity = startCity;
        this.finalCity = finalCity;
        this.start = start;
        this.end = end;
        this.duration = duration;
    }*/
    public void setStartCity(String startCity) {
        this.startCity = startCity;
    }

    public void setFinalCity(String finalCity) {
        this.finalCity = finalCity;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getStartCity() {
        return startCity;
    }

    public String getFinalCity() {
        return finalCity;
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }

    public int getDuration() {
        return duration;
    }

//    public ArrayList<String> getCityNames() {
//        ArrayList<String> cities = new ArrayList<>();
//        List<DBCity> dbcities = DBCity.find(DBCity.class, "dbtour = "+this, this.getId().toString());
//        for (int i = 0; i < dbcities.size(); i++) {
//            cities.add(dbcities.get(i).getName());
//        }
//        return cities;
//    }
}


