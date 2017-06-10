package de.jadehs.trawell.api;


import android.renderscript.Sampler;
import android.util.JsonReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


import de.jadehs.trawell.graph.Place;
import de.jadehs.trawell.models.Accomodation;

import static de.jadehs.trawell.view.NewTourActivity.cities;

//import se.walkercrou.places.exception.GooglePlacesException;
/**
 * Created by Lisa Haltermann on 24.05.2017.
 */
/**
 * Main class of API. Used for all entry web-api operations.
 */
public class GooglePlaces {

    public String name;
    public String iconURL;
    public String location;
    public static List<Accomodation> list;

    // Breite ist lat, Lände ist lon
    public static List<Accomodation> getLodgingFrom(final double lonEingabe, final double latEinagbe, final int radiusEingabe) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                /*
                Lodging Api
                username: trawell
                password: projgeo6
                */
                double lat = latEinagbe;
                double lon = lonEingabe;
                int radius = radiusEingabe;

                URL url;

                try {
                    final String apiKey = "AIzaSyA1jEeZR3rlEoUzVPYrcPsofCLGXETFwgo";
                    url = new URL("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+lat+","+lon+"&radius="+radius+"&type=lodging&key="+apiKey);
                    InputStream inputStream = url.openStream();

                    JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
                    list = new ArrayList<Accomodation>();
                    list = readJsonStream(inputStream);

                } catch (IOException ex) {
                }





            }
        }).start();

        return list;
    }


    public static List<Accomodation> readJsonStream(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            return readMessagesArray(reader);
        } finally {
            //reader.close();
        }
    }

    public static List<Accomodation> readMessagesArray(JsonReader reader) throws IOException {
        List<Accomodation> messages = new ArrayList<Accomodation>();

        reader.beginArray();
        while (reader.hasNext()) {
            messages.add(readMessage(reader));
        }
        reader.endArray();
        return messages;
    }

    public static  Accomodation readMessage(JsonReader reader) throws IOException {
        String name = null;
        double bewertung = 0;
        String adresse = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String field = reader.nextName();
            if (field.equals("name")) {
                name = reader.nextString();
            } else if(field.equals("rating")){
                bewertung = reader.nextDouble();
            } else if(field.equals("vicinity")){
                adresse = reader.nextString();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new Accomodation(name,bewertung,adresse);
    }
}


