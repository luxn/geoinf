package de.jadehs.trawell;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;

import de.jadehs.trawell.api.OnTaskCompletedListener;
import de.jadehs.trawell.api.Weather;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;


    //test comment


    //blabla

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_new_tour);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_my_tours);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        Weather.getWeatherFrom("London", new OnTaskCompletedListener<Weather>() {
            @Override
            public void onSuccess(Weather weather) {
                Log.d("WEATHER"," ... " + weather.location + " : " + weather.temp + "°C");
            }

            @Override
            public void onException(Exception e) {
                e.printStackTrace();
            }
        });


    }

}
