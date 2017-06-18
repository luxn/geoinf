package de.jadehs.trawell.view.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import de.jadehs.trawell.R;
import de.jadehs.trawell.api.OnTaskCompletedListener;
import de.jadehs.trawell.api.Weather;
import de.jadehs.trawell.graph.DayTime;
import de.jadehs.trawell.graph.Dijkstra;
import de.jadehs.trawell.graph.Duration;
import de.jadehs.trawell.graph.GraphLoader;
import de.jadehs.trawell.graph.Location;
import de.jadehs.trawell.graph.TrawellGraph;
import de.jadehs.trawell.graph.Trip;
import de.jadehs.trawell.models.City;
import de.jadehs.trawell.models.Tour;

public class CurrentTourFragment extends Fragment {


    TextView departureTV, weatherTV;
    ImageView interrailIcon;
    Weather weather;
    private List<Tour> myTours;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_current_tour, container, false);

        long diffInDays = 9999999;
        Tour currentTour = null;
        Date today = new Date();
        myTours = new ArrayList<>();
        myTours = Tour.listAll(Tour.class);
        // Es wird untersucht, welcher Endzeitpunkt der verfügbaren Touren dem jetzigen Datum am nächsten liegt.
        // Die resultierende Tour wird als die aktuellste Tour angesehen.
        for (int i = 0; i < myTours.size(); i++) {
            Tour tour = myTours.get(i);
            Date end = tour.getEnd();
            if (getDateDiff(today, end, TimeUnit.DAYS) < diffInDays) {
                currentTour = tour;
            }
        }

        if (currentTour != null) {
            getActivity().setTitle(currentTour.getStartCity()+" - "+currentTour.getFinalCity());


            TrawellGraph graph = TrawellGraph.get(getContext());

            List<City> cities = Tour.find(City.class, "TOUR = ?", String.valueOf(currentTour.getId()));

            Date now = new Date();
            DayTime dayTime = new DayTime(now.getHours(), now.getMinutes());







            City nextCity = null;

            Date tourStartDate = currentTour.getStart();
            long diff = 0;
            // Überprüfen, ob die Tour schon begonnen hat
            if (now.before(tourStartDate)) {
                nextCity = cities.get(0);
                diff = getDateDiff(now, tourStartDate, TimeUnit.DAYS);
            } else {
                //diff = getDateDiff(tourStartDate, now, TimeUnit.DAYS);
            }

            //Log.d("nextCity", ""+nextCity.getName());
            Location startLocation = graph.getLocationByName(currentTour.getStartCity());
            //Location finalLocation = graph.getLocationByName(nextCity.getName());
            Location finalLocation = graph.getLocationByName(currentTour.getFinalCity());
            List<Trip> tripList = graph.dijkstra(startLocation, finalLocation, dayTime);

            if (!tripList.isEmpty()) {
                Trip t = tripList.get(0);
                DayTime startTime = t.getStartTime();
                Duration duration = dayTime.timeBetween(startTime);
                departureTV = (TextView) view.findViewById(R.id.departureTextView);
                Spanned text = Html.fromHtml("Next Departure in <b>"+diff+" days and "+duration.getDurationInMinutes()+" minutes.</b>");
                departureTV.setText(text);
                TextView tripTV = (TextView) view.findViewById(R.id.tripTextView);
                StringBuilder s = new StringBuilder();
                for (Trip tr : tripList) {
                    s.append(tr.toString()+"  |  ");
                }
                s.delete(s.length()-3, s.length());
                //tripTV.setText(s.toString());
                tripTV.setText(t.toString());
            }



            //cities.get(0).getD



            weatherTV = (TextView) view.findViewById(R.id.weatherTextView);
            Weather.getWeatherFrom(currentTour.getFinalCity(), new OnTaskCompletedListener<Weather>() {
                @Override
                public void onSuccess(Weather w) {
                    weather = w;
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            weatherTV.setText("Current weather in "+weather.location +":"+ "\nTemperature: " + weather.temp +"°C\nHumidity: "+weather.hum+"%");
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

        }

        interrailIcon = (ImageView) view.findViewById(R.id.informationInterrail);

        interrailIcon.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://www.interrail.eu/de"));
                startActivity(intent);
            }

        });
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

    private long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
    }
}
