package de.jadehs.trawell.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import de.jadehs.trawell.models.ItemAdapter;

import com.woxthebox.draglistview.DragListView;

import java.util.ArrayList;

import de.jadehs.trawell.R;

public class OrganizeTravelFragment extends Fragment {

    private ArrayList<Pair<Long, String>> mItemArray;
    private DragListView citiesListView;
    private ArrayList<String> cities;
    ArrayAdapter<String> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Organize your Tour");

        View view = inflater.inflate(R.layout.fragment_organize_travel, container, false);

        citiesListView = (DragListView) view.findViewById(R.id.citiesListView);
        citiesListView.setDragListListener(new DragListView.DragListListener() {
            @Override
            public void onItemDragStarted(int position) {
                //Toast.makeText(getActivity(), "Start - position: " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemDragging(int itemPosition, float x, float y) {
            }

            @Override
            public void onItemDragEnded(int fromPosition, int toPosition) {
                if (fromPosition != toPosition) {
                    //Toast.makeText(getActivity(), "End - position: " + toPosition, Toast.LENGTH_SHORT).show();
                }
            }
        });

        mItemArray = new ArrayList<>();
        mItemArray.add(new Pair<>((long) 0, "Oldenburg"));
        mItemArray.add(new Pair<>((long) 1, "Prag"));
        mItemArray.add(new Pair<>((long) 2, "Budapest"));
        mItemArray.add(new Pair<>((long) 3, "Rom"));
        mItemArray.add(new Pair<>((long) 4, "Mailand"));
        mItemArray.add(new Pair<>((long) 5, "Madrid"));
        mItemArray.add(new Pair<>((long) 6, "Lissabon"));
        mItemArray.add(new Pair<>((long) 7, "Paris"));
        mItemArray.add(new Pair<>((long) 8, "Oldenburg"));


        citiesListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ItemAdapter listAdapter = new ItemAdapter(mItemArray, R.layout.list_item, R.id.image, false);
        citiesListView.setAdapter(listAdapter, true);
        citiesListView.setCanDragHorizontally(false);
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
        return view;
    }

}
