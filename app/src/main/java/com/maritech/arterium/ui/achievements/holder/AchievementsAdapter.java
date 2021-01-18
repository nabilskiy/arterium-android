package com.maritech.arterium.ui.achievements.holder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.maritech.arterium.R;
import com.maritech.arterium.ui.achievements.data.AchievementsContent;
import com.maritech.arterium.ui.add_new_mp.holder.SelectedDoctorAdapter;
import com.maritech.arterium.ui.choose_doctor.data.ChooseDoctorContent;

import java.util.ArrayList;

public class AchievementsAdapter extends RecyclerView.Adapter<AchievementsAdapter.ViewHolder> {

    private AchievementsAdapter.OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClicked(int position, AchievementsContent object);
    }

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

    public AchievementsAdapter(ArrayList<AchievementsContent> dataSet, AchievementsAdapter.OnItemClickListener onItemClickListener) {
        localDataSet = dataSet;
        this.onItemClickListener = onItemClickListener;
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
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClicked(position, localDataSet.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

}
