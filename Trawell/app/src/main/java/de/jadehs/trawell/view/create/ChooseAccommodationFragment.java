package de.jadehs.trawell.view.create;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.MapView;

import java.util.ArrayList;
import java.util.List;

import de.jadehs.trawell.R;
//import de.jadehs.trawell.api.GooglePlaces;
import de.jadehs.trawell.api.AccommodationsService;
import de.jadehs.trawell.api.OnTaskCompletedListener;
import de.jadehs.trawell.graph.Location;
import de.jadehs.trawell.graph.TrawellGraph;
import de.jadehs.trawell.models.Accommodation;
import de.jadehs.trawell.models.City;
import de.jadehs.trawell.models.Tour;
import de.jadehs.trawell.miscellaneous.ItemAdapter;
import de.jadehs.trawell.miscellaneous.TourArrayAdapter;
import de.jadehs.trawell.view.home.MainActivity;

public class ChooseAccommodationFragment extends Fragment implements OnTaskCompletedListener<List<Accommodation>>, GoogleApiClient.OnConnectionFailedListener {

    private Long cityId;
    private GoogleApiClient mGoogleApiClient;
    private TrawellGraph graph;

    private ListView listView;
    private TourArrayAdapter listViewAdapter;
    private List<City> cities;

    Button ready;
    MapView mapView;
    ArrayAdapter<String> adapter;
    private static final int GOOGLE_API_CLIENT_ID = 0;
    private static ArrayList<Pair<Long, String>> mItemArray;
    private static ItemAdapter listAdapter;
    public static int aktuelleLocationID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_accommodation, container, false);

        graph = TrawellGraph.get(getContext());

        if(getActivity().getClass().equals(MainActivity.class)){
            cityId = MainActivity.cityId;
        } else if (getActivity().getClass().equals(NewTourActivity.class)){
            cityId = NewTourActivity.cityId;
        }

        City city = City.findById(City.class, cityId);
        getActivity().setTitle("Choose your Lodgings for " + city.getName());

        Location location = graph.getLocationByName(city.getName());

        mGoogleApiClient = new GoogleApiClient
                .Builder(getActivity())
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(getActivity(), this)
                .build();

        AccommodationsService.getAccomodationsFor(location, mGoogleApiClient, this);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("destroyView", "destroyView");
        mGoogleApiClient.stopAutoManage(getActivity());
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e("trawell", connectionResult.getErrorMessage());
    }

    @Override
    public void onSuccess(List<Accommodation> list) {
        Log.d("LODGING","size "+list.size());

        for(int i = 0; i < list.size(); i++){
            Log.d("LODGING","name "+list.get(i).getName());
            Log.d("LODGING","adresse "+list.get(i).getAdresse());
            Log.d("LODGING","rating "+list.get(i).getBewertung());
        }
    }

    @Override
    public void onException(Exception e) {

    }
}