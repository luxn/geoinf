//package de.jadehs.trawell.api;
//
//
//import android.renderscript.Sampler;
//import android.util.JsonReader;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.BufferedReader;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//
//import java.io.InputStreamReader;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.List;
//
//
//import de.jadehs.trawell.graph.Place;
//import de.jadehs.trawell.models.Accomodation;
//
//import static de.jadehs.trawell.view.NewTourActivity.cities;
//
////import se.walkercrou.places.exception.GooglePlacesException;
///**
// * Created by Lisa Haltermann on 24.05.2017.
// */
///**
// * Main class of API. Used for all entry web-api operations.
// */
//public class GooglePlaces {
//
//    public String name;
//    public String iconURL;
//    public String location;
//
//
//    // Breite ist lat, Lände ist lon
//    public static List<Accomodation> getLodgingFrom(final double lonEingabe, final double latEinagbe, final int radiusEingabe) {
//        final List<Accomodation> list = new ArrayList<Accomodation>();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                /*
//                Lodging Api
//                username: trawell
//                password: projgeo6
//                */
//                double lat = latEinagbe;
//                double lon = lonEingabe;
//                int radius = radiusEingabe;
//
//                URL url;
//
//                try {
//                    final String apiKey = "AIzaSyA1jEeZR3rlEoUzVPYrcPsofCLGXETFwgo";
//                    url = new URL("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+lat+","+lon+"&radius="+radius+"&type=lodging&key="+apiKey);
//                    InputStream inputStream = url.openStream();
//
//                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
//                    String json = reader.readLine();
//
//                    JSONObject jsonObject = new JSONObject(json);
//                    JSONArray jsonArrayItems = jsonObject.getJSONArray("results");
//                    for (int i = 0; i < 10; i++) {
//
//                        JSONObject jsonObj = jsonArrayItems.getJSONObject(i);
//                        double lat3 = jsonObj.getJSONObject("geometry").getJSONObject("location").getDouble("lat");
//                        double lon3 = jsonObj.getJSONObject("geometry").getJSONObject("location").getDouble("lon");
//                        String name = jsonObj.getString("name");
//                        int ranting = jsonObj.getInt("rating");
//
//                        String adress = jsonObj.getString("vicinity");
//                        android.location.Location loc = new android.location.Location(name);
//                        loc.setLongitude(lon3);
//                        loc.setLatitude(lat3);
//                        list.add(new Accomodation(name, ranting,adress));
//                    }
//                } catch (IOException ex) {
//                } catch (JSONException ex) {
//                }
//            }
//        }).start();
//
//        return list;
//    }
//}
//
//
