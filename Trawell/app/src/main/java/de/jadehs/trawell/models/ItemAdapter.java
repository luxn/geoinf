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

package de.jadehs.trawell.models;

import android.graphics.Color;
import android.support.v4.util.Pair;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.ImageButton;
import android.widget.TextView;

import com.woxthebox.draglistview.DragItemAdapter;

import java.util.ArrayList;

import de.jadehs.trawell.R;
import de.jadehs.trawell.view.OrganizeTravelFragment;

import static de.jadehs.trawell.view.NewTourActivity.cities;
import static de.jadehs.trawell.view.NewTourActivity.tour;

public class ItemAdapter extends DragItemAdapter<Pair<Long, String>, ItemAdapter.ViewHolder> {

    private int mLayoutId;
    private int mGrabHandleId;
    private boolean mDragOnLongPress;
    private Long id;
    private ViewHolder holder;

    public ItemAdapter(ArrayList<Pair<Long, String>> list, int layoutId, int grabHandleId, boolean dragOnLongPress) {
        mLayoutId = layoutId;
        mGrabHandleId = grabHandleId;
        mDragOnLongPress = dragOnLongPress;
        setHasStableIds(true);
        setItemList(list);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(mLayoutId, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        String text = mItemList.get(position).second;
        id = getItemId(position);
        String duration = "0";
        holder.mText.setText(text);
        holder.mDuration.setText(duration);
        holder.downButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String durationString = String.valueOf(holder.mDuration.getText());
                int duration = Integer.parseInt(durationString);
                if(duration > 0)
                    duration--;
                holder.mDuration.setText(String.valueOf(duration));
//                tour.getCities().get(id.intValue()).setDuration(duration);
            }
        });
        holder.upButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String durationString = String.valueOf(holder.mDuration.getText());
                int duration = Integer.parseInt(durationString);
                if(duration < 15)
                    duration++;
                holder.mDuration.setText(String.valueOf(duration));
//                tour.getCities().get(id.intValue()).setDuration(duration);
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

        public ViewHolder(final View itemView) {
            super(itemView, mGrabHandleId, mDragOnLongPress);
            mText = (TextView) itemView.findViewById(R.id.text);
            mDuration = (TextView) itemView.findViewById(R.id.durationTV);
            upButton = (ImageButton) itemView.findViewById(R.id.upBTN);
            downButton = (ImageButton) itemView.findViewById(R.id.downBTN);
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