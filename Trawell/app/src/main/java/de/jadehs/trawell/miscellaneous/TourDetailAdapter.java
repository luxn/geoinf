package de.jadehs.trawell.miscellaneous;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.jadehs.trawell.R;
import de.jadehs.trawell.graph.DayTime;
import de.jadehs.trawell.graph.TrawellGraph;
import de.jadehs.trawell.graph.Trip;
import de.jadehs.trawell.models.Accommodation;
import de.jadehs.trawell.models.City;
import de.jadehs.trawell.models.Tour;

/**
 * Created by Christopher on 07.06.2017.
 */

public class TourDetailAdapter<T> extends ArrayAdapter<T> {

    private Class<T> type;
    private int listSize;
    private TrawellGraph graph = TrawellGraph.get(getContext());

    public TourDetailAdapter(Context context, int textViewResourceId, ArrayList<T> myTours, Class<T> classT){
        super(context, textViewResourceId, myTours);
        type = classT;
        listSize = myTours.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;


        if(view == null){
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            if(type.equals(City.class))
                view = vi.inflate(R.layout.tour_overview_item, null);
        }
        if(type.equals(City.class)){
            City city = (City) getItem(position);

            Tour tour = Tour.findById(Tour.class,city.getTour().getId());

            if(city != null){
                List<Accommodation> acco = Accommodation.find(Accommodation.class, "CITY = ?", String.valueOf(city.getId()));

                TextView name = (TextView) view.findViewById(R.id.detailCityNameTV);
                TextView accoName = (TextView) view.findViewById(R.id.detailAccoNameTV);
                TextView fromTo = (TextView) view.findViewById(R.id.fromToTV);
                TextView tripsTV = (TextView) view.findViewById(R.id.tripsTV);

                StringBuilder tripsString = new StringBuilder();

                if(name != null){
                    name.setText(city.getName());
                    if(!acco.isEmpty())
                        accoName.setText(acco.get(0).getName());
                    if(!tour.getFinalCity().equals(city.getName())) {
                        Log.d("city",""+city.getName());
                        City nextCity = (City) getItem(position+1);
                        List<Trip> trips = graph.dijkstra(graph.getLocationByName(city.getName()),graph.getLocationByName(nextCity.getName()),new DayTime("8:30"));
                        for(Trip t : trips){
                            tripsString.append(t.toString()+"\n");
                        }
                        if(tripsString != null)
                            tripsTV.setText(tripsString);
                        fromTo.setText(city.getName() + " - " + ((City) getItem(position + 1)).getName());
                    } else {
                        name.setText(city.getName());
                        accoName.setText(acco.get(0).getName());
                        fromTo.setText("");
                        tripsTV.setText("");

                    }
                }
            }
        }

        return view;
    }
}
