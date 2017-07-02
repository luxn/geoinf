package de.jadehs.trawell.view.create;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.maps.MapView;

import java.util.ArrayList;
import java.util.List;

import de.jadehs.trawell.R;
//import de.jadehs.trawell.api.GooglePlaces;
import de.jadehs.trawell.models.Accommodation;
import de.jadehs.trawell.models.City;
import de.jadehs.trawell.models.Tour;
import de.jadehs.trawell.miscellaneous.ItemAdapter;
import de.jadehs.trawell.miscellaneous.TrawellArrayAdapter;
import de.jadehs.trawell.view.home.MainActivity;

public class AccommodationsFragment extends Fragment  {

    private Long tourId;
    private ListView listView;
    private TrawellArrayAdapter listViewAdapter;
    private List<City> cities;
    private Button readyBTN;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Choose lodgings for your destinations");

        View view = inflater.inflate(R.layout.fragment_accommodations, container, false);

        if(getActivity().getClass().equals(MainActivity.class)){
            tourId = MainActivity.getTourId();
        } else if (getActivity().getClass().equals(NewTourActivity.class)){
            tourId = NewTourActivity.getTourId();
        }

        readyBTN = (Button) view.findViewById(R.id.readyBTN);
        readyBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        cities = Tour.find(City.class, "TOUR = ?", String.valueOf(tourId));

        // List View einladen
        listView = (ListView) view.findViewById(R.id.accoListView);

//        if(acco.size() < cities.size()) {
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        if (getActivity().getClass().equals(MainActivity.class)) {
                            MainActivity.setCityId(cities.get(position).getId());
                            MainActivity.goTo(ChooseAccommodationFragment.class);
                        } else if (getActivity().getClass().equals(NewTourActivity.class)) {
                            NewTourActivity.setCityId(cities.get(position).getId());
                            NewTourActivity.goTo(ChooseAccommodationFragment.class);
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (java.lang.InstantiationException e) {
                        e.printStackTrace();
                    }
                }
            });
//        }
        listViewAdapter = new TrawellArrayAdapter(getContext(), R.layout.tour_item,(ArrayList) cities, City.class);
        listView.setAdapter(listViewAdapter);

        return view;
    }




}