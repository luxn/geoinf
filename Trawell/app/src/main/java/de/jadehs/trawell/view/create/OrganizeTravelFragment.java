package de.jadehs.trawell.view.create;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.woxthebox.draglistview.DragListView;

import java.util.ArrayList;

import de.jadehs.trawell.R;
import de.jadehs.trawell.miscellaneous.ItemAdapter;
import de.jadehs.trawell.models.City;
import de.jadehs.trawell.models.Tour;
import de.jadehs.trawell.view.home.MainActivity;

public class OrganizeTravelFragment extends Fragment {

    private static ArrayList<Pair<Long, String>> mItemArray;
    private DragListView citiesListView;
    private static ItemAdapter listAdapter;
    private ArrayList<City> cities;
    private Tour tour;

    private Button save, chooseAccoBTN;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Organize your tour");

        View view = inflater.inflate(R.layout.fragment_organize_travel, container, false);

        NewTourActivity.setView(view);
        NewTourActivity.setContext(getContext());
        cities = NewTourActivity.getCities();
        tour = NewTourActivity.getTour();

        citiesListView = (DragListView) view.findViewById(R.id.citiesListView);

        mItemArray = new ArrayList<>();
        for(int i = 0; i < cities.size(); i++){
            String city = cities.get(i).getName();
            mItemArray.add(new Pair<>((long) i, city ));

        }

        citiesListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        listAdapter = new ItemAdapter(mItemArray, R.layout.list_item, R.id.item_layout, false);
        citiesListView.setAdapter(listAdapter, true);
        citiesListView.setCanDragHorizontally(false);
        // Start and final location cannot be changed
        citiesListView.setCanNotDragAboveTopItem(true);
        citiesListView.setCanNotDragBelowBottomItem(true);

        save = (Button) view.findViewById(R.id.saveBTN);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                int rest = tour.getDuration() % cities.size();
//                int duration = tour.getDuration() / cities.size();
                // Save the temporary tour in the database
                tour.save();
//                newTourId = tour.getId().intValue();
                // Save the selected cities in the database
                for(int i = 0; i < cities.size(); i++) {
                    cities.get(i).save();
                    Log.d("tour", ""+cities.get(i).getTour());
                }
                Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        chooseAccoBTN = (Button) view.findViewById(R.id.chooseAccoBTN2);
        chooseAccoBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Save the temporary tour in the database
                tour.save();
//                newTourId = tour.getId().intValue();
                // Save the selected cities in the database
                for(int i = 0; i < cities.size(); i++) {
                    cities.get(i).save();
                }
                try {
                    NewTourActivity.setTourId(tour.getId());
                    NewTourActivity.goTo(AccommodationsFragment.class);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (java.lang.InstantiationException e) {
                    e.printStackTrace();
                }
            }
        });
        if(tour.getDuration() > checkDuration()) {
            chooseAccoBTN.setEnabled(false);
            save.setEnabled(false);
        }

        return view;
    }

    public int checkDuration(){
        int duration = 0;
        for (City city : cities){
            duration += city.getDuration();
        }
        return duration;
    }
}
