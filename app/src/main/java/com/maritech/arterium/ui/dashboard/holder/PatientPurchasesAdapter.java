package com.maritech.arterium.ui.dashboard.holder;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.maritech.arterium.R;
import com.maritech.arterium.ui.dashboard.data.PatientPurchasesContent;
import com.maritech.arterium.ui.notifications.data.NotificationsContent;

import java.util.ArrayList;

public class PatientPurchasesAdapter extends RecyclerView.Adapter<PatientPurchasesAdapter.ViewHolder> {
    ArrayList<PatientPurchasesContent> localDataSet;
    private PatientPurchasesAdapter.OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClicked(int position, PatientPurchasesContent object);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ConstraintLayout clItemNotifications;
        final TextView tvHeader;
        final TextView tvName;
        final TextView tvLastBuy;
        final View viewLine;

        public ViewHolder(View view) {
            super(view);

            tvHeader = (TextView) view.findViewById(R.id.tvHeader);
            tvName = (TextView) view.findViewById(R.id.tvName);
            tvLastBuy = (TextView) view.findViewById(R.id.tvLastBuy);
            viewLine = (View) view.findViewById(R.id.viewLine);
            clItemNotifications = (ConstraintLayout) view.findViewById(R.id.clItemNotifications);

        }

    }

    public PatientPurchasesAdapter(ArrayList<PatientPurchasesContent> dataSet, PatientPurchasesAdapter.OnItemClickListener onItemClickListener) {
        localDataSet = dataSet;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public PatientPurchasesAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_patient_purchases, viewGroup, false);
        return new PatientPurchasesAdapter.ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    @Override
    public void onBindViewHolder(PatientPurchasesAdapter.ViewHolder viewHolder, final int position) {

        if (position == 0 ) {
            viewHolder.tvHeader.setText(localDataSet.get(position).getData());
            viewHolder.tvHeader.setVisibility(View.VISIBLE);
        }

        if (position >= 1 && localDataSet.get(position).getData().equals(localDataSet.get(position - 1).getData())) {
            viewHolder.tvHeader.setVisibility(View.GONE);
        } else {
            viewHolder.tvHeader.setText(localDataSet.get(position).getData());
            viewHolder.tvHeader.setVisibility(View.VISIBLE);
            if ((localDataSet.size() - 1) == position) {
                viewHolder.tvHeader.setVisibility(View.GONE);
            }
        }

        viewHolder.tvName.setText(localDataSet.get(position).getName());

        if (localDataSet.get(position).getMessage().equals("0")) {
            viewHolder.tvLastBuy.setText("Нет покупок");
            viewHolder.tvLastBuy.setTextColor(Color.parseColor("#FF3347"));
        } else {
            viewHolder.tvLastBuy.setText(localDataSet.get(position).getMessage());
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyDataSetChanged();
                onItemClickListener.onItemClicked(position, localDataSet.get(position));
            }
        });

    }


}
