package de.jadehs.trawell.api;

import android.media.MediaPlayer;
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
    public String iconURL;
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

    public static void getWeatherFrom(final String location, final OnTaskCompletedListener<Weather> callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                /*
                Weather Api
                username: trawell
                password: projgeo6
                */

                try {
                    final String apiKey = "16485cd730499881bbb74c9f3d35b89c";
                    final URL urlWeatherAPI = new URL("http://api.openweathermap.org/data/2.5/weather?q="+ location + "&appid=" + apiKey);
                    InputStream inputStream = getStreamForUrl(urlWeatherAPI);
                    JSONObject jsonObject = new JSONObject(streamToString(inputStream));


                    double temperaturKelvin = jsonObject.getJSONObject("main").getDouble("temp");
                    String iconID = jsonObject.getJSONArray("weather").getJSONObject(0).getString("icon");
                    String l = jsonObject.getString("name");
                    inputStream.close();

                    Weather w = new Weather();
                    w.temp = temperaturKelvin - 273.15;
                    w.location = l;
                    w.iconURL = "http://openweathermap.org/img/w/"+iconID+".png";

                    callback.onSuccess(w);



                } catch (IOException ex) {
                    callback.onException(ex);
                } catch (JSONException ex) {
                    callback.onException(ex);
                }


        /*
            Rückgabe JSON sieht so aus
            {
              "coord": {
                "lon": 10,
                "lat": 53.55
              },
              "weather": [
                {
                  "id": 802,
                  "main": "Clouds",
                  "description": "scattered clouds",
                  "icon": "03d"
                }
              ],
              "base": "stations",
              "main": {
                "temp": 281.83,
                "pressure": 1020,
                "humidity": 42,
                "temp_min": 281.15,
                "temp_max": 282.15
              },
              "visibility": 10000,
              "wind": {
                "speed": 2.1,
                "deg": 270
              },
              "clouds": {
                "all": 40
              },
              "dt": 1494319800,
              "sys": {
                "type": 1,
                "id": 4883,
                "message": 0.0851,
                "country": "DE",
                "sunrise": 1494300531,
                "sunset": 1494356708
              },
              "id": 2911298,
              "name": "Hamburg",
              "cod": 200
            }

         */
            }
        }).start();


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
