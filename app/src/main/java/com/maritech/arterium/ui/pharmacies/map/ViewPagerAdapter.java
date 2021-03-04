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

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ViewPagerAdapter extends PagerAdapter {

    public interface OnItemClickListener {
        void onItemClicked(int position);
    }

    private final ViewPagerAdapter.OnItemClickListener onItemClickListener;

    Context context;
    int[] pharmacy;

    LayoutInflater mLayoutInflater;
    private View view;
    private Object object;

    public ViewPagerAdapter(Context context,
                            int[] pharmacy,
                            ViewPagerAdapter.OnItemClickListener onItemClickListener) {
        this.context = context;
        this.pharmacy = pharmacy;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public float getPageWidth(int position) {
        return 0.95f;
    }

    @Override
    public int getCount() {
        return pharmacy.length;
    }


    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        this.view = view;
        this.object = object;
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        View itemView = mLayoutInflater.inflate(R.layout.item, container, false);

        TextView pharmacyTitle =
                itemView.findViewById(R.id.pharmacy).findViewById(R.id.tvPharmacyItemTitle);
        pharmacyTitle.setText(pharmacy[position]);

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