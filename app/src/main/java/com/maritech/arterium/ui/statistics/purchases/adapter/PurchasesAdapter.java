package com.maritech.arterium.ui.statistics.purchases.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.maritech.arterium.R;
import com.maritech.arterium.data.models.PurchaseModel;
import com.maritech.arterium.utils.DateTimeUtil;
import org.jetbrains.annotations.NotNull;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class PurchasesAdapter
        extends RecyclerView.Adapter<PurchasesAdapter.ViewHolder> {

    private final SimpleDateFormat dateFormat;

    ArrayList<PurchaseModel> localDataSet;

    private final OnItemClickListener onItemClickListener;
    private final Context context;

    public interface OnItemClickListener {
        void onItemClicked(int position, PurchaseModel object);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ConstraintLayout clItemNotifications;
        final TextView tvHeader;
        final TextView tvName;
        final TextView tvLastBuy;
        final View viewLine;

        public ViewHolder(View view) {
            super(view);

            tvHeader = view.findViewById(R.id.tvHeader);
            tvName = view.findViewById(R.id.tvName);
            tvLastBuy = view.findViewById(R.id.tvLastBuy);
            viewLine = view.findViewById(R.id.viewLine);
            clItemNotifications = view.findViewById(R.id.clItemNotifications);
        }
    }

    public PurchasesAdapter(Context context,
                            ArrayList<PurchaseModel> dataSet,
                            OnItemClickListener onItemClickListener) {
        localDataSet = dataSet;
        this.context = context;
        this.onItemClickListener = onItemClickListener;

        this.dateFormat =
                new SimpleDateFormat("dd MMMM yyyy", DateTimeUtil.getLocale(context));
    }

    public void setData(ArrayList<PurchaseModel> dataSet) {
        this.localDataSet.clear();
        this.localDataSet.addAll(dataSet);
        notifyDataSetChanged();
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_patient_purchases, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    @Override
    public void onBindViewHolder(@NotNull ViewHolder viewHolder,
                                 final int position) {

        long createdDateLong = localDataSet.get(position).getTimestamp() * 1000;

        Date createdDate = new Date(createdDateLong);
        Date prevCreatedDate = null;

        if (position > 0) {
            long prevCreatedDateLong = localDataSet.get(position - 1).getTimestamp() * 1000;
            prevCreatedDate = new Date(prevCreatedDateLong);
        }

        String dateStr = dateFormat.format(createdDate);

        boolean isEquals = false;
        if (prevCreatedDate != null) {
            String prevDateStr = dateFormat.format(prevCreatedDate);

            isEquals = dateStr.equals(prevDateStr);
        }

        if (position > 0 && isEquals) {
            viewHolder.tvHeader.setVisibility(View.GONE);
        } else {
            viewHolder.tvHeader.setText(dateStr);
            viewHolder.tvHeader.setVisibility(View.VISIBLE);
        }

        viewHolder.tvName.setText(localDataSet.get(position).getPatientName());

        String purchase = localDataSet.get(position).getSoldSum();

        if (purchase == null || purchase.isEmpty()) {
            viewHolder.tvLastBuy.setText(context.getString(R.string.without_purchases));
            viewHolder.tvLastBuy.setTextColor(Color.parseColor("#FF3347"));
        } else {
            if (localDataSet.get(position).getIsPrimary()) {
                viewHolder.tvLastBuy.setText(
                        context.getString(R.string.level_purchase_primary)
                );
                viewHolder.tvLastBuy.setTextColor(Color.parseColor("#228945"));
            } else {
                viewHolder.tvLastBuy.setText(context.getString(R.string.level_purchase_repeat));
                viewHolder.tvLastBuy.setTextColor(Color.parseColor("#D98700"));
            }
        }

        viewHolder.clItemNotifications.setOnClickListener(
                v -> onItemClickListener.onItemClicked(position, localDataSet.get(position))
        );
    }
}
