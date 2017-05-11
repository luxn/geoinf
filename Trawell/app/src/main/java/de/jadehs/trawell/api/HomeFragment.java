package de.jadehs.trawell.api;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import de.jadehs.trawell.R;

public class HomeFragment extends Fragment {

    Button newTour;
    Button myTour;
    ImageButton setting;
    ImageButton information;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // NewTourBTN
        newTour = (Button) view.findViewById(R.id.newTourBTN);

        newTour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    MainActivity.goTo(InterrailFragment.class);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (java.lang.InstantiationException e) {
                    e.printStackTrace();
                }
            }
        });


        //MyTourBTN
        myTour = (Button) view.findViewById(R.id.myTourBTN);

        myTour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    MainActivity.goTo(MyTourFragment.class);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (java.lang.InstantiationException e) {
                    e.printStackTrace();
                }
            }
        });

        // Setting BTN
        Log.d("HomeFragment", "in goToHighscore Methode");

        setting = (ImageButton) view.findViewById(R.id.settingBTN);

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("HomeFragment", "in lambda");
                Log.d("HomeFragment", "in lambda");

                try {
                    MainActivity.goTo(SettingFragment.class);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (java.lang.InstantiationException e) {
                    e.printStackTrace();
                }
            }
        });

        // Information BTN fehlt noch

        // Inflate the layout for this fragment
        return view;
    }

}
