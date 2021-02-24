package com.maritech.arterium.ui.notifications.holder;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.maritech.arterium.R;
import com.maritech.arterium.ui.notifications.data.NotificationsContent;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.ViewHolder> {
    ArrayList<NotificationsContent> localDataSet;
    private final NotificationsAdapter.OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClicked(int position, NotificationsContent object);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ConstraintLayout clItemNotifications;
        final TextView tvMessage;
        final TextView tvTime;
        final View line;

        public ViewHolder(View view) {
            super(view);

            tvMessage = view.findViewById(R.id.tvMessage);
            tvTime = view.findViewById(R.id.tvTime);
            clItemNotifications = view.findViewById(R.id.clItemNotifications);
            line = view.findViewById(R.id.viewLine);
        }
    }

    public NotificationsAdapter(ArrayList<NotificationsContent> dataSet,
                                NotificationsAdapter.OnItemClickListener onItemClickListener) {
        localDataSet = dataSet;
        this.onItemClickListener = onItemClickListener;
    }

    @NotNull
    @Override
    public NotificationsAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_notifications, viewGroup, false);
        return new NotificationsAdapter.ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    @Override
    public void onBindViewHolder(NotificationsAdapter.ViewHolder viewHolder, final int position) {

        viewHolder.tvTime.setText(localDataSet.get(position).getData());
        viewHolder.tvMessage.setText(localDataSet.get(position).getMessage());

        viewHolder.itemView.setOnClickListener(v -> {
            notifyDataSetChanged();
            onItemClickListener.onItemClicked(position, localDataSet.get(position));
        });

        if(position == getItemCount() - 1){
            viewHolder.line.setVisibility(View.INVISIBLE);
        }
    }
}





