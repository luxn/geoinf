package de.jadehs.trawell.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import de.jadehs.trawell.R;
import de.jadehs.trawell.database.DBTour;

/**
 * Created by Christopher on 07.06.2017.
 */

public class ArrayAdapterString extends ArrayAdapter<Accomodation> {

    public ArrayAdapterString(Context context, int textViewResourceId, ArrayList<Accomodation> myTours){
        super(context, textViewResourceId, myTours);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if(view == null){
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            view = vi.inflate(R.layout.tour_item, null);
        }
        Accomodation ac = getItem(position);

        if(ac != null){
            TextView text = (TextView) view.findViewById(R.id.tourItem);
            if(text != null){
                text.setText("Das Hotel "+ac.getName()+" hat die Adresse "+ ac.getAdresse()+" und ist mit "+ ac.getBewertung()+" bewertet");
            }
        }
        return view;
    }
}
