/*
 * Copyright 2014 Magnus Woxblom
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.jadehs.trawell.miscellaneous;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v4.util.Pair;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.woxthebox.draglistview.DragItemAdapter;

import java.util.ArrayList;

import de.jadehs.trawell.R;
import de.jadehs.trawell.graph.TrawellGraph;
import de.jadehs.trawell.models.City;
import de.jadehs.trawell.view.create.NewTourActivity;

public class ItemAdapter extends DragItemAdapter<Pair<Long, String>, ItemAdapter.ViewHolder> {

    private int mLayoutId;
    private int mGrabHandleId;
    private boolean mDragOnLongPress;
    private Long id;
    private Context context;
    private ViewHolder holder;
    private TrawellGraph graph;
    private View contextView;
    private ArrayList<City> cities = NewTourActivity.cities;

    public ItemAdapter(ArrayList<Pair<Long, String>> list, int layoutId, int grabHandleId, boolean dragOnLongPress) {
        mLayoutId = layoutId;
        mGrabHandleId = grabHandleId;
        mDragOnLongPress = dragOnLongPress;
        context = NewTourActivity.context;
        graph = TrawellGraph.get(context);
        contextView = NewTourActivity.view;
        setHasStableIds(true);
        setItemList(list);
    }
    public int checkDuration(){
        int duration = 0;
        for (City city : cities){
            duration += city.getDuration();
        }
        return duration;
    }

    public void changeIndices(City clickedCity, int position){
        City city = cities.get(position);
        int clickedCityPos = cities.indexOf(clickedCity);
        cities.set(position, clickedCity);
        cities.set(clickedCityPos, city);
    }

    public City getCityByName(ArrayList<City> cities, String name){
        for (City city : cities){
            if(city.getName().equals(name)){
                return city;
            }
        }
        return null;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(mLayoutId, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        final Button chooseAcco = (Button) contextView.findViewById(R.id.chooseAccoBTN2);
        final Button save = (Button) contextView.findViewById(R.id.saveBTN);

        String cityName = mItemList.get(position).second;
        final City city = getCityByName(cities, cityName);

        // finalCity and startCity have no duration
        if(position == 0 || position == cities.size()-1)
            holder.durationLay.setVisibility(View.INVISIBLE);

        // change position
        changeIndices(city, position);
        //DEBUG
//        Log.d("cityId",""+city.getName()+" "+cities.indexOf(city));

        // get current duration
        String duration = String.valueOf(cities.get(position).getDuration());
        holder.mText.setText(cityName);
        holder.mDuration.setText(duration);
        holder.downButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String durationString = String.valueOf(holder.mDuration.getText());
                int duration = Integer.parseInt(durationString);
                if(duration > 0) {
                    duration--;
                    chooseAcco.setEnabled(false);
                    save.setEnabled(false);
                }
                holder.mDuration.setText(String.valueOf(duration));
                cities.get(position).setDuration(duration);
            }
        });
        holder.upButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String durationString = String.valueOf(holder.mDuration.getText());
                int duration = Integer.parseInt(durationString);
                Log.d("duration", ""+city.getTour().getDuration());
                if(duration < 15 && city.getTour().getDuration() > checkDuration()) {
                    duration++;
                    chooseAcco.setEnabled(false);
                    save.setEnabled(false);
                } else {
                    Toast.makeText(context, "You have just " + city.getTour().getDuration() + " days!", Toast.LENGTH_SHORT).show();
                    chooseAcco.setEnabled(true);
                    save.setEnabled(true);
                }
                holder.mDuration.setText(String.valueOf(duration));
                cities.get(position).setDuration(duration);
            }
        });
        holder.itemView.setTag(mItemList.get(position));
    }

    @Override
    public long getItemId(int position) {
        return mItemList.get(position).first;
    }

    public class ViewHolder extends DragItemAdapter.ViewHolder {
        public TextView mText;
        public TextView mDuration;
        public ImageButton upButton, downButton;
        public ConstraintLayout durationLay;

        public ViewHolder(final View itemView) {
            super(itemView, mGrabHandleId, mDragOnLongPress);
            mText = (TextView) itemView.findViewById(R.id.text);
            mDuration = (TextView) itemView.findViewById(R.id.durationTV);
            upButton = (ImageButton) itemView.findViewById(R.id.upBTN);
            downButton = (ImageButton) itemView.findViewById(R.id.downBTN);
            durationLay = (ConstraintLayout) itemView.findViewById(R.id.durationLayout);

        }

        @Override
        public void onItemClicked(View view) {
//            OrganizeTravelFragment.changeDurationForItem(getAdapterPosition());
        }

        @Override
        public boolean onItemLongClicked(View view) {
            //Toast.makeText(view.getContext(), "Item long clicked", Toast.LENGTH_SHORT).show();
            return true;
        }
    }
}