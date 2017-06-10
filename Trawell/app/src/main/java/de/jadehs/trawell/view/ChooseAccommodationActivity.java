package de.jadehs.trawell.view;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

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

import de.jadehs.trawell.R;
import de.jadehs.trawell.database.DBCity;
import de.jadehs.trawell.graph.Location;
import de.jadehs.trawell.graph.TrawellGraph;

public class ChooseAccommodationActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private static int cityId;
    private TrawellGraph graph;
    private int radius = 5000;
    private String type = "lodging";
    private StringBuilder sb;
    private ArrayList<String> places;
    private GoogleApiClient mGoogleApiClient;

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
        Log.d("key", ""+ getString(R.string.API_KEY));

        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();


        new Thread(new Runnable() {
            @Override
            public void run() {

        URL url = null;

        try {
            url = new URL(sb.toString());
            InputStream inStream = url.openStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(inStream,"UTF-8"));
            StringBuilder jsonResult = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                jsonResult.append(line);
            }

            JSONObject jsonObj = new JSONObject(jsonResult.toString());
            JSONArray jsonArray = jsonObj.getJSONArray("results");

            places = new ArrayList<String>();

            for(int i = 0; i < jsonArray.length(); i++){
                String place_id = jsonArray.getJSONObject(i).getString("place_id");
                places.add(place_id);
            }

            for(int i = 0; i < places.size(); i++){
                String placeId = places.get(i);
                Places.GeoDataApi.getPlaceById(mGoogleApiClient, placeId).setResultCallback(
                        new ResultCallback<PlaceBuffer>() {
                            @Override
                            public void onResult(@NonNull PlaceBuffer places) {
                                if(places.getStatus().isSuccess() && places.getCount() > 0){
                                    final Place myPlace = places.get(0);
                                    // Hier muss eine Liste erzeugt werden die einem ArrayAdapter übergeben wird
                                    // der den ListView verwaltet und die Unterkünfte anzeigt!
                                    Log.d("place", ""+myPlace.getName());
                                    Log.d("rating", ""+myPlace.getRating());
                                    Log.d("adress", ""+myPlace.getAddress());
                                }
                            }
                        }
                );
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
            }
        }).start();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
