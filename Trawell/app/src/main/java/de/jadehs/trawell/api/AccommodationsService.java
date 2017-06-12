package de.jadehs.trawell.api;

import android.support.annotation.NonNull;
import android.util.JsonReader;

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
import java.util.Collections;
import java.util.List;

import de.jadehs.trawell.R;
import de.jadehs.trawell.models.Accommodation;
import de.jadehs.trawell.graph.Location;
import de.jadehs.trawell.view.home.MainActivity;

/**
 * Created by Luxn on 10.06.2017.
 */

public class AccommodationsService {


    public static void getAccomodationsFor(final Location location, final GoogleApiClient apiClient, final OnTaskCompletedListener<List<Accommodation>> callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final String apiKey = "AIzaSyAgxZyKMWkDMuwcYZEfIAPvsFghJC04NDY";

                    List<String> placeIds = new ArrayList<>();

                    //URL zusammenbauen
                    StringBuilder sb = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
                    sb.append("location="+location.getLatitude()+","+location.getLongitude());
                    sb.append("&radius=5000");
                    sb.append("&type=lodging");
                    sb.append("&keyword=cruise");
                    sb.append("&key="+MainActivity.context.getString(R.string.API_KEY));

                    URL url = new URL(sb.toString());

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
                        placeIds.add(place_id);
                    }

                    //Infos zu den Orten/Ids holen, parallel mit Threads
                    List<Thread> workers = new ArrayList<>();

                    //ErgebnisListe, syncronisiert da wir mit Threads arbeiten
                    final List<Accommodation> accommodations = Collections.synchronizedList(new ArrayList<Accommodation>());


 /*                   //JSON Parsing
                    final List<Accommodation>  jsonReader = JSONReader.readJsonStream(inStream);
                    for(int i=0; i< jsonReader.size(); i++){
                        synchronized (accommodations) {
                            accommodations.add(jsonReader.get(i));
                        }
                    }  */



                    for (final String id : placeIds) {
                        Thread t = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Places.GeoDataApi.getPlaceById(apiClient, id).setResultCallback(
                                        new ResultCallback<PlaceBuffer>() {

                                            @Override
                                            public void onResult(@NonNull PlaceBuffer places) {

                                                if(places.getStatus().isSuccess() && places.getCount() > 0){

                                                    final Place myPlace = places.get(0);
                                                    Accommodation acco = new Accommodation();
                                                    acco.setAdresse(myPlace.getAddress().toString());
                                                    acco.setBewertung(String.valueOf(myPlace.getRating()));
                                                    acco.setName(myPlace.getName().toString());
                                                    synchronized (accommodations) {
                                                        accommodations.add(acco);
                                                    }
                                                }
                                                places.release();
                                            }
                                        }
                                );
                            }
                        });
                        workers.add(t);
                        t.start();
                    }

                    //Threads wieder alle einsammeln im diesen Thread :)
                    for (Thread t : workers) {
                        t.join();
                    }

                    callback.onSuccess(accommodations);

                } catch (Exception e) {
                    callback.onException(e);
                }
            }
        }).start();
    }
}
