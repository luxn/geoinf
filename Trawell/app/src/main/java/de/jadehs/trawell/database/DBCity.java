package de.jadehs.trawell.database;

import com.orm.SugarRecord;

/**
 * Created by David on 06.06.2017.
 */

public class DBCity extends SugarRecord<DBCity> {
    String name;
    DBTour dbtour;


}
