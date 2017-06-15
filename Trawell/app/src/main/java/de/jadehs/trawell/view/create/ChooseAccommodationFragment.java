package de.jadehs.trawell.view.create;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Places;

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
import de.jadehs.trawell.miscellaneous.TrawellArrayAdapter;
import de.jadehs.trawell.view.home.MainActivity;

public class ChooseAccommodationFragment extends Fragment implements OnTaskCompletedListener<List<Accommodation>>, GoogleApiClient.OnConnectionFailedListener {

    private Long cityId;
    private GoogleApiClient mGoogleApiClient;
    private TrawellGraph graph;
    private ListView accoListView;
    private ArrayList<Accommodation> accommodations;
    private TrawellArrayAdapter<Accommodation> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_accommodation, container, false);

        accoListView = (ListView) view.findViewById(R.id.accoListView);

        graph = TrawellGraph.get(getContext());

        if(getActivity().getClass().equals(MainActivity.class)){
            cityId = MainActivity.cityId;
        } else if (getActivity().getClass().equals(NewTourActivity.class)){
            cityId = NewTourActivity.cityId;
        }

        City city = City.findById(City.class, cityId);
        Log.d("city",""+city.getName());
        getActivity().setTitle("Choose your Lodgings for " + city.getName());

        Location location = graph.getLocationByName(city.getName());

        mGoogleApiClient = new GoogleApiClient
                .Builder(getActivity())
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(getActivity(), this)
                .build();

        accommodations = new ArrayList<>();

        AccommodationsService.getAccomodationsFor(location, mGoogleApiClient, this);


//        Log.d("size",""+accommodations.size());
        Log.d("size",""+accommodations.size());
        adapter = new TrawellArrayAdapter<>(getContext(),R.layout.accommodation_item, accommodations, Accommodation.class);
        accoListView.setAdapter(adapter);

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

        for(int i = 0; i < list.size(); i++){
            Accommodation acco = new Accommodation();
            acco.setName(list.get(i).getName());
            acco.setAdresse(list.get(i).getAdresse());
            acco.setBewertung(list.get(i).getBewertung());
            acco.setPhoneNumber(list.get(i).getPhoneNumber());
            acco.setUrl(list.get(i).getUrl());
            this.accommodations.add(acco);
            Log.d("done","done");
//            Log.d("LODGING","name "+list.get(i).getName());
//            Log.d("LODGING","adresse "+list.get(i).getAdresse());
//            Log.d("LODGING","rating "+list.get(i).getBewertung());
        }
        Log.d("size",""+accommodations.size());
        adapter.notifyDataSetChanged();
        getFragmentManager().beginTransaction().detach(this).attach(this).commit();
    }

    @Override
    public void onException(Exception e) {

    }
}