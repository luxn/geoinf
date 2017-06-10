package de.jadehs.trawell.view.create;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.woxthebox.draglistview.DragListView;

import java.util.ArrayList;

import de.jadehs.trawell.R;
import de.jadehs.trawell.database.DBCity;
import de.jadehs.trawell.miscellaneous.ItemAdapter;
import de.jadehs.trawell.view.home.MainActivity;

import static de.jadehs.trawell.view.create.NewTourActivity.cities;
import static de.jadehs.trawell.view.create.NewTourActivity.newTourId;
import static de.jadehs.trawell.view.create.NewTourActivity.tour;

public class OrganizeTravelFragment extends Fragment {

    private static ArrayList<Pair<Long, String>> mItemArray;
    private DragListView citiesListView;
    private static ItemAdapter listAdapter;
    private static AlertDialog.Builder builder;
    private static LinearLayout ll;

    private Button save, chooseAccoBTN;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onSaveInstanceState(savedInstanceState);
        getActivity().setTitle("Organize your tour");

        View view = inflater.inflate(R.layout.fragment_organize_travel, container, false);

        ll = (LinearLayout) view.findViewById(R.id.item_layout);

        citiesListView = (DragListView) view.findViewById(R.id.citiesListView);

        mItemArray = new ArrayList<>();
        for(int i = 0; i < cities.size(); i++){
            String city = cities.get(i);
            mItemArray.add(new Pair<>((long) i, city ));
        }

        citiesListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        listAdapter = new ItemAdapter(mItemArray, R.layout.list_item, R.id.item_layout, false);
        citiesListView.setAdapter(listAdapter, true);
        citiesListView.setCanDragHorizontally(false);
        citiesListView.setScrollingEnabled(false);
        // Start and final location cannot be changed
        citiesListView.setCanNotDragAboveTopItem(true);
        citiesListView.setCanNotDragBelowBottomItem(true);

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

        builder = new AlertDialog.Builder(this.getContext());

        save = (Button) view.findViewById(R.id.saveBTN);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                int rest = tour.getDuration() % cities.size();
//                int duration = tour.getDuration() / cities.size();
                // Save the temporary tour in the database
                tour.save();
                newTourId = tour.getId().intValue();
                // Save the selected cities in the database
                for(int i = 0; i < cities.size(); i++) {
                    DBCity city = new DBCity(cities.get(i), tour.getId());
                    city.save();
                }
                Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        chooseAccoBTN = (Button) view.findViewById(R.id.chooseAccoBTN2);
        chooseAccoBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Save the temporary tour in the database
                tour.save();
                newTourId = tour.getId().intValue();
                // Save the selected cities in the database
                for(int i = 0; i < cities.size(); i++) {
                    DBCity city = new DBCity(cities.get(i), tour.getId());
                    city.save();
                }
                try {
                    NewTourActivity.goTo(AccommodationsFragment.class);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (java.lang.InstantiationException e) {
                    e.printStackTrace();
                }
            }
        });

        return view;
    }

    public static void changeDurationForItem(final int position) {
        final String city =  mItemArray.get(position).second.substring(0, mItemArray.get(position).second.length()-11);
        final CharSequence options[] = new CharSequence[] {"1 day(s)", "2 day(s)", "3 day(s)", "4 day(s)", "5 day(s)", "6 day(s)", "7 day(s)" };
        builder.setTitle("Change duration for "+city);
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                final String city =  mItemArray.get(position).second.substring(0, mItemArray.get(position).second.length()-11);
                mItemArray.set(position, new Pair<>((long) position, city + " - " + options[which]));
                listAdapter.notifyDataSetChanged();
            }
        });
        builder.show();

    }
}
