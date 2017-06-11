package de.jadehs.trawell.api;


import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
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
import java.util.ArrayList;

import de.jadehs.trawell.database.DBAccommodation;
import de.jadehs.trawell.view.ChooseAccommodationActivity;

import static de.jadehs.trawell.view.ChooseAccommodationActivity.addAccommodation;

//import se.walkercrou.places.exception.GooglePlacesException;
/**
 * Created by Lisa Haltermann on 24.05.2017.
 */
/**
 * Main class of API. Used for all entry web-api operations.
 */
public class GooglePlaces extends AsyncTask<Object, Activity, ArrayList<DBAccommodation>> implements GoogleApiClient.OnConnectionFailedListener{

    private Exception exception;
    private ArrayList<String> places;
    private GoogleApiClient mGoogleApiClient;
    private ArrayList<DBAccommodation> accommodations = new ArrayList<DBAccommodation>();

    @Override
    protected ArrayList<DBAccommodation> doInBackground(Object... obj) {

        mGoogleApiClient = (GoogleApiClient) obj[0];
        URL url = null;
        places = new ArrayList<String>();

        try {
        url = new URL(obj[1].toString());
        InputStream inStream = url.openStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(inStream,"UTF-8"));
        StringBuilder jsonResult = new StringBuilder();

        String line;
        while ((line = reader.readLine()) != null) {
        jsonResult.append(line);
        }

        JSONObject jsonObj = new JSONObject(jsonResult.toString());
        JSONArray jsonArray = jsonObj.getJSONArray("results");

        for(int i = 0; i < jsonArray.length(); i++){
            String place_id = jsonArray.getJSONObject(i).getString("place_id");
            places.add(place_id);
        }

        for(int i = 0; i < places.size(); i++){
            final String placeId = places.get(i);
            Places.GeoDataApi.getPlaceById(mGoogleApiClient, placeId).setResultCallback(
                new ResultCallback<PlaceBuffer>() {

                @Override
                public void onResult(@NonNull PlaceBuffer places) {

                    if(places.getStatus().isSuccess() && places.getCount() > 0){
                        final Place myPlace = places.get(0);
                        DBAccommodation acco = new DBAccommodation();
                        acco.setAdresse(myPlace.getAddress().toString());
                        acco.setBewertung(String.valueOf(myPlace.getRating()));
                        acco.setName(myPlace.getName().toString());
                        addAccommodation(acco);
//                        Log.d("now",""+accommodations.size());
//                        Log.d("place", ""+myPlace.getName());
//                        Log.d("rating", ""+myPlace.getRating());
//                        Log.d("adress", ""+myPlace.getAddress());
                    }
                    places.release();
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
        return accommodations;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}


