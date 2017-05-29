package de.jadehs.trawell.api;


import android.util.Log;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
//import se.walkercrou.places.exception.GooglePlacesException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import de.jadehs.trawell.graph.Place;

import static de.jadehs.trawell.view.NewTourActivity.locations;
/**
 * Created by Lisa Haltermann on 24.05.2017.
 */
/**
 * Main class of API. Used for all entry web-api operations.
 */
public class GooglePlaces {


    public String lat;
    public String lon;
    public String name;
    public String iconURL;
    public String location;


    /**
     * @param town
     */
    public static void getLodging(String town) throws IOException {
        String url = "api.openweathermap.org/data/2.5/weather?q=" + town;
        // delete later..
        url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=32.800870,-96.830803&radius=400&type=lodging&key=AIzaSyA1jEeZR3rlEoUzVPYrcPsofCLGXETFwgo";
        InputStream is = getStreamForUrl(new URL(url));

        try {
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            File targetFile = new File(System.getProperty("java.io.tmpdir") + "targetFile.tmp");
            //File targetFile = new File("src/main/resources/targetFile.tmp");
            OutputStream outStream = new FileOutputStream(targetFile);
            outStream.write(buffer);
        } catch (IOException ex) {

        }

        //JsonReader jsonReader = Json.createReader(new FileInputStream(file));
    }

    private static InputStream getStreamForUrl(URL requestedURL) {
        try {
            InputStream stream = requestedURL.openStream();
            return stream;
        } catch (Exception ex) {
            Log.e("ERROR", ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }

    private static String streamToString(InputStream inputStream) throws IOException {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }
        return result.toString("UTF-8");
    }

    public static List<Place> getLodgingFrom(final String lat2, final String lon2) {
        final List<Place> list = new ArrayList<Place>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                /*
                Lodging Api
                username: trawell
                password: projgeo6
                */

//maps.googleapis.com/maps/api/place/nearbysearch/json?location=32.800870,-96.830803&radius=400&type=lodging
                try {
                    final String apiKey = "AIzaSyA1jEeZR3rlEoUzVPYrcPsofCLGXETFwgo";
                    final URL urlLodgingAPI = new URL("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + lat2 + "," + lon2 + "&radius=2000&type=lodging&key=" + apiKey);
                    InputStream inputStream = getStreamForUrl(urlLodgingAPI);
                    JSONObject jsonObject = new JSONObject(streamToString(inputStream));


                    JSONArray jsonArrayItems = jsonObject.getJSONArray("results");
                    for (int i = 0; i < jsonArrayItems.length(); i++) {

                        double lat = locations.get(i).getLatitude();
                        double lng = locations.get(i).getLongitude();

                        JSONObject jsonObj = jsonArrayItems.getJSONObject(i);
                        String lat3 = jsonObj.getJSONObject("geometry").getJSONObject("location").getString("lat");
                        String lon3 = jsonObj.getJSONObject("geometry").getJSONObject("location").getString("lon");
                        String name = jsonObj.getString("name");
                        list.add(new Place(lat3,lon3,name));
                    }

                    //callback.onSuccess(w);


                } catch (IOException ex) {
                } catch (JSONException ex) {
                }


            }
        }).start();


        return list;
    }
}
    // static main gibt es in Android nicht
    /*
    public static void main (String[] args) throws Exception{
        Weather w = Weather.getLodgingFrom("London");

        Log.d("WEATHER", w.location + " : " + w.temp +"°C");
        //Log.d("London", "test");
    }
    */

