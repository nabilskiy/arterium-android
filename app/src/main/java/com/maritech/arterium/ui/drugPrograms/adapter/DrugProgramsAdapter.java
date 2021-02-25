package com.maritech.arterium.ui.drugPrograms.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.maritech.arterium.R;
import com.maritech.arterium.ui.drugPrograms.data.DialogContent;

import java.util.ArrayList;

public class DrugProgramsAdapter extends RecyclerView.Adapter<DrugProgramsAdapter.ViewHolder> {

    ArrayList<DialogContent> localDataSet;
    private DrugProgramsAdapter.OnItemClickListener onItemClickListener;


    public interface OnItemClickListener {
        void onItemClicked(int position, DialogContent object, String tittle);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tvName;
        final TextView tvHint;
        final ImageView ivBtnCheck;
        final View viewLine;

        public ViewHolder(View view) {
            super(view);

            tvName = view.findViewById(R.id.tvName);
            tvHint = view.findViewById(R.id.tvHint);
            ivBtnCheck = view.findViewById(R.id.ivBtnCheck);
            viewLine = view.findViewById(R.id.viewLine);
        }
    }

    public DrugProgramsAdapter(ArrayList<DialogContent> dataSet, DrugProgramsAdapter.OnItemClickListener onItemClickListener) {
        localDataSet = dataSet;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_dialog_recycler, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        viewHolder.ivBtnCheck.setActivated(localDataSet.get(position).isActive());
        viewHolder.tvName.setText(localDataSet.get(position).getIdTittle());
        viewHolder.tvName.setTextColor(ContextCompat.getColor(viewHolder.tvName.getContext(), localDataSet.get(position).getIdTextColor()));
        viewHolder.tvHint.setTextColor(ContextCompat.getColor(viewHolder.tvHint.getContext(), localDataSet.get(position).getIdTextColor()));
        viewHolder.tvHint.setText(localDataSet.get(position).getIdHint());

        if (localDataSet.size()-1 == position){
            viewHolder.viewLine.setVisibility(View.GONE);
        }

        viewHolder.itemView.setOnClickListener(v -> {
            onItemClickListener.onItemClicked(position, localDataSet.get(position), String.valueOf(viewHolder.tvName.getText()));
            notifyDataSetChanged();
        });

    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

}
