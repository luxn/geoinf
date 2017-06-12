package de.jadehs.trawell.view.tours;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import de.jadehs.trawell.R;

public class TourActivity extends AppCompatActivity {

    //existing tour id
    public static int exTourId;
    public static FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour);

        Intent intent = getIntent();
        exTourId = intent.getIntExtra("tourId", -1);

//        Log.d("exTourId-Activity", ""+exTourId);

        TourActivity.fragmentManager = getSupportFragmentManager();
        Fragment myFragment = new TourDetailFragment();
        TourActivity.fragmentManager.beginTransaction().add(
                R.id.tourActivityContainer, myFragment).commit();


    }
    public static <T extends Fragment> void goTo(Class<T> tClass) throws IllegalAccessException, InstantiationException {
        T fragment = tClass.newInstance();
        TourActivity.fragmentManager.beginTransaction().replace(
                R.id.tourActivityContainer, fragment).addToBackStack(fragment.getTag()).commit();
    }
}
