package de.jadehs.trawell.view;

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

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;

import java.util.ArrayList;
import java.util.List;

import de.jadehs.trawell.R;
//import de.jadehs.trawell.api.GooglePlaces;
import de.jadehs.trawell.database.DBCity;
import de.jadehs.trawell.database.DBTour;
import de.jadehs.trawell.models.ItemAdapter;
import de.jadehs.trawell.models.TourArrayAdapter;

import static de.jadehs.trawell.view.NewTourActivity.newTourId;
import static de.jadehs.trawell.view.TourActivity.exTourId;
//import static de.jadehs.trawell.view.TourActivity.tourId;
// Schlüssel der API AIzaSyA1jEeZR3rlEoUzVPYrcPsofCLGXETFwgo

// z.B https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=32.800870,-96.830803&radius=400&type=lodging&key=AIzaSyA1jEeZR3rlEoUzVPYrcPsofCLGXETFwgo
//                                                                  // lon und lat                  radius   typ          key
public class AccommodationsFragment extends Fragment  {

    private int tourId;
    private ListView listView;
    private TourArrayAdapter listViewAdapter;

    Button ready;
    MapView mapView;
    GoogleMap googleMap;
    ArrayList city;
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

//        Log.d("getActivity", ""+getActivity().getLocalClassName());
        if(getActivity().getClass().equals(TourActivity.class)){
            tourId = exTourId;
//            Log.d("exTourId", ""+tourId);
        } else if (getActivity().getClass().equals(NewTourActivity.class)){
            tourId = newTourId;
//            Log.d("newTourId", ""+tourId);
        }

        DBTour tour = DBTour.findById(DBTour.class, new Long(tourId));
        List<DBCity> city;
        city = DBTour.find(DBCity.class, "TOUR_ID =" + tour.getId());

        // List View einladen
        listView = (ListView) view.findViewById(R.id.accoListView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        listViewAdapter = new TourArrayAdapter(getContext(), R.layout.tour_item,(ArrayList) city, DBCity.class);
        listView.setAdapter(listViewAdapter);
//        listViewAdapter = new ArrayAdapter<DBCity>();

//        // Listet alle cities auf
//        mItemArray = new ArrayList<>();
//        for(int i = 0; i < tour.getCities().size(); i++){
//            String city = tour.getCities().get(i).getLocation().toString();
//            mItemArray.add(new Pair<>((long) i, city ));
//        }
//
//        listAdapter = new ItemAdapter(mItemArray, R.layout.list_item, R.id.item_layout, false);
//        listView.setAdapter((ListAdapter) listAdapter);
//        listView.setHorizontalScrollBarEnabled(true);
//        listView.setVerticalScrollBarEnabled(true);
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                // Setzt die aktuelle Location auf die Location die angeklickt wurde
//                aktuelleLocationID = position;
//
//                try {
//                    MainActivity.goTo(accomodationsCity.class );
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                } catch (InstantiationException e) {
//                    e.printStackTrace();
//                } catch (java.lang.InstantiationException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        ready.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//                    MainActivity.goTo(HomeFragment.class);
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                } catch (InstantiationException e) {
//                    e.printStackTrace();
//                } catch (java.lang.InstantiationException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
        return view;
    }








}