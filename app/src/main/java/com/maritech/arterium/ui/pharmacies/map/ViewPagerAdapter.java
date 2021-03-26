package com.maritech.arterium.ui.pharmacies.map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.maritech.arterium.R;
import com.maritech.arterium.data.models.PharmacyModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

public class ViewPagerAdapter extends PagerAdapter {

    public interface OnItemClickListener {
        void onItemClicked(int position);
    }

    private final ViewPagerAdapter.OnItemClickListener onItemClickListener;

    ArrayList<PharmacyModel> models;

    LayoutInflater mLayoutInflater;

    public ViewPagerAdapter(Context context,
                            ArrayList<PharmacyModel> pharmacyModels,
                            ViewPagerAdapter.OnItemClickListener onItemClickListener) {
        this.models = pharmacyModels;
        this.mLayoutInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public float getPageWidth(int position) {
        return 0.80f;
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        View itemView = mLayoutInflater.inflate(R.layout.item, container, false);

        TextView pharmacyTitle =
                itemView.findViewById(R.id.pharmacy).findViewById(R.id.tvPharmacyItemTitle);
        pharmacyTitle.setText(models.get(position).getName());

        itemView.findViewById(R.id.pharmacy).setBackgroundResource(R.drawable.bg_items_pharmacy);
        Objects.requireNonNull(container).addView(itemView);

        itemView.setOnClickListener(v -> onItemClickListener.onItemClicked(position));

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, @NotNull Object object) {
        container.removeView((ConstraintLayout) object);
    }
}