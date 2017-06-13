package de.jadehs.trawell.miscellaneous;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import de.jadehs.trawell.R;
import de.jadehs.trawell.models.Accommodation;
import de.jadehs.trawell.models.City;
import de.jadehs.trawell.models.Tour;

/**
 * Created by Christopher on 07.06.2017.
 */

public class TourArrayAdapter<T> extends ArrayAdapter<T> {

    private Class<T> type;

    public TourArrayAdapter(Context context, int textViewResourceId, ArrayList<T> myTours, Class<T> classT){
        super(context, textViewResourceId, myTours);
        type = classT;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if(view == null){
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            view = vi.inflate(R.layout.tour_item, null);
        }

        if(type.equals(Tour.class)) {
            Tour tour = (Tour) getItem(position);

            if (tour != null) {
                TextView text = (TextView) view.findViewById(R.id.tourItem);
                if (text != null) {
                    text.setText(tour.getStartCity() + " - " + tour.getFinalCity());
                }
            }
        } else if(type.equals(City.class)){
            City city = (City) getItem(position);

            if (city != null) {
                TextView text = (TextView) view.findViewById(R.id.tourItem);
                if (text != null) {
                    text.setText(city.getName());
                }
            }
        } else if(type.equals(Accommodation.class)){
            Accommodation acco = (Accommodation) getItem(position);

            if (acco != null) {
                TextView text = (TextView) view.findViewById(R.id.tourItem);
                if (text != null) {
                    text.setText(acco.getName());
//                    text.setText("Das Hotel "+acco.getName()+
//                            " hat die Adresse "+ acco.getAdresse()+
//                            " und ist mit "+ acco.getBewertung()+
//                            " bewertet");
                }
            }
        }
        return view;
    }
}
