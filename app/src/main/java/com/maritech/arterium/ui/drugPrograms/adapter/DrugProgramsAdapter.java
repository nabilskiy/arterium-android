package com.maritech.arterium.ui.drugPrograms.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.maritech.arterium.R;
import com.maritech.arterium.data.models.DrugProgramModel;

import java.util.ArrayList;

public class DrugProgramsAdapter extends RecyclerView.Adapter<DrugProgramsAdapter.ViewHolder> {

    private ArrayList<DrugProgramModel> localDataSet;
    private DrugProgramsAdapter.OnItemClickListener onItemClickListener;
    private Context context;

    public interface OnItemClickListener {
        void onItemClicked(int position, DrugProgramModel object, String tittle);
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

    public DrugProgramsAdapter(Context context,
                               ArrayList<DrugProgramModel> dataSet,
                               DrugProgramsAdapter.OnItemClickListener onItemClickListener) {
        localDataSet = dataSet;
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_dialog_recycler, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        viewHolder.ivBtnCheck.setActivated(localDataSet.get(position).isSelected());
        viewHolder.tvName.setText(String.format("%s - \"%s\"",
                localDataSet.get(position).getTitle(), localDataSet.get(position).getSlogan()));

        int color;
        if (position == 1) {
            color = ContextCompat.getColor(viewHolder.tvName.getContext(), R.color.blue);
        } else if (position == 2) {
            color = ContextCompat.getColor(viewHolder.tvName.getContext(), R.color.red);
        } else {
            color = ContextCompat.getColor(viewHolder.tvName.getContext(), R.color.purple);
        }

        viewHolder.tvName.setTextColor(color);

        if (localDataSet.get(position).getDescription() != null) {
            viewHolder.tvHint.setText(localDataSet.get(position).getDescription());
        } else {
            viewHolder.tvHint.setText(context.getString(R.string.drug_program_desc));
        }

        if (localDataSet.size() - 1 == position) {
            viewHolder.viewLine.setVisibility(View.GONE);
        }

        viewHolder.itemView.setOnClickListener(v -> {
            onItemClickListener.onItemClicked(
                    position,
                    localDataSet.get(position),
                    String.valueOf(viewHolder.tvName.getText())
            );
            notifyDataSetChanged();
        });

    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

}
