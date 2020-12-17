package com.maritech.arterium.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.maritech.arterium.R;

public class DashboardFragment extends Fragment {

    private ImageView ivSearch;
    private TextView tvDoctors;
    private ImageView ivClose;
    private ConstraintLayout clSearch;

    private DashboardViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), s -> {

            ivSearch = root.findViewById(R.id.ivSearch);
            tvDoctors = root.findViewById(R.id.tvDoctors);
            ivClose = root.findViewById(R.id.ivClose);
            clSearch = root.findViewById(R.id.clSearch);

            ivSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ivSearch.setVisibility(View.GONE);
                    tvDoctors.setVisibility(View.GONE);

                    ivClose.setVisibility(View.VISIBLE);
                    clSearch.setVisibility(View.VISIBLE);
                }
            });


            ivClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ivSearch.setVisibility(View.VISIBLE);
                    tvDoctors.setVisibility(View.VISIBLE);

                    ivClose.setVisibility(View.GONE);
                    clSearch.setVisibility(View.GONE);
                }
            });

        });
        return root;
    }
}