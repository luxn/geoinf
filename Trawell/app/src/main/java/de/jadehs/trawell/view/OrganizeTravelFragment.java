package de.jadehs.trawell.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import de.jadehs.trawell.R;

public class OrganizeTravelFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Organize your Tour");

        View view = inflater.inflate(R.layout.fragment_organize_travel, container, false);

        //ready = (Button) view.findViewById(R.id.readyBTN);

//        ready.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//                    MainActivity.goTo(HomeFragment.class);
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                } catch (InstantiationException e) {
//                    e.printStackTrace();
//                } catch (java.lang.InstantiationException e) {
//                    e.printStackTrace();
//                }
//            }
//        });

        // housing BTN noch nicht initialisiert

        // Inflate the layout for this fragment
        return view;
    }

}
