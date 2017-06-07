package de.jadehs.trawell.models;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import de.jadehs.trawell.R;
import de.jadehs.trawell.database.DBTour;

/**
 * Created by Christopher on 07.06.2017.
 */

public class TourArrayAdapter extends ArrayAdapter<DBTour> {

    public TourArrayAdapter(Context context, int textViewResourceId, ArrayList<DBTour> myTours){
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
        DBTour tour = getItem(position);

        if(tour != null){
            TextView text = (TextView) view.findViewById(R.id.tourItem);
            if(text != null){
                text.setText(tour.getStartCity() + " - " + tour.getFinalCity());
            }
        }
        return view;
    }
}
