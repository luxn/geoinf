package de.jadehs.trawell.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import de.jadehs.trawell.R;
import de.jadehs.trawell.api.GooglePlaces;
import de.jadehs.trawell.api.OnTaskCompletedListener;
import de.jadehs.trawell.api.Weather;
import de.jadehs.trawell.graph.Place;

import static de.jadehs.trawell.view.NewTourActivity.locations;
// Schlüssel der API AIzaSyA1jEeZR3rlEoUzVPYrcPsofCLGXETFwgo

// z.B https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=32.800870,-96.830803&radius=400&type=lodging&key=AIzaSyA1jEeZR3rlEoUzVPYrcPsofCLGXETFwgo
//                                                                  // lon und lat                  radius   typ          key
public class AccommodationsFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {


    Button ready;
    MapView mapView;
    GoogleMap googleMap;


    ListView citiesListView;
    ArrayList<String> cities;
    ArrayAdapter<String> adapter;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Choose your accommodation");

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        citiesListView = (ListView) view.findViewById(R.id.citiesListView);
        cities = new ArrayList<>();
        adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,cities);
        citiesListView.setAdapter(adapter);
        ready = (Button) view.findViewById(R.id.ReadyBTN);

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

        mapView = (MapView) view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        // housing BTN noch nicht initialisiert

        // Inflate the layout for this fragment
        return view;
    }


    // Zeigt die allgemeinen ausgewählten Städte an
    @Override
    public void onMapReady(GoogleMap googleM) {
        googleMap = googleM;

        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.setOnMarkerClickListener(this);

        for(int i = 0; i < locations.size(); i++){
            Log.d("location", "" + locations.get(i));
            String title = locations.get(i).toString();
            double lat = locations.get(i).getLatitude();
            double lng = locations.get(i).getLongitude();
            LatLng position = new LatLng(lat,lng);
            googleMap.addMarker(new MarkerOptions().position(position).title(title));
        }
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(51.508530, -0.076132)));
    }

    // Zeigt die Hotels in der Nähe der angeklickten Stadt an
    @Override
    public boolean onMarkerClick(Marker marker) {
        String name = marker.getTitle();
        Log.d("Click on", "" + name);
        LatLng a = marker.getPosition();
        String lat = String.valueOf(a.latitude);
        String lon = String.valueOf(a.longitude);
        List<Place> list = GooglePlaces.getLodgingFrom(lat,lon);

        for(int i=0; i<list.size();i++){
            double lat4 = Double.parseDouble(list.get(i).getLatitude());
            double lng4 = Double.parseDouble(list.get(i).getLongitude());
            LatLng position = new LatLng(lat4,lng4);
            String title = list.get(i).getName();
            googleMap.addMarker(new MarkerOptions().position(position).title(title));

        }
        return false;
    }


}