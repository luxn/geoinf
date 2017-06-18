package de.jadehs.trawell.view.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import de.jadehs.trawell.R;
import de.jadehs.trawell.models.Accommodation;
import de.jadehs.trawell.models.City;
import de.jadehs.trawell.models.Tour;
import de.jadehs.trawell.miscellaneous.TrawellArrayAdapter;
import de.jadehs.trawell.view.tours.TourDetailFragment;

import static de.jadehs.trawell.view.home.MainActivity.tourId;

public class TourOverviewFragment extends Fragment {

    public ListView myToursLV;
    public ArrayAdapter<Tour> adapter;
    private List<Tour> myTours;
    private int position;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        getActivity().setTitle("My tours");
        myTours = new ArrayList<>();
        myTours = Tour.listAll(Tour.class);

        View view = inflater.inflate(R.layout.fragment_my_tours, container, false);

        myToursLV = (ListView) view.findViewById(R.id.myToursList);
        myToursLV.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
                position = info.position;
                menu.add(0, v.getId(), 0, "delete this tour").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Tour tour = myTours.get(position);
                        List<City> cities = Tour.find(City.class, "TOUR = ?", String.valueOf(tour.getId()));
                        List<Accommodation> accommodations = null;
                        for(int i = 0; i < cities.size(); i++){
                            Accommodation.find(Accommodation.class, "CITY = ?", String.valueOf(cities.get(i).getId())).get(0).delete();
                            cities.get(i).delete();
                        }
                        myTours.remove(position);
                        tour.delete();
                        adapter.notifyDataSetChanged();
                        return true;
                    }
                });

            }
        });
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

        adapter = new TrawellArrayAdapter(getContext(), R.layout.tour_item, (ArrayList) myTours, Tour.class);
        myToursLV.setAdapter(adapter);
        // Inflate the layout for this fragment
        return view;
    }

}
