package com.maritech.arterium.ui.map;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.maritech.arterium.R;
import com.maritech.arterium.ui.choose_doctor.data.ChooseDoctorContent;
import com.maritech.arterium.ui.choose_doctor.holder.ChooseDoctorAdapter;

import java.util.Objects;

public class ViewPagerAdapter extends PagerAdapter {

    public interface OnItemClickListener {
        void onItemClicked(int position);
    }

    private ViewPagerAdapter.OnItemClickListener onItemClickListener;

    // Context object
    Context context;

    // Array of images
    int[] pharmacy;

    // Layout Inflater
    LayoutInflater mLayoutInflater;
    private View view;
    private Object object;

    // Viewpager Constructor
    public ViewPagerAdapter(Context context, int[] pharmacy, ViewPagerAdapter.OnItemClickListener onItemClickListener) {
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
        // return the number of images
        return pharmacy.length;
    }


    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        this.view = view;
        this.object = object;
        return view == ((ConstraintLayout) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        // inflating the item.xml
        View itemView = mLayoutInflater.inflate(R.layout.item, container, false);

        // referencing the image view from the item.xml file
        TextView pharmacyTitle = (TextView) itemView.findViewById(R.id.pharmacy).findViewById(R.id.tvPharmacyItemTitle);
        pharmacyTitle.setText(pharmacy[position]);

        itemView.findViewById(R.id.pharmacy).setBackgroundResource(R.drawable.bg_items_pharmacy);
        // Adding the View
        Objects.requireNonNull(container).addView(itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClicked(position);
            }
        });


        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ConstraintLayout) object);
    }
}