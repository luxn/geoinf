package de.jadehs.trawell.view.home;

import android.content.Intent;
import android.graphics.Color;
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

public class NewTourFragment extends Fragment {

    TextView textView;
    Button newTourButton;
    ImageView interrailImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_new_tour, container, false);

        textView = (TextView) view.findViewById(R.id.textView);
        textView.setText("You want to travel to Europe's most amazing places by train? Then click the button below and plan your Interrail tour.");

        newTourButton = (Button) view.findViewById(R.id.newTourButton);
        newTourButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                Intent intent = new Intent(getActivity().getApplicationContext(), NewTourActivity.class);
                startActivity(intent);
            }

        });

        interrailImage = (ImageView) view.findViewById(R.id.interrailImageView);
        interrailImage.setBackgroundColor(Color.rgb(255, 255, 255));

        // Inflate the layout for this fragment
        return view;
    }

}
