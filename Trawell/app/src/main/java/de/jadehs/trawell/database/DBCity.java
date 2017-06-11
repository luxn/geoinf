package de.jadehs.trawell.database;

import com.orm.SugarRecord;

/**
 * Created by David on 06.06.2017.
 */

public class DBCity extends SugarRecord<DBCity> {
    private Long tourId;
    private String name;
    private int duration;

    public DBCity() {
    }

    public DBCity(String name, Long id) {
        this.name = name;
        this.tourId = id;
    }
    public Long getTourId() {
        return tourId;
    }

    public void setTourId(Long tourId) {
        this.tourId = tourId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
