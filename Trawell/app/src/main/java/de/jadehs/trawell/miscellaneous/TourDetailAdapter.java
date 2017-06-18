package de.jadehs.trawell.miscellaneous;

import android.content.Context;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.jadehs.trawell.R;
import de.jadehs.trawell.models.Accommodation;
import de.jadehs.trawell.models.City;
import de.jadehs.trawell.models.Tour;
import de.jadehs.trawell.view.tours.TourDetailFragment;

/**
 * Created by Christopher on 07.06.2017.
 */

public class TourDetailAdapter<T> extends ArrayAdapter<T> {

    private Class<T> type;

    public TourDetailAdapter(Context context, int textViewResourceId, ArrayList<T> myTours, Class<T> classT){
        super(context, textViewResourceId, myTours);
        type = classT;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;


        if(view == null){
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            view = vi.inflate(R.layout.tour_overview_item, null);
        }
        City city = (City) getItem(position);
        List<Accommodation> acco = Accommodation.find(Accommodation.class, "CITY = ?", String.valueOf(city.getId()));

        TextView name = (TextView) view.findViewById(R.id.cityNameTV);
        TextView accoName = (TextView) view.findViewById(R.id.accoNameTV);
        TextView fromTo = (TextView) view.findViewById(R.id.fromToTV);

        name.setText(city.getName());
        accoName.setText(acco.get(0).getName());
        fromTo.setText(city.getName());



        return view;
    }
}
