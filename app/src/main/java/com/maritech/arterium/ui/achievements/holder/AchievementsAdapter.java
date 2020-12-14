package com.maritech.arterium.ui.achievements.holder;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.maritech.arterium.R;
import com.maritech.arterium.ui.achievements.data.AchievementsContent;

import java.util.ArrayList;
import java.util.List;

public class AchievementsAdapter extends RecyclerView.Adapter<AchievementsAdapter.ViewHolder> {

    ArrayList<ArrayList<AchievementsContent>> localDataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tvOne;
        final TextView tvTwo;
        final TextView tvThree;

        final ImageView ivOne;
        final ImageView ivTwo;
        final ImageView ivThree;


        public ViewHolder(View view) {
            super(view);

            tvOne = (TextView) view.findViewById(R.id.tvAchievementOne);
            tvTwo = (TextView) view.findViewById(R.id.tvAchievementTwo);
            tvThree = (TextView) view.findViewById(R.id.tvAchievementThree);

            ivOne = (ImageView) view.findViewById(R.id.ivAchievementOne);
            ivTwo = (ImageView) view.findViewById(R.id.ivAchievementTwo);
            ivThree = (ImageView) view.findViewById(R.id.ivAchievementThree);
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     *                by RecyclerView.
     */
    public AchievementsAdapter(ArrayList<ArrayList<AchievementsContent>> dataSet) {
        localDataSet = dataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_achievements, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        Log.e("!!!Size", String.valueOf(localDataSet.get(position).size()));

        if (localDataSet.get(position).size() == 1) {
            viewHolder.ivOne.setImageResource(localDataSet.get(position).get(0).getIdImage());
            viewHolder.tvOne.setText(localDataSet.get(position).get(0).getIdName());

            viewHolder.ivTwo.setVisibility(View.INVISIBLE);
            viewHolder.ivThree.setVisibility(View.INVISIBLE);
            viewHolder.tvTwo.setVisibility(View.INVISIBLE);
            viewHolder.tvThree.setVisibility(View.INVISIBLE);
        } else if (localDataSet.get(position).size() == 2) {

            viewHolder.ivOne.setImageResource(localDataSet.get(position).get(0).getIdImage());
            viewHolder.tvOne.setText(localDataSet.get(position).get(0).getIdName());

            viewHolder.ivTwo.setImageResource(localDataSet.get(position).get(1).getIdImage());
            viewHolder.tvTwo.setText(localDataSet.get(position).get(1).getIdName());

            viewHolder.ivThree.setVisibility(View.INVISIBLE);
            viewHolder.tvThree.setVisibility(View.INVISIBLE);

        } else if (localDataSet.get(position).size() == 3) {
            viewHolder.ivOne.setImageResource(localDataSet.get(position).get(0).getIdImage());
            viewHolder.ivTwo.setImageResource(localDataSet.get(position).get(1).getIdImage());
            viewHolder.ivThree.setImageResource(localDataSet.get(position).get(2).getIdImage());

            viewHolder.tvOne.setText(localDataSet.get(position).get(0).getIdName());
            viewHolder.tvTwo.setText(localDataSet.get(position).get(1).getIdName());
            viewHolder.tvThree.setText(localDataSet.get(position).get(2).getIdName());

        } else {
            viewHolder.ivOne.setVisibility(View.GONE);
            viewHolder.ivTwo.setVisibility(View.GONE);
            viewHolder.ivThree.setVisibility(View.GONE);

            viewHolder.tvOne.setVisibility(View.GONE);
            viewHolder.tvTwo.setVisibility(View.GONE);
            viewHolder.tvThree.setVisibility(View.GONE);
        }


//
//        if (position == 0) {
//            viewHolder.ivOne.setImageResource(localDataSet.get(position).getIdImage());
//            viewHolder.ivTwo.setImageResource(localDataSet.get(position + 1).getIdImage());
//            viewHolder.ivThree.setImageResource(localDataSet.get(position + 2).getIdImage());
//
//            viewHolder.tvOne.setText(localDataSet.get(position).getIdName());
//            viewHolder.tvTwo.setText(localDataSet.get(position + 1).getIdName());
//            viewHolder.tvThree.setText(localDataSet.get(position + 2).getIdName());
//
//        } else if (position % 3 == 0) {
//            viewHolder.ivOne.setImageResource(localDataSet.get(position).getIdImage());
//            viewHolder.ivTwo.setImageResource(localDataSet.get(position + 1).getIdImage());
//            viewHolder.ivThree.setImageResource(localDataSet.get(position + 2).getIdImage());
//
//            viewHolder.tvOne.setText(localDataSet.get(position).getIdName());
//            viewHolder.tvTwo.setText(localDataSet.get(position + 1).getIdName());
//            viewHolder.tvThree.setText(localDataSet.get(position + 2).getIdName());
//        }
//
//        else {
//            viewHolder.ivOne.setVisibility(View.GONE);
//            viewHolder.ivTwo.setVisibility(View.GONE);
//            viewHolder.ivThree.setVisibility(View.GONE);
//
//            viewHolder.tvOne.setVisibility(View.GONE);
//            viewHolder.tvTwo.setVisibility(View.GONE);
//            viewHolder.tvThree.setVisibility(View.GONE);
//        }

    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

}
