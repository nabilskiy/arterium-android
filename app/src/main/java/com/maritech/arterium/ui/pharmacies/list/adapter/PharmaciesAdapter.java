package com.maritech.arterium.ui.pharmacies.list.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.maritech.arterium.R;
import com.maritech.arterium.data.models.PharmacyModel;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;

public class PharmaciesAdapter
        extends RecyclerView.Adapter<PharmaciesAdapter.ViewHolder> {

    private final ArrayList<PharmacyModel> localDataSet;

    private final PharmaciesAdapter.OnItemClickListener onItemClickListener;
    Context context;

    public PharmaciesAdapter(Context context,
                             ArrayList<PharmacyModel> dataSet,
                             PharmaciesAdapter.OnItemClickListener onItemClickListener) {
        this.localDataSet = dataSet;
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClicked(int position, PharmacyModel object);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tvPharmacyItemTitle;
        final TextView tvPharmacyAddress;
        final TextView tvWorkingHours;
        final TextView tvHowToGetToKnow;
        final FrameLayout routeLayout;

        public ViewHolder(View view) {
            super(view);

            tvPharmacyItemTitle = view.findViewById(R.id.tvPharmacyItemTitle);
            tvPharmacyAddress = view.findViewById(R.id.tvPharmacyAddress);
            tvWorkingHours = view.findViewById(R.id.tvWorkingHours);
            tvHowToGetToKnow = view.findViewById(R.id.tvHowToGetToKnow);
            routeLayout = view.findViewById(R.id.route_layout);
            routeLayout.setOnClickListener(v ->
                    onItemClickListener.onItemClicked(
                            getAdapterPosition(), localDataSet.get(getAdapterPosition())
                    )
            );
        }

        private void bind(PharmacyModel model) {
            tvPharmacyItemTitle.setText(model.getName());
            tvPharmacyAddress.setText(model.getAddress());
        }
    }

    public void setData(ArrayList<PharmacyModel> dataSet) {
        this.localDataSet.clear();
        this.localDataSet.addAll(dataSet);
        notifyDataSetChanged();
    }

    @NotNull
    @Override
    public PharmaciesAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.pharmacy_item, viewGroup, false);
        return new PharmaciesAdapter.ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    @Override
    public void onBindViewHolder(@NotNull PharmaciesAdapter.ViewHolder viewHolder,
                                 final int position) {

        viewHolder.bind(localDataSet.get(position));
    }
}
