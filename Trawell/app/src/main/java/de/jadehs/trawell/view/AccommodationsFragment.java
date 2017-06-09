package de.jadehs.trawell.view;

import android.location.Location;
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
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.common.api.GoogleApiActivity;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.woxthebox.draglistview.DragListView;

import java.util.ArrayList;
import java.util.List;

import de.jadehs.trawell.R;
import de.jadehs.trawell.api.GooglePlaces;
import de.jadehs.trawell.graph.Place;
import de.jadehs.trawell.models.ItemAdapter;

import static de.jadehs.trawell.view.NewTourActivity.cities;
import static de.jadehs.trawell.view.NewTourActivity.tour;
// Schlüssel der API AIzaSyA1jEeZR3rlEoUzVPYrcPsofCLGXETFwgo

// z.B https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=32.800870,-96.830803&radius=400&type=lodging&key=AIzaSyA1jEeZR3rlEoUzVPYrcPsofCLGXETFwgo
//                                                                  // lon und lat                  radius   typ          key
public class AccommodationsFragment extends Fragment  {


    Button ready;
    MapView mapView;
    GoogleMap googleMap;
    ArrayList city;
    ListView listView;
    ArrayAdapter<String> adapter;
    private static final int GOOGLE_API_CLIENT_ID = 0;
    private static ArrayList<Pair<Long, String>> mItemArray;
    private static ItemAdapter listAdapter;
    public static int aktuelleLocationID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Choose your accommodation");

        View view = inflater.inflate(R.layout.fragment_accommodations, container, false);

        // List View einladen
        listView = (ListView) view.findViewById(R.id.listView);


        // Listet alle cities auf
        mItemArray = new ArrayList<>();
        for(int i = 0; i < tour.getCities().size(); i++){
            String city = tour.getCities().get(i).getLocation().toString();
            mItemArray.add(new Pair<>((long) i, city ));
        }

        listAdapter = new ItemAdapter(mItemArray, R.layout.list_item, R.id.item_layout, false);
        listView.setAdapter((ListAdapter) listAdapter);
        listView.setHorizontalScrollBarEnabled(true);
        listView.setVerticalScrollBarEnabled(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Setzt die aktuelle Location auf die Location die angeklickt wurde
                aktuelleLocationID = position;

                try {
                    MainActivity.goTo(accomodationsCity.class );
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (java.lang.InstantiationException e) {
                    e.printStackTrace();
                }


            }
        });

        ready.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    MainActivity.goTo(HomeFragment.class);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (java.lang.InstantiationException e) {
                    e.printStackTrace();
                }
            }
        });












        return view;
    }








}