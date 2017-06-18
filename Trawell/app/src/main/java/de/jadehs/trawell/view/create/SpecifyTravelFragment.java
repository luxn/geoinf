package de.jadehs.trawell.view.create;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.jadehs.trawell.R;

import static de.jadehs.trawell.view.create.NewTourActivity.graph;
import static de.jadehs.trawell.view.create.NewTourActivity.ticketId;
import static de.jadehs.trawell.view.create.NewTourActivity.tour;

public class SpecifyTravelFragment extends Fragment {

    Button nextBTN;
    EditText startET, endET, durationET;
    AutoCompleteTextView startCityTV, finalCityTV;
    Calendar myCalendar = Calendar.getInstance();
    String[] cities = new String[graph.getLocations().size()];

    private final String dateFormat = "dd/MM/yyyy";
    private final SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.GERMAN);
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        getActivity().setTitle("Specify your travel");

        View view = inflater.inflate(R.layout.fragment_specify_travel, container, false);

        endET = (EditText) view.findViewById((R.id.endEditText));

        durationET = (EditText) view.findViewById(R.id.durationET);
        durationET.setEnabled(false);

        if(ticketId >= 0){
            endET.setEnabled(false);
            if(ticketId == 0){
                durationET.setText("7");
            } else if (ticketId == 1){
                durationET.setText("10");
            } else if(ticketId == 2){
                durationET.setText("15");
            }
        }

        for (int i = 0; i < graph.getLocations().size(); i++) {
            cities[i] = graph.getLocations().get(i).toString();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getContext(), android.R.layout.select_dialog_singlechoice, cities);

        startCityTV = (AutoCompleteTextView) view.findViewById(R.id.startCityTextView);
        startCityTV.setThreshold(1);
        startCityTV.setAdapter(adapter);
        startCityTV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                fieldsAreFilledOut();
            }
        });

        finalCityTV = (AutoCompleteTextView) view.findViewById(R.id.finalCityTextView);
        finalCityTV.setThreshold(1);
        finalCityTV.setAdapter(adapter);
        finalCityTV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                fieldsAreFilledOut();
            }

            @Override
            public void afterTextChanged(Editable s) {
                fieldsAreFilledOut();
            }
        });

        startET = (EditText) view.findViewById((R.id.startEditText));
        startET.setOnClickListener(new View.OnClickListener() {
            DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    // TODO Auto-generated method stub
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateLabel(startET);
                }
            };

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(SpecifyTravelFragment.this.getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        endET.setOnClickListener(new View.OnClickListener() {
            DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    // TODO Auto-generated method stub
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateLabel(endET);
                }
            };

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(SpecifyTravelFragment.this.getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        nextBTN = (Button) view.findViewById(R.id.newTourNextBTN);
        nextBTN.setEnabled(false);
        nextBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // Date Format
                    //Specify the temporary tour
                    tour.setStartCity(startCityTV.getText().toString());
                    tour.setFinalCity(finalCityTV.getText().toString());
                    tour.setStart(sdf.parse(startET.getText().toString()));
                    tour.setEnd(sdf.parse(endET.getText().toString()));
                    tour.setDuration(Integer.parseInt(durationET.getText().toString()));
                    // next Step --> select cities
                    NewTourActivity.goTo(SelectCitiesFragment.class);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (java.lang.InstantiationException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    public void fieldsAreFilledOut(){
        if(endET.getText().toString().equals("") || startET.getText().toString().equals("") ||
                finalCityTV.getText().toString().equals("") || startCityTV.getText().toString().equals("")){
            nextBTN.setEnabled(false);
        } else {
            List<String> c = Arrays.asList(cities);
            if(c.contains(startCityTV.getText().toString()) &&
                     c.contains(finalCityTV.getText().toString())) {
                nextBTN.setEnabled(true);
                Log.d("cityinlist","true");
            } else {

                nextBTN.setEnabled(false);
                Toast.makeText(getContext(), "One of your chosen cities is not available",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void autoInsert(EditText editText) {
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = sdf.parse(startET.getText().toString());
            endDate = sdf.parse(endET.getText().toString());
        } catch (ParseException e) {
        }
        int diffInDays = (int) ((endDate.getTime() - startDate.getTime())
                / (1000 * 60 * 60 * 24)) + 1;
        if(diffInDays > 30){
            Date maxEnd = null;
            try {
                maxEnd = sdf.parse(startET.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar c = Calendar.getInstance();
            c.setTime(maxEnd);
            c.add(Calendar.DATE, 30);
            maxEnd = c.getTime();
            endET.setText(sdf.format(new Date(maxEnd.getTime())));
            editText.setText("30");
            Toast.makeText(getContext(), "Can not take longer than 30 days!",Toast.LENGTH_SHORT).show();
        } else {
            editText.setTextColor(Color.BLACK);
            editText.setText(Integer.toString(diffInDays));
        }
    }


    private void updateLabel(EditText editText) {
        if(ticketId < 0) {

            editText.setText(sdf.format(myCalendar.getTime()));
            if (!startET.getText().toString().equals("") && !endET.getText().toString().equals("")) {
                Date startDate = null;
                Date endDate = null;
                try {
                    startDate = sdf.parse(startET.getText().toString());
                    endDate = sdf.parse(endET.getText().toString());
                } catch (ParseException e) {
                }
                int diffInDays = (int) ((startDate.getTime() - endDate.getTime())
                        / (1000 * 60 * 60 * 24)) + 1;
                if (diffInDays < 1)
                    editText.setText(sdf.format(myCalendar.getTime()));
                else {
                    endET.setText("");
                    durationET.setText("");
                    Toast.makeText(getContext(), "Startdate cannot be later than enddate!", Toast.LENGTH_SHORT).show();
                }
            }
            if (!startET.getText().toString().equals("") && !endET.getText().toString().equals(""))
                autoInsert(durationET);
        } else {
            editText.setText(sdf.format(myCalendar.getTime()));
            Date end = myCalendar.getTime();
            Calendar c = Calendar.getInstance();
            c.setTime(end);
            c.add(Calendar.DATE, Integer.parseInt(durationET.getText().toString())-1);
            end = c.getTime();
            endET.setText(sdf.format(new Date(end.getTime())));
        }
        fieldsAreFilledOut();
    }
}
