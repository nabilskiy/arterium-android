package com.maritech.arterium.ui.achievements.holder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.maritech.arterium.R;
import com.maritech.arterium.ui.achievements.data.AchievementsContent;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AchievementsAdapter extends RecyclerView.Adapter<AchievementsAdapter.ViewHolder> {

    private final AchievementsAdapter.OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClicked(int position, AchievementsContent object);
    }

    ArrayList<AchievementsContent> localDataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tvName;
        final ImageView ivFoto;

        public ViewHolder(View view) {
            super(view);

            tvName = view.findViewById(R.id.tvAchievement);
            ivFoto = view.findViewById(R.id.ivAchievement);
        }
    }

    public AchievementsAdapter(ArrayList<AchievementsContent> dataSet, AchievementsAdapter.OnItemClickListener onItemClickListener) {
        localDataSet = dataSet;
        this.onItemClickListener = onItemClickListener;
    }

    @NotNull
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
        viewHolder.itemView.setOnClickListener(v ->
                onItemClickListener.onItemClicked(position, localDataSet.get(position)));

    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

}
