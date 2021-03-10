package com.maritech.arterium.ui.patients.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.maritech.arterium.R;
import com.maritech.arterium.data.models.PatientModel;
import com.maritech.arterium.utils.DateTimeUtil;

import org.jetbrains.annotations.NotNull;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class PatientsAdapter
        extends RecyclerView.Adapter<PatientsAdapter.ViewHolder> {

    private final SimpleDateFormat dateFormat;

    ArrayList<PatientModel> localDataSet;

    private final PatientsAdapter.OnItemClickListener onItemClickListener;
    private final Context context;

    public interface OnItemClickListener {
        void onItemClicked(int position, PatientModel object);
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

    public PatientsAdapter(Context context,
                           ArrayList<PatientModel> dataSet,
                           PatientsAdapter.OnItemClickListener onItemClickListener) {
        localDataSet = dataSet;
        this.context = context;
        this.onItemClickListener = onItemClickListener;
        this.dateFormat =
                new SimpleDateFormat("dd MMMM yyyy", DateTimeUtil.getLocale(context));
    }

    public void setData(ArrayList<PatientModel> dataSet) {
        this.localDataSet.clear();
        this.localDataSet.addAll(dataSet);
        notifyDataSetChanged();
    }

    @NotNull
    @Override
    public PatientsAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_patient_purchases, viewGroup, false);
        return new PatientsAdapter.ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    @Override
    public void onBindViewHolder(@NotNull PatientsAdapter.ViewHolder viewHolder,
                                 final int position) {

        long createdDateLong = localDataSet.get(position).getCreatedAt() * 1000;

        Date createdDate = new Date(createdDateLong);
        Date prevCreatedDate = null;

        if (position > 0) {
            long prevCreatedDateLong = localDataSet.get(position - 1).getCreatedAt() * 1000;
            prevCreatedDate = new Date(prevCreatedDateLong);
        }

        boolean isEquals = false;
        if (prevCreatedDate != null)
            isEquals = dateFormat.format(createdDate).equals(dateFormat.format(prevCreatedDate));

        if (position > 0 && (localDataSet.size() - 1) == position || isEquals) {
            viewHolder.tvHeader.setVisibility(View.GONE);
        } else {
            viewHolder.tvHeader.setText(dateFormat.format(createdDate));
            viewHolder.tvHeader.setVisibility(View.VISIBLE);
        }

        viewHolder.tvName.setText(localDataSet.get(position).getName());

        Long lastPurchases = localDataSet.get(position).getLastPurchaseAt();
        if (lastPurchases == null) {
            viewHolder.tvLastBuy.setText(context.getString(R.string.without_purchases));
            viewHolder.tvLastBuy.setTextColor(Color.parseColor("#FF3347"));
        } else {

            viewHolder.tvLastBuy.setText(
                    context.getString(
                            R.string.last_purchases, dateFormat.format(
                                    new Date(lastPurchases * 1000)
                            )
                    )
            );
            viewHolder.tvLastBuy.setTextColor(ContextCompat.getColor(context, R.color.gray));
        }

        viewHolder.clItemNotifications.setOnClickListener(
                v -> onItemClickListener.onItemClicked(position, localDataSet.get(position)));
    }
}
