package de.jadehs.trawell.view.tours;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import de.jadehs.trawell.R;
import de.jadehs.trawell.miscellaneous.TrawellArrayAdapter;
import de.jadehs.trawell.models.Accommodation;
import de.jadehs.trawell.models.City;
import de.jadehs.trawell.models.Tour;
import de.jadehs.trawell.view.create.AccommodationsFragment;
import de.jadehs.trawell.view.home.MainActivity;

import static de.jadehs.trawell.view.home.MainActivity.tourId;

public class TourDetailFragment extends Fragment {

    private Button chooseAccoBTN;
    private ListView tourdetailLV;
    private TrawellArrayAdapter<City> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tour, container, false);

        Tour tour = Tour.findById(Tour.class, tourId);

        getActivity().setTitle(""+tour.getStartCity() + " - " + tour.getFinalCity());

        List<City> city = Tour.find(City.class, "TOUR = ?", String.valueOf(tour.getId()));

        List<Accommodation> accommodations = new ArrayList<>();

        for(City c : city){
            List<Accommodation> acco = Accommodation.find(Accommodation.class, "CITY = ?", String.valueOf(c.getId()));
            if(acco.isEmpty()) {
                Log.d("city", "" + c.getName());
                Log.d("acco", "NoAcco");
            } else {
                accommodations.add(acco.get(0));
                Log.d("city", ""+c.getName());
                Log.d("acco", ""+acco.get(0).getName());
            }
        }

        tourdetailLV = (ListView) view.findViewById(R.id.tourDetailLV);
        adapter = new TrawellArrayAdapter<>(getContext(),R.layout.tour_overview_item, (ArrayList) city, City.class);
        tourdetailLV.setAdapter(adapter);



        chooseAccoBTN = (Button) view.findViewById(R.id.chooseAccoBTN);
        chooseAccoBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    MainActivity.goTo(AccommodationsFragment.class);
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
