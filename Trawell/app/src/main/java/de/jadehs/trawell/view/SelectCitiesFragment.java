package de.jadehs.trawell.view;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import de.jadehs.trawell.R;
import de.jadehs.trawell.graph.Location;
import de.jadehs.trawell.graph.TrawellGraph;
import de.jadehs.trawell.graph.TripLoader;

import static de.jadehs.trawell.view.NewTourActivity.locations;

public class SelectCitiesFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    MapView mapView;
    GoogleMap googleMap;
    Button nextBTN, previousBTN;

    ListView citiesListView;
    ArrayList<String> cities;
    ArrayAdapter<String> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle("Select the cities you want to visit");
        View view = inflater.inflate(R.layout.fragment_select_cities, container, false);

        citiesListView = (ListView) view.findViewById(R.id.citiesListView);
        cities = new ArrayList<>();
        adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,cities);
        citiesListView.setAdapter(adapter);

        nextBTN = (Button) view.findViewById(R.id.selectCitiesNextBTN);
        nextBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    NewTourActivity.goTo(OrganizeTravelFragment.class);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (java.lang.InstantiationException e) {
                    e.printStackTrace();
                }
            }
        });

        previousBTN = (Button) view.findViewById(R.id.selectCitiesPreBTN);

        previousBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    NewTourActivity.goTo(NewTourFragment.class);
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

        // Inflate the layout for this fragment
        return view;
    }
    public void addItems(String name){
        cities.add(name);
        adapter.notifyDataSetChanged();
    }
    public void removeItems(String name){
        cities.remove(name);
        adapter.notifyDataSetChanged();
    }

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

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        String name = marker.getTitle();
        Log.d("Click on", "" + name);
        if(cities.contains(name)) {
            removeItems(name);
            marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        } else{
            addItems(name);
            marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        }
        return false;
    }
}
