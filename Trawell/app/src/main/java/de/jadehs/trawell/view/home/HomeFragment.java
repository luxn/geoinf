package de.jadehs.trawell.view.home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ShareCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import de.jadehs.trawell.api.OnTaskCompletedListener;
import de.jadehs.trawell.api.Weather;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.jadehs.trawell.R;
import de.jadehs.trawell.graph.Location;
import de.jadehs.trawell.graph.TrawellGraph;
import de.jadehs.trawell.share.TrawellMapGenerator;

public class HomeFragment extends Fragment {

    TextView weatherText = null;
    View view;
    Weather weather;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home, container, false);

        Button btn = (Button) view.findViewById(R.id.btnShare);
        final ImageView iv = (ImageView) view.findViewById(R.id.ivDebug);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TrawellGraph graph = TrawellGraph.get(getContext());

                TrawellMapGenerator mapGenerator = new TrawellMapGenerator(getContext(), graph);

                Location hamburg = graph.getLocationByName("Hamburg");
                Location paris = graph.getLocationByName("Paris");
                Location montpellier = graph.getLocationByName("Montpellier");

                List<Location> list = new ArrayList<>();
                list.add(hamburg);
                list.add(paris);
                list.add(montpellier);

                mapGenerator.drawRoute(new Date(), list);

                iv.setImageBitmap(mapGenerator.getMap());

                Intent i = mapGenerator.getShareIntent();
                startActivity(i);
            }
        });


        getActivity().setTitle("Trawell");

        // Diashow
        ImageView diashow = (ImageView) view.findViewById(R.id.diaImageView);
            int images[] = {R.drawable.belgien, R.drawable.bosnienundherzegowina, R.drawable.bulgarien,
                R.drawable.daenemark, R.drawable.deutschland, R.drawable.finnland, R.drawable.frankreich,
                R.drawable.griechenland, R.drawable.grossbritannien, R.drawable.irland, R.drawable.kroatien,
                R.drawable.luxemburg, R.drawable.mezedonien, R.drawable.montenegro, R.drawable.niederlande,
                R.drawable.norway, R.drawable.oesterreich, R.drawable.polen, R.drawable.portugal, R.drawable.rumaenien,
                R.drawable.schweden, R.drawable.schweiz, R.drawable.slowakei, R.drawable.slowenien, R.drawable.spanien,
                R.drawable.tschechien, R.drawable.tuerkei, R.drawable.ungarn};

        animate(diashow, images, 0);

        // Inflate the layout for this fragment
        return view;
    }
    // Inner class for the weather icon that needs to be downloaded
    class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
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

        String path = getString(images[index]);
        String fileName = path.substring(path.lastIndexOf("/")+1,path.indexOf("."));
        Log.d("bild: ", ""+ fileName);

        // Informations about the Weather
        weatherText = (TextView) view.findViewById(R.id.weatherTextView);

        Weather.getWeatherFrom(fileName, new OnTaskCompletedListener<Weather>() {
            @Override
            public void onSuccess(Weather w) {
                weather = w;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        weatherText.setText(weather.location + "\nTemperature: " + weather.temp +"°C\nHumidity: "+weather.hum+"%");
                        new DownloadImageTask((ImageView) view.findViewById(R.id.weatherImageView))
                                .execute(weather.iconURL);
                    }
                });
            }

            @Override
            public void onException(Exception e) {
                e.printStackTrace();
            }
        });

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
}