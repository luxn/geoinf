package de.jadehs.trawell.view.create;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import de.jadehs.trawell.R;

import static de.jadehs.trawell.view.create.NewTourActivity.cities;
import static de.jadehs.trawell.view.create.NewTourActivity.graph;
import static de.jadehs.trawell.view.create.NewTourActivity.tour;

public class SelectCitiesFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private MapView mapView;
    private GoogleMap googleMap;
    private Button nextBTN, previousBTN;

    private ListView citiesListView;
    private ArrayAdapter<String> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        getActivity().setTitle("Select the cities you want to visit");

        // temporary list of selected cities (just for the View)
        cities = new ArrayList<>();

        View view = inflater.inflate(R.layout.fragment_select_cities, container, false);

        citiesListView = (ListView) view.findViewById(R.id.citiesListView);

        adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,cities);
        citiesListView.setAdapter(adapter);

        nextBTN = (Button) view.findViewById(R.id.selectCitiesNextBTN);
        nextBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

//                    Location startCity = graph.getLocationByName(tour.getStartCity());
//                    Location finalCity = graph.getLocationByName(tour.getFinalCity());
//                    List<Location> citiesDijstra = graph.dijkstra(startCity, finalCity);

//                    for(int i = 0; i < cities.size();i++){
//                        if(rest > 0 ) {
//                            tour.addCity(new City(graph.getLocationByName(cities.get(i)), duration + 1));
//                            rest--;
//                        } else
//                            tour.addCity(new City(graph.getLocationByName(cities.get(i)),duration));
//                    }
                    // Next step --> organize the travel
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

        mapView = (MapView) view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        if (!cities.contains(tour.getStartCity()) || !cities.contains(tour.getFinalCity())){
            addItems(0,tour.getStartCity());
            addItems(1,tour.getFinalCity());
        }
        // Inflate the layout for this fragment
        return view;
    }
    public void addItems(int i, String name){
        cities.add(i,name);
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

        for(int i = 0; i < graph.getLocations().size(); i++){
            Log.d("location", "" + graph.getLocations().get(i));
            String title = graph.getLocations().get(i).toString();
            double lat = graph.getLocations().get(i).getLatitude();
            double lng = graph.getLocations().get(i).getLongitude();
            LatLng position = new LatLng(lat,lng);
            if (title.equals(tour.getStartCity()) || title.equals(tour.getFinalCity()))
                googleMap.addMarker(new MarkerOptions().position(position).
                        title(title).icon(BitmapDescriptorFactory.
                        defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            else
                googleMap.addMarker(new MarkerOptions().position(position).
                        title(title).icon(BitmapDescriptorFactory.
                        defaultMarker(BitmapDescriptorFactory.HUE_RED)));
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
        if (!name.equals(tour.getStartCity()) && !name.equals(tour.getFinalCity())){
            if (cities.contains(name)) {
                removeItems(name);
                marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            } else {
                addItems(1,name);
                marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            }
    }
        return false;
    }
}
