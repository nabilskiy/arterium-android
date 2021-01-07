package com.maritech.arterium.ui.choose_mp.holder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.maritech.arterium.R;
import com.maritech.arterium.ui.choose_mp.data.ChooseMpContent;

import java.util.ArrayList;


public class ChooseMpAdapter extends RecyclerView.Adapter<ChooseMpAdapter.ViewHolder> {
    ArrayList<ChooseMpContent> localDataSet;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClicked(int position, ChooseMpContent object);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tvUserName;
        final TextView tvUserCity;
        final ImageView ivUserPhoto;
        final ImageView ivCheck;

        public ViewHolder(View view) {
            super(view);

            tvUserName = (TextView) view.findViewById(R.id.tvUserName);
            tvUserCity = (TextView) view.findViewById(R.id.tvUserCity);
            ivUserPhoto = (ImageView) view.findViewById(R.id.ivUserPhoto);
            ivCheck = (ImageView) view.findViewById(R.id.ivCheck);
        }

    }

    public ChooseMpAdapter(ArrayList<ChooseMpContent> dataSet, OnItemClickListener onItemClickListener) {
        localDataSet = dataSet;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ChooseMpAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_choose_mp, viewGroup, false);
        return new ChooseMpAdapter.ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        viewHolder.ivUserPhoto.setImageResource(localDataSet.get(position).getPhoto());
        viewHolder.tvUserName.setText(localDataSet.get(position).getName());
        viewHolder.tvUserCity.setText(localDataSet.get(position).getCity() + " • " + localDataSet.get(position).getNumberPhone());

        if (localDataSet.get(position).getSelected()) {
            viewHolder.ivCheck.setVisibility(View.VISIBLE);
            viewHolder.ivUserPhoto.setAlpha(0.7f);

        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (localDataSet.get(position).getSelected()) {
                    for (int i = 0; i < localDataSet.size(); i++) {
                        localDataSet.get(i).setSelected(false);
                    }

                } else {
                    for (int i = 0; i < localDataSet.size(); i++) {
                        localDataSet.get(i).setSelected(false);
                    }
                    localDataSet.get(position).setSelected(true);
                }

                notifyDataSetChanged();
                onItemClickListener.onItemClicked(position, localDataSet.get(position));
            }
        });

    }


}





