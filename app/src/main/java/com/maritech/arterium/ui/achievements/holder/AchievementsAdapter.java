package com.maritech.arterium.ui.achievements.holder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.maritech.arterium.R;
import com.maritech.arterium.ui.achievements.data.AchievementsContent;

import java.util.ArrayList;

public class AchievementsAdapter extends RecyclerView.Adapter<AchievementsAdapter.ViewHolder> {

    ArrayList<AchievementsContent> localDataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tvName;
        final ImageView ivFoto;

        public ViewHolder(View view) {
            super(view);

            tvName = (TextView) view.findViewById(R.id.tvAchievement);
            ivFoto = (ImageView) view.findViewById(R.id.ivAchievement);
        }
    }

    public AchievementsAdapter(ArrayList<AchievementsContent> dataSet) {
        localDataSet = dataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_achievements, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.ivFoto.setImageResource(localDataSet.get(position).getIdImage());
        viewHolder.tvName.setText(localDataSet.get(position).getIdName());
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

}
