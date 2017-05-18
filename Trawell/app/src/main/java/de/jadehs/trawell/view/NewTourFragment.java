package de.jadehs.trawell.view;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import de.jadehs.trawell.R;

public class NewTourFragment extends Fragment {

    Button nextBTN, homeBTN;
    EditText startET, endET, durationET;
    AutoCompleteTextView startCityTV, finalCityTV;
    Calendar myCalendar = Calendar.getInstance();
    String[] cities = {
            "Brüssel",
            "Antwerpen",
            "Sarajevo",
            "Sofia",
            "Kopenhagen",
            "Berlin",
            "München",
            "Köln",
            "Hamburg",
            "Helsinki",
            "Paris",
            "Lille",
            "Marseille",
            "Lyon",
            "Bordeaux",
            "Athen",
            "London",
            "Edinburgh",
            "Manchester",
            "Newcastle",
            "Zagreb (Agram)",
            "Luxemburg",
            "Skopje",
            "Podgorica",
            "Oslo",
            "Wien",
            "Salzburg",
            "Lissabon",
            "Porto",
            "Bukarest",
            "Stockholm",
            "Bern",
            "Basel",
            "Zürich",
            "Belgrad",
            "Bratislava (Pressburg)",
            "Ljubljana (Laibach)",
            "Madrid",
            "Barcelona",
            "Sevilla",
            "Ankara",
            "Budapest"
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Specify your travel");

        View view = inflater.inflate(R.layout.fragment_new_tour, container, false);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.select_dialog_singlechoice, cities);

        durationET = (EditText) view.findViewById(R.id.durationEditText);
        durationET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (!startET.getText().toString().equals("") && !endET.getText().toString().equals("")) {
                        autoInsert(durationET);
                    }
                } else {
                    Toast.makeText(NewTourFragment.this.getContext(), "lost focus", Toast.LENGTH_SHORT).show();
                }
            }
        });

        startCityTV = (AutoCompleteTextView) view.findViewById(R.id.startCityTextView);
        startCityTV.setThreshold(1);
        startCityTV.setAdapter(adapter);

        finalCityTV = (AutoCompleteTextView) view.findViewById(R.id.finalCityTextView);
        finalCityTV.setThreshold(1);
        finalCityTV.setAdapter(adapter);

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
                new DatePickerDialog(NewTourFragment.this.getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        endET = (EditText) view.findViewById((R.id.endEditText));
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
                new DatePickerDialog(NewTourFragment.this.getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        nextBTN = (Button) view.findViewById(R.id.newTourNextBTN);
        nextBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* use later... if (!startET.getText().toString().equals("") &&
                        !endET.getText().toString().equals("") &&
                        !durationET.getText().toString().equals("") &&
                        !startCityTV.getText().toString().equals("") &&
                        !finalCityTV.getText().toString().equals("")) */
                    try {
                        NewTourActivity.goTo(SelectCitiesFragment.class);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (java.lang.InstantiationException e) {
                        e.printStackTrace();
                    }

            }
        });

        homeBTN = (Button) view.findViewById(R.id.homeBTN);
        homeBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void autoInsert(EditText editText) {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.GERMAN);
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = sdf.parse(startET.getText().toString());
            endDate = sdf.parse(endET.getText().toString());
        } catch (ParseException e) {
        }
        int diffInDays = (int) ((endDate.getTime() - startDate.getTime())
                / (1000 * 60 * 60 * 24));
        editText.setText(Integer.toString(diffInDays));
    }


    private void updateLabel(EditText editText) {

        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.GERMAN);
        editText.setText(sdf.format(myCalendar.getTime()));
    }
}
