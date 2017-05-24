package de.jadehs.trawell.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import de.jadehs.trawell.models.ItemAdapter;

import com.google.android.gms.vision.text.Text;
import com.woxthebox.draglistview.DragListView;

import java.util.ArrayList;

import de.jadehs.trawell.R;

public class OrganizeTravelFragment extends Fragment {

    private static ArrayList<Pair<Long, String>> mItemArray;
    private DragListView citiesListView;
    private static ItemAdapter listAdapter;
    private TextView cityTextView;
    private static AlertDialog.Builder builder;
    private static LinearLayout ll;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Organize your Tour");

        View view = inflater.inflate(R.layout.fragment_organize_travel, container, false);

        ll = (LinearLayout) view.findViewById(R.id.item_layout);

        citiesListView = (DragListView) view.findViewById(R.id.citiesListView);
        citiesListView.setDragListListener(new DragListView.DragListListener() {
            @Override
            public void onItemDragStarted(int position) {
                //Toast.makeText(getActivity(), "Start - position: " + position, Toast.LENGTH_SHORT).show();
                //Toast.makeText(getActivity(), ""+mItemArray[""], Toast.LENGTH_SHORT).show();
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
        mItemArray.add(new Pair<>((long) 0, "Oldenburg - 2 day(s)"));
        mItemArray.add(new Pair<>((long) 1, "Prag - 2 day(s)"));
        mItemArray.add(new Pair<>((long) 2, "Budapest - 2 day(s)"));
        mItemArray.add(new Pair<>((long) 3, "Rom - 2 day(s)"));
        mItemArray.add(new Pair<>((long) 4, "Mailand - 2 day(s)"));
        mItemArray.add(new Pair<>((long) 5, "Madrid - 2 day(s)"));
        mItemArray.add(new Pair<>((long) 6, "Lissabon - 2 day(s)"));
        mItemArray.add(new Pair<>((long) 7, "Paris - 2 day(s)"));
        mItemArray.add(new Pair<>((long) 8, "Oldenburg - 2 day(s)"));

        citiesListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        listAdapter = new ItemAdapter(mItemArray, R.layout.list_item, R.id.up_down_image, false);
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

        builder = new AlertDialog.Builder(this.getContext());;

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
