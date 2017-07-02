package de.jadehs.trawell.view.create;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.orm.util.Collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.jadehs.trawell.R;
import de.jadehs.trawell.models.City;
import de.jadehs.trawell.models.Tour;
import de.jadehs.trawell.graph.TrawellGraph;

public class NewTourActivity extends AppCompatActivity {

    private static Long tourId;
    private static Long cityId;
    private static int ticketId;
    private static Tour tour;
    private static TrawellGraph graph;
    private static FragmentManager fragmentManager;
    private static Context context;
    private static View view;
    private static ArrayList<City> cities;

    public static <T extends Fragment> void goTo(Class<T> tClass) throws IllegalAccessException, InstantiationException {
        T fragment = tClass.newInstance();
        NewTourActivity.fragmentManager.beginTransaction().replace(
                R.id.newTourContainer, fragment).addToBackStack(fragment.getTag()).commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_tour);
        cities = new ArrayList<>();
        graph = TrawellGraph.get(getApplicationContext());
        // new instance of a tour (just temporary)
        tour = new Tour();

        context = this;

        NewTourActivity.fragmentManager = getSupportFragmentManager();
        Fragment chooseTicketFragment = new ChooseTicketFragment();
        NewTourActivity.fragmentManager.beginTransaction().add(
                R.id.newTourContainer, chooseTicketFragment).commit();
    }

    public static Long getTourId() {
        return tourId;
    }

    public static void setTourId(Long tourId) {
        NewTourActivity.tourId = tourId;
    }

    public static Long getCityId() {
        return cityId;
    }

    public static void setCityId(Long cityId) {
        NewTourActivity.cityId = cityId;
    }

    public static int getTicketId() {
        return ticketId;
    }

    public static void setTicketId(int ticketId) {
        NewTourActivity.ticketId = ticketId;
    }

    public static Tour getTour() {
        return tour;
    }

    public static void setTour(Tour tour) {
        NewTourActivity.tour = tour;
    }

    public static TrawellGraph getGraph() {
        return graph;
    }

    public static void setGraph(TrawellGraph graph) {
        NewTourActivity.graph = graph;
    }

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        NewTourActivity.context = context;
    }

    public static View getView() {
        return view;
    }

    public static void setView(View view) {
        NewTourActivity.view = view;
    }

    public static ArrayList<City> getCities() {
        return cities;
    }

    public static void setCities(ArrayList<City> cities) {
        NewTourActivity.cities = cities;
    }
}
