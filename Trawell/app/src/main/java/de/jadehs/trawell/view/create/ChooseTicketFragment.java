package de.jadehs.trawell.view.create;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.SupportActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import de.jadehs.trawell.R;
import de.jadehs.trawell.view.create.NewTourActivity;

public class ChooseTicketFragment extends Fragment {

    private RadioGroup radioGroup;
    private Button nextBTN, withoutTicketBTN;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_choose_ticket, container, false);

        radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                withoutTicketBTN.setEnabled(false);
                nextBTN.setEnabled(true);
            }
        });

        withoutTicketBTN = (Button) view.findViewById(R.id.withoutTicketBTN);
        withoutTicketBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    NewTourActivity.ticketId = -1;
                    NewTourActivity.goTo(SpecifyTravelFragment.class);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (java.lang.InstantiationException e) {
                    e.printStackTrace();
                }
            }
        });

        nextBTN = (Button) view.findViewById(R.id.ticketNextBTN);
        nextBTN.setEnabled(false);
        nextBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int radioButtonId = radioGroup.getCheckedRadioButtonId();
                    NewTourActivity.ticketId = radioGroup.indexOfChild(radioGroup.findViewById(radioButtonId));
                    NewTourActivity.goTo(SpecifyTravelFragment.class);
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
