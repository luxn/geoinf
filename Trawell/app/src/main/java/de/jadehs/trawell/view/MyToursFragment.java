package de.jadehs.trawell.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import de.jadehs.trawell.R;
import de.jadehs.trawell.view.HomeFragment;
import de.jadehs.trawell.view.MainActivity;

public class MyToursFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("My Tours");

        View view = inflater.inflate(R.layout.fragment_my_tours, container, false);

        // Inflate the layout for this fragment
        return view;
    }

}
