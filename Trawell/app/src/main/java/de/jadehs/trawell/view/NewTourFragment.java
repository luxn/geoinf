package de.jadehs.trawell.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import de.jadehs.trawell.R;

public class NewTourFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("New Tour");

        View view = inflater.inflate(R.layout.fragment_new_tour, container, false);

        // Inflate the layout for this fragment
        return view;
    }

}
