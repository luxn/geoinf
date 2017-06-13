package de.jadehs.trawell.view.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import de.jadehs.trawell.R;
import de.jadehs.trawell.models.Tour;
import de.jadehs.trawell.miscellaneous.TourArrayAdapter;
import de.jadehs.trawell.view.tours.TourDetailFragment;

import static de.jadehs.trawell.view.home.MainActivity.tourId;

public class TourOverviewFragment extends Fragment {

    public ListView myToursLV;
    public ArrayAdapter<Tour> adapter;
    private List<Tour> myTours;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        getActivity().setTitle("My tours");
        myTours = new ArrayList<>();
        myTours = Tour.listAll(Tour.class);

        View view = inflater.inflate(R.layout.fragment_my_tours, container, false);

        myToursLV = (ListView) view.findViewById(R.id.myToursList);
        myToursLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tourId = myTours.get(position).getId();
                try {
                    MainActivity.goTo(TourDetailFragment.class);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (java.lang.InstantiationException e) {
                    e.printStackTrace();
                }
            }
        });
        adapter = new TourArrayAdapter(getContext(), R.layout.tour_item, (ArrayList) myTours, Tour.class);
        myToursLV.setAdapter(adapter);
        // Inflate the layout for this fragment
        return view;
    }

}
