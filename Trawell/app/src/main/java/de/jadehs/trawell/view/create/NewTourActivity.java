package de.jadehs.trawell.view.create;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import de.jadehs.trawell.R;
import de.jadehs.trawell.models.Tour;
import de.jadehs.trawell.graph.TrawellGraph;

public class NewTourActivity extends AppCompatActivity {

    public static int newTourId;
    public static Tour tour;
    public static TrawellGraph graph;
    public static FragmentManager fragmentManager;
    public static ArrayList<String> cities = new ArrayList<>();

    public static <T extends Fragment> void goTo(Class<T> tClass) throws IllegalAccessException, InstantiationException {
        T fragment = tClass.newInstance();
        NewTourActivity.fragmentManager.beginTransaction().replace(
                R.id.newTourContainer, fragment).addToBackStack(fragment.getTag()).commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_tour);

        graph = TrawellGraph.get(getApplicationContext());
        // new instance of a tour (just temporary)
        tour = new Tour();

        NewTourActivity.fragmentManager = getSupportFragmentManager();
        Fragment specifyTravelfragment = new SpecifyTravelFragment();
        NewTourActivity.fragmentManager.beginTransaction().add(
                R.id.newTourContainer, specifyTravelfragment).commit();
    }
}
