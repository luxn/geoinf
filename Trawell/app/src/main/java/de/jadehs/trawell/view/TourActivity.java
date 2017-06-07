package de.jadehs.trawell.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import de.jadehs.trawell.R;
import de.jadehs.trawell.database.DBTour;

public class TourActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour);

        Intent intent = getIntent();
        int itemPosition = intent.getIntExtra("itemPosition", -1);
        DBTour tour = DBTour.findById(DBTour.class, new Long(itemPosition + 1));

        this.setTitle(""+tour.getStartCity() + " - " + tour.getFinalCity());


    }
}
