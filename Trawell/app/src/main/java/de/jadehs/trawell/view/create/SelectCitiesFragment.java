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

import java.util.List;

import de.jadehs.trawell.R;
import de.jadehs.trawell.miscellaneous.TrawellArrayAdapter;
import de.jadehs.trawell.models.City;

import static de.jadehs.trawell.view.create.NewTourActivity.cities;
import static de.jadehs.trawell.view.create.NewTourActivity.graph;
import static de.jadehs.trawell.view.create.NewTourActivity.tour;

public class SelectCitiesFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private MapView mapView;
    private GoogleMap googleMap;
    private Button nextBTN, previousBTN;

    private ListView citiesListView;
    private ArrayAdapter<City> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        super.onSaveInstanceState(savedInstanceState);
        getActivity().setTitle("Select the cities you want to visit");

        View view = inflater.inflate(R.layout.fragment_select_cities, container, false);

        citiesListView = (ListView) view.findViewById(R.id.citiesListView);

        adapter = new TrawellArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1, cities, City.class);
        citiesListView.setAdapter(adapter);

        nextBTN = (Button) view.findViewById(R.id.selectCitiesNextBTN);
        nextBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //TODO
//                    graph.dijkstraRaw(cities.get(0),)
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

        if (cityContainsList(cities, tour.getFinalCity()) == null
                || cityContainsList(cities, tour.getStartCity()) == null){
            City startCity = new City(tour.getStartCity(),tour, 0);
            City finalCity = new City(tour.getFinalCity(),tour, 0);
            addItems(0,startCity);
            addItems(1,finalCity);
        }
        // Inflate the layout for this fragment
        return view;
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

            if (cityContainsList(cities, title) != null)
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

    public static City cityContainsList(List<City> list, String name){
        for (City c : list){
            if(c.getName().equals(name)){
                return c;
            }
        }
        return null;
    }

    public void addItems(int i, City currentCity){
        cities.add(i,currentCity);
        adapter.notifyDataSetChanged();
    }
    public void removeItems(City currentCity){
        cities.remove(currentCity);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        String name = marker.getTitle();
        City currentCity;
        if (!name.equals(tour.getStartCity()) && !name.equals(tour.getFinalCity())){
            if ((currentCity = cityContainsList(cities, name)) != null) {
                removeItems(currentCity);
                marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            } else {
                currentCity = new City(name, tour, 0);
                addItems(1,currentCity);
                marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            }
    }
        return false;
    }
}
