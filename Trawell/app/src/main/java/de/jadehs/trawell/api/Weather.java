package de.jadehs.trawell.api;

import android.renderscript.ScriptGroup;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by David on 05.05.2017.
 */

public class Weather {


    public double temp;
    public String location;


    /**
     *
     * @param town
     */
    public static void getWeather(String town) throws IOException {
        String url = "api.openweathermap.org/data/2.5/weather?q=" + town;
        // delete later..
        url = "http://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=b1b15e88fa797225412429c1c50c122a1";
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
            URLConnection con = requestedURL.openConnection();
            return con.getInputStream();
        } catch (Exception ex) {
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

    public static Weather getWeatherFrom(String location) throws IOException, JSONException {

        /*
        Weather Api
        username: trawell
        password: projgeo6
        */
        final String apiKey = "16485cd730499881bbb74c9f3d35b89c";
        final URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q=" + location + "&appid=" + apiKey);
        final URL url2 = new URL("http://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=b1b15e88fa797225412429c1c50c122a1");
        InputStream inputStream = getStreamForUrl(url);
        JSONObject jsonObject = new JSONObject(streamToString(inputStream));

        /*
            Annahme
            Rückgabe JSON sähe so aus
            {
                "temp": 285.2,
                "location": "London",
                "lat": 48.62,
                "lon": 0.23
            }

         */

        double temperaturKelvin = jsonObject.getDouble("temp");
        String l = jsonObject.getString("location");
        inputStream.close();

        Weather w = new Weather();
        w.temp = temperaturKelvin + 273.15;
        w.location = l;

        return w;

    }
    // static main gibt es in Android nicht
    /*
    public static void main (String[] args) throws Exception{
        Weather w = Weather.getWeatherFrom("London");

        Log.d("WEATHER", w.location + " : " + w.temp +"°C");
        //Log.d("London", "test");
    }
    */
}
