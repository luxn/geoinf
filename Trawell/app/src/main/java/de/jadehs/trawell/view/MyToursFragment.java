package de.jadehs.trawell.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import de.jadehs.trawell.R;
import de.jadehs.trawell.models.Tour;
import de.jadehs.trawell.view.HomeFragment;
import de.jadehs.trawell.view.MainActivity;

import static de.jadehs.trawell.models.BaseModel.myTours;

public class MyToursFragment extends Fragment {

    public ListView myToursLV;
    public ArrayAdapter<Tour> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("My Tours");

        View view = inflater.inflate(R.layout.fragment_my_tours, container, false);

        myToursLV = (ListView) view.findViewById(R.id.myToursList);
        adapter = new ArrayAdapter<Tour>(getContext(),android.R.layout.simple_list_item_1,myTours);
        myToursLV.setAdapter(adapter);
        // Inflate the layout for this fragment
        return view;
    }

}
