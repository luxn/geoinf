package de.jadehs.trawell.api;

import android.util.Log;

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

    /**
     *
     * @param town
     */
    public static void getWeather(String town) {
        String url = "api.openweathermap.org/data/2.5/weather?q=" + town;
        // delete later..
        url = "http://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=b1b15e88fa797225412429c1c50c122a1";
        InputStream is = getStreamForUrl(url);

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

    private static InputStream getStreamForUrl(String requestedURL) {
        try {
            URL url = new URL(requestedURL);
            URLConnection con = url.openConnection();
            return con.getInputStream();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static void main (String[] args) {
        Weather w = new Weather();
        w.getWeather("London");
        //Log.d("London", "test");
    }
}
