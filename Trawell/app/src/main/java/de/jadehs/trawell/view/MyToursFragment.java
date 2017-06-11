package de.jadehs.trawell.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import de.jadehs.trawell.R;
import de.jadehs.trawell.database.DBCity;
import de.jadehs.trawell.database.DBTour;
import de.jadehs.trawell.models.TourArrayAdapter;

public class MyToursFragment extends Fragment {

    public ListView myToursLV;
    public ArrayAdapter<DBTour> adapter;
    private List<DBTour> myTours;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("My Tours");
        myTours = new ArrayList<DBTour>();
        myTours = DBTour.listAll(DBTour.class);

        View view = inflater.inflate(R.layout.fragment_my_tours, container, false);

        myToursLV = (ListView) view.findViewById(R.id.myToursList);
        myToursLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DBTour tour = myTours.get(position);
                Intent intent = new Intent(getActivity().getApplicationContext(), TourActivity.class);
                intent.putExtra("tourId", tour.getId().intValue());
                startActivity(intent);
            }
        });
        adapter = new TourArrayAdapter(getContext(), R.layout.tour_item, (ArrayList) myTours, DBTour.class);
        myToursLV.setAdapter(adapter);
        // Inflate the layout for this fragment
        return view;
    }

}
