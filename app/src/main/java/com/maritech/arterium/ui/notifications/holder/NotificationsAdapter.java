package com.maritech.arterium.ui.notifications.holder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.maritech.arterium.R;
import com.maritech.arterium.data.models.NotificationResponse;
import org.jetbrains.annotations.NotNull;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;


public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.ViewHolder> {

    private final ArrayList<NotificationResponse.Data> localDataSet;
    private final NotificationsAdapter.OnItemClickListener onItemClickListener;

    private final SimpleDateFormat dateFormat =
            new SimpleDateFormat("HH:mm", Locale.getDefault());


    public NotificationsAdapter(ArrayList<NotificationResponse.Data> dataSet,
                                NotificationsAdapter.OnItemClickListener onItemClickListener) {
        this.localDataSet = dataSet;
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

        NotificationResponse.Data model = localDataSet.get(position);

        long millis = model.getCreatedAt() * 1000;
        viewHolder.tvTime.setText(dateFormat.format(new Date(millis)));

        viewHolder.tvMessage.setText(localDataSet.get(position).getMessage());

        viewHolder.itemView.setOnClickListener(v -> {
            notifyDataSetChanged();
            onItemClickListener.onItemClicked(position, localDataSet.get(position));
        });

        viewHolder.readView.setVisibility(model.getRead() ? View.VISIBLE : View.GONE);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ConstraintLayout clItemNotifications;
        final TextView tvMessage;
        final TextView tvTime;
        final View readView;

        public ViewHolder(View view) {
            super(view);

            tvTime = view.findViewById(R.id.tvTime);
            tvMessage = view.findViewById(R.id.tvMessage);
            clItemNotifications = view.findViewById(R.id.clItemNotifications);
            readView = view.findViewById(R.id.readView);
        }
    }

    public interface OnItemClickListener {
        void onItemClicked(int position, NotificationResponse.Data object);
    }
}





