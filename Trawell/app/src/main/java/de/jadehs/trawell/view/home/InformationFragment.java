package de.jadehs.trawell.view.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import de.jadehs.trawell.R;

public class InformationFragment extends Fragment {

    TextView informations;
    ImageView interrailIcon;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Interrail Informations");

        View view = inflater.inflate(R.layout.fragment_information, container, false);

        informations = (TextView) view.findViewById(R.id.informationsTextView);
        informations.setText("You want to travel to Europe's most amazing cities by train? " +
                "With the interrail pass you have chosen the right ticket for this occasion. " +
                "The Interrail Pass is a train ticket that allows you to travel on almost all " +
                "trains in Europe. With it, you get access to 37 railway and ferry companies in " +
                "30 countries. \n\nFor even more informations click on the interrail logo to visit " +
                "the interrail website.\n" +
                "Have fun planing your tours and experiencing the whole of europe!");

        interrailIcon = (ImageView) view.findViewById(R.id.informationInterrail);

        interrailIcon.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://www.interrail.eu/de"));
                startActivity(intent);

            }

        });
        // Inflate the layout for this fragment
        return view;
    }

}
