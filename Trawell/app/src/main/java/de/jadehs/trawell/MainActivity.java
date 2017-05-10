package de.jadehs.trawell;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;

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


        Weather.getWeatherFrom("Oldenburg", new OnTaskCompletedListener<Weather>() {
            @Override
            public void onSuccess(Weather weather) {
                mTextMessage.setText("Weather in " + weather.location + ": " + weather.temp +"°C");
                new DownloadImageTask((ImageView) findViewById(R.id.imageWeather))
                    .execute(weather.iconURL);
            }

            @Override
            public void onException(Exception e) {
                e.printStackTrace();
            }
        });

        // Diashow
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        int images[] = { R.drawable.belgien, R.drawable.bosnienundherzegowina, R.drawable.bulgarien,
                R.drawable.daenemark, R.drawable.deutschland, R.drawable.finnland, R.drawable.frankreich,
                R.drawable.griechenland, R.drawable.grossbritannien, R.drawable.irland, R.drawable.kroatien,
                R.drawable.luxemburg, R.drawable.mezedonien, R.drawable.montenegro, R.drawable.niederlande,
                R.drawable.norway, R.drawable.oesterreich, R.drawable.polen, R.drawable.portugal, R.drawable.rumaenien,
                R.drawable.schweden, R.drawable.schweiz, R.drawable.slowakei, R.drawable.slowenien, R.drawable.spanien,
                R.drawable.tschechien, R.drawable.tuerkei, R.drawable.ungarn};
        animate(imageView, images, 0);
    }

    private void animate(final ImageView imageView, final int images[], final int index) {

        int fadeInDuration = 500;
        int timeBetween = 3000;
        int fadeOutDuration = 1000;

        imageView.setImageResource(images[index]);

        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator());
        fadeIn.setDuration(fadeInDuration);

        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setStartOffset(fadeInDuration + timeBetween);
        fadeOut.setDuration(fadeOutDuration);

        AnimationSet animation = new AnimationSet(false);
        animation.addAnimation(fadeIn);
        animation.addAnimation(fadeOut);
        imageView.setAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationEnd(Animation animation) {
                // Repeats the images recursive
                if (images.length > index + 1) {
                    animate(imageView, images, index + 1);
                } else {
                    animate(imageView, images, 0);
                }
            }
            public void onAnimationRepeat(Animation animation) {
            }
            public void onAnimationStart(Animation animation) {
            }
        });
    }

    // Inner class for the weather icon that needs to be downloaded
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
