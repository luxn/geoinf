package de.jadehs.trawell.view;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import de.jadehs.trawell.R;
import de.jadehs.trawell.api.GooglePlaces;
import de.jadehs.trawell.database.DBAccommodation;
import de.jadehs.trawell.database.DBCity;
import de.jadehs.trawell.graph.Location;
import de.jadehs.trawell.graph.TrawellGraph;
import de.jadehs.trawell.models.TourArrayAdapter;

public class ChooseAccommodationActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private static int cityId;
    private TrawellGraph graph;
    private int radius = 5000;
    private String type = "lodging";
    private StringBuilder sb;
    private GoogleApiClient mGoogleApiClient;

    private ArrayList<String> places;
    private static ArrayList<DBAccommodation> accommodations;

    private ListView accoListView;
    private TourArrayAdapter<DBAccommodation> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_accommodation);
        graph = new TrawellGraph();

        Intent intent = getIntent();
        cityId = intent.getIntExtra("cityId", -1);

        DBCity city = DBCity.findById(DBCity.class, new Long(cityId));
        this.setTitle("Choose your Lodgings for " + city.getName());

        Location location = graph.getLocationByName(city.getName());

        sb = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        sb.append("location="+location.getLatitude()+","+location.getLongitude());
        sb.append("&radius="+radius);
        sb.append("&type="+type);
        sb.append("&keyword=cruise");
        sb.append("&key="+getString(R.string.API_KEY));

        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();

        GooglePlaces googleP = new GooglePlaces();
        googleP.execute(mGoogleApiClient,sb);

        Log.d("size",""+accommodations.size());

        for(int i = 0; i < accommodations.size(); i++){
            Log.d("name",""+accommodations.get(i).getName());
            Log.d("adresse",""+accommodations.get(i).getAdresse());
            Log.d("rating",""+accommodations.get(i).getBewertung());
        }

//        accoListView = (ListView) this.findViewById(R.id.accoListView);
//        accoListView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//        adapter = new TourArrayAdapter<>(this,R.layout.tour_item,(ArrayList) accommodations, DBAccommodation.class);
//        accoListView.setAdapter(adapter);

    }

    public static void addAccommodation(DBAccommodation acco){
        accommodations.add(acco);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
