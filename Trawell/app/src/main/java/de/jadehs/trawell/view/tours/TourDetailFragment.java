package de.jadehs.trawell.view.tours;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import de.jadehs.trawell.R;
import de.jadehs.trawell.models.City;
import de.jadehs.trawell.models.Tour;
import de.jadehs.trawell.view.create.AccommodationsFragment;

public class TourDetailFragment extends Fragment {

    private Button chooseAccoBTN;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d("exTourId-Fragment", ""+ TourActivity.exTourId);
        Tour tour = Tour.findById(Tour.class, new Long(TourActivity.exTourId));
        List<City> city = new ArrayList<>();
        city = Tour.find(City.class, "TOUR_ID =" + tour.getId());
        getActivity().setTitle(""+tour.getStartCity() + " - " + tour.getFinalCity());

        for(int i = 0; i < city.size();i++){
            Log.d("city",""+city.get(i).getName());
        }

        View view = inflater.inflate(R.layout.fragment_tour, container, false);

        chooseAccoBTN = (Button) view.findViewById(R.id.chooseAccoBTN);
        chooseAccoBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    TourActivity.goTo(AccommodationsFragment.class);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (java.lang.InstantiationException e) {
                    e.printStackTrace();
                }
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

}
