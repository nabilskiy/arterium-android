package com.maritech.arterium.ui.add_new_mp.holder;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.maritech.arterium.R;
import com.maritech.arterium.ui.choose_doctor.data.ChooseDoctorContent;

import java.util.ArrayList;


public class SelectedDoctorAdapter extends RecyclerView.Adapter<SelectedDoctorAdapter.ViewHolder> {
    ArrayList<ChooseDoctorContent> localDataSet;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClicked(int position, ChooseDoctorContent object);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tvUserName;
        final TextView tvUserCity;
        final ImageView ivUserPhoto;
        final ImageView ivCheck;

        final View viewLineTop;
        final View viewLine;
        final ImageView ivRemove;
        final ImageView ivArrow;


        public ViewHolder(View view) {
            super(view);

            tvUserName = (TextView) view.findViewById(R.id.tvUserName);
            tvUserCity = (TextView) view.findViewById(R.id.tvUserCity);
            ivUserPhoto = (ImageView) view.findViewById(R.id.ivUserPhoto);
            ivCheck = (ImageView) view.findViewById(R.id.ivCheck);

            ivRemove = (ImageView) view.findViewById(R.id.ivRemove);
            ivArrow = (ImageView) view.findViewById(R.id.ivArrow);
            viewLineTop = (View) view.findViewById(R.id.viewLineTop);
            viewLine = (View) view.findViewById(R.id.viewLine);

        }

    }

    public SelectedDoctorAdapter(ArrayList<ChooseDoctorContent> dataSet, SelectedDoctorAdapter.OnItemClickListener onItemClickListener) {
        localDataSet = dataSet;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public SelectedDoctorAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_choose_mp, viewGroup, false);
        return new SelectedDoctorAdapter.ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    @Override
    public void onBindViewHolder(SelectedDoctorAdapter.ViewHolder viewHolder, final int position) {

        viewHolder.ivUserPhoto.setImageResource(localDataSet.get(position).getPhoto());
        viewHolder.tvUserName.setText(localDataSet.get(position).getName());
        viewHolder.tvUserCity.setText(localDataSet.get(position).getCity() + " • " + localDataSet.get(position).getNumberPhone());

        viewHolder.viewLine.setVisibility(View.GONE);
        viewHolder.viewLineTop.setVisibility(View.VISIBLE);
        viewHolder.ivArrow.setVisibility(View.GONE);
        viewHolder.ivRemove.setVisibility(View.VISIBLE);


        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

        //delllllete

                notifyDataSetChanged();
                onItemClickListener.onItemClicked(position, localDataSet.get(position));
            }
        });

    }


}





