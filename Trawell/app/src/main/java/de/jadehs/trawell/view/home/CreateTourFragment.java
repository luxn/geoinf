package de.jadehs.trawell.view.home;

import android.content.Intent;
import android.graphics.Color;
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
import de.jadehs.trawell.view.create.NewTourActivity;

public class CreateTourFragment extends Fragment {

    TextView textView;
    Button newTourButton;
    ImageView interrailImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_new_tour, container, false);

        textView = (TextView) view.findViewById(R.id.textView);
        textView.setText("This easy steps will help you to create the perfect tour for you: \n" +
                "First you will choose what kind of interrail ticket you have, you can continue without having one.\nSecond you specify your trip.\n"+
                "The Next step is to select the cities you want to visit on the map." +
                " You can change the order of the cities and select how long you want to stay in each city on the next screen.\n" +
                "Now save the tour you have created or choose a place to stay in one of the cities, you will be able to edit " +
                "your lodgings in the MyTours section of the app at any point.");

        newTourButton = (Button) view.findViewById(R.id.newTourButton);
        newTourButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                Intent intent = new Intent(getActivity().getApplicationContext(), NewTourActivity.class);
                startActivity(intent);
            }

        });

        interrailImage = (ImageView) view.findViewById(R.id.interrailImageView);

        interrailImage.setOnClickListener(new View.OnClickListener(){

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
