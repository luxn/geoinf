package de.jadehs.trawell.view.create;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import de.jadehs.trawell.R;
//import de.jadehs.trawell.api.GooglePlaces;
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
    private Location location;
    private ArrayList<String> placeIds = new ArrayList<>();
    private Thread placeIdThread, accommodationThread;
    private City city;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_choose_accommodation, container, false);
        accoListView = (ListView) view.findViewById(R.id.accoListView);

        graph = TrawellGraph.get(getContext());

        if(getActivity().getClass().equals(MainActivity.class)){
            cityId = MainActivity.cityId;
        } else if (getActivity().getClass().equals(NewTourActivity.class)){
            cityId = NewTourActivity.cityId;
        }

        city = City.findById(City.class, cityId);
        getActivity().setTitle("Choose your Lodgings for " + city.getName());

        location = graph.getLocationByName(city.getName());

        mGoogleApiClient = new GoogleApiClient
                .Builder(getActivity())
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(getActivity(), this)
                .build();

        placeIds = new ArrayList<>();
        getPlacesIds();
        placeIdThread.start();
        try {
            placeIdThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        accommodations = new ArrayList<>();
        getAccommodations();
        accommodationThread.start();
        try {
            accommodationThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        adapter = new TrawellArrayAdapter<>(getContext(),R.layout.tour_item, accommodations, Accommodation.class);
        accoListView.setAdapter(adapter);
        accoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                List<Accommodation> oldAccommodation = Accommodation.find(Accommodation.class, "CITY = ?", String.valueOf(city.getId()));
                if(!oldAccommodation.isEmpty())
                    oldAccommodation.get(0).delete();

                Log.d("acco", ""+accommodations.get(position).getName());
                Accommodation selectedAccommodation = accommodations.get(position);
                selectedAccommodation.setCity(city);
                selectedAccommodation.save();
                try {
                if(getActivity().getClass().equals(MainActivity.class)){
                    MainActivity.goTo(AccommodationsFragment.class);
                } else if (getActivity().getClass().equals(NewTourActivity.class)){
                    NewTourActivity.goTo(AccommodationsFragment.class);
                }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (java.lang.InstantiationException e) {
                    e.printStackTrace();
                }

            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mGoogleApiClient.stopAutoManage(getActivity());
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e("trawell", connectionResult.getErrorMessage());
    }

    @Override
    public void onSuccess(List<Accommodation> list) {
//        for(int i = 0; i < list.size(); i++){
//            Accommodation acco = new Accommodation();
//            acco.setName(list.get(i).getName());
//            acco.setAdresse(list.get(i).getAdresse());
//            acco.setBewertung(list.get(i).getBewertung());
//            acco.setPhoneNumber(list.get(i).getPhoneNumber());
//            acco.setUrl(list.get(i).getUrl());
//            this.accommodations.add(acco);
//            Log.d("done","done");
////            Log.d("LODGING","name "+list.get(i).getName());
////            Log.d("LODGING","adresse "+list.get(i).getAdresse());
////            Log.d("LODGING","rating "+list.get(i).getBewertung());
//        }
//        Log.d("size",""+accommodations.size());
//        adapter.notifyDataSetChanged();
//        getFragmentManager().beginTransaction().detach(this).attach(this).commit();
    }

    @Override
    public void onException(Exception e) {

    }

    public void addAcco(Accommodation acco){
        accommodations.add(acco);
        adapter.notifyDataSetChanged();
    }
    public void getAccommodations(){
        accommodationThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (String id : placeIds){
                    Places.GeoDataApi.getPlaceById(mGoogleApiClient, id).setResultCallback(
                            new ResultCallback<PlaceBuffer>() {

                                @Override
                                public void onResult(@NonNull PlaceBuffer places) {

                                    if (places.getStatus().isSuccess() && places.getCount() > 0) {

                                        final Place myPlace = places.get(0);
                                        Accommodation acco = new Accommodation();
                                        acco.setAdresse(myPlace.getAddress().toString());
                                        acco.setBewertung(String.valueOf(myPlace.getRating()));
                                        acco.setName(myPlace.getName().toString());
                                        acco.setPhoneNumber(myPlace.getPhoneNumber().toString());
                                        if (myPlace.getWebsiteUri() != null)
                                            acco.setUrl(myPlace.getWebsiteUri().toString());
                                        addAcco(acco);
                                    }
                                    places.release();
                                }
                            });
            }
            }
        });
    }
    public void add(String id){
        placeIds.add(id);
    }
    public void getPlacesIds(){
       placeIdThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final String apiKey = "AIzaSyAgxZyKMWkDMuwcYZEfIAPvsFghJC04NDY";

                    placeIds = new ArrayList<>();

                    //URL zusammenbauen
                    StringBuilder sb = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
                    sb.append("location=" + location.getLatitude() + "," + location.getLongitude());
                    sb.append("&radius=5000");
                    sb.append("&type=lodging");
                    sb.append("&key=" + MainActivity.context.getString(R.string.API_KEY));

                    URL url = new URL(sb.toString());

                    InputStream inStream = url.openStream();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, "UTF-8"));

                    StringBuilder jsonResult = new StringBuilder();

                    String line;
                    while ((line = reader.readLine()) != null) {
                        jsonResult.append(line);
                    }

                    JSONObject jsonObj = new JSONObject(jsonResult.toString());
                    JSONArray jsonArray = jsonObj.getJSONArray("results");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        String place_id = jsonArray.getJSONObject(i).getString("place_id");
                        add(place_id);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


}