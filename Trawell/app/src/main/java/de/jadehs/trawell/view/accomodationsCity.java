package de.jadehs.trawell.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;


import java.util.ArrayList;
import java.util.List;

import de.jadehs.trawell.R;
import de.jadehs.trawell.api.GooglePlaces;
import de.jadehs.trawell.models.Accomodation;
import de.jadehs.trawell.models.ArrayAdapterString;
import de.jadehs.trawell.models.City;


import static de.jadehs.trawell.view.AccommodationsFragment.aktuelleLocationID;
import static de.jadehs.trawell.view.NewTourActivity.tour;


public class accomodationsCity extends Fragment {

    // die aktuelle Location aus dem AccomodationsFragment
    int id =aktuelleLocationID;
    ArrayList city;
    ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Choose your accommodation");

        View view = inflater.inflate(R.layout.fragment_accommodations, container, false);

        // List View einladen
        listView = (ListView) view.findViewById(R.id.listView);

        // Die Übergabe der Lon und Lat der Stadt
        double lon = tour.getCities().get(id).getLocation().getLongitude();
        double lat = tour.getCities().get(id).getLocation().getLatitude();

        // Lässt sich alle Hotels im umkreis von 4000m geben
        List<Accomodation> list ;
        list =GooglePlaces.getLodgingFrom(lon,lat,4000);

        // Schreibt alle Hotels in die Liste
        final ArrayList<Accomodation> mItemArray;
        mItemArray = new ArrayList<Accomodation>();
        for(int i = 0; i < list.size(); i++){
            String adresse = list.get(i).getAdresse();
            String name = list.get(i).getName();
            double bewertung = list.get(i).getBewertung();
            mItemArray.add(new Accomodation(name, bewertung, adresse));
        }


        // Ein Adapter wie die Hotels jetzt angezeigt werden
        ArrayAdapterString listAdapter = new ArrayAdapterString( view.getContext(),view.getId(),mItemArray);
        listView.setAdapter((ListAdapter) listAdapter);
        listView.setHorizontalScrollBarEnabled(true);
        listView.setVerticalScrollBarEnabled(true);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Speichern in die City das Hotel
                City a = (City) city.get(position);
                a.setAccommodation(mItemArray.get(position).getName());

                try {
                    MainActivity.goTo(accomodationsCity.class );
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (java.lang.InstantiationException e) {
                    e.printStackTrace();
                }


            }
        });



        return view;
    }


}
