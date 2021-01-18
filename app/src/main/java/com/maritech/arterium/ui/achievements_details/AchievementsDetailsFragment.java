package com.maritech.arterium.ui.achievements_details;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentResultListener;

import com.maritech.arterium.R;
import com.maritech.arterium.ui.base.BaseActivity;
import com.maritech.arterium.ui.base.BaseFragment;

public class AchievementsDetailsFragment extends BaseFragment {

    static final String BUNDLE_KEY_ACHIEVEMENTS_NAME = "achievements_name";
    static final String BUNDLE_KEY_ACHIEVEMENTS_IMAGE = "achievements_image";
    static final String BUNDLE_KEY_ACHIEVEMENTS_DESCRIPTIONS = "achievements_descriptions";
    static final String REQUEST_KEY = "achievementsDetails";

    private ConstraintLayout btnBack;
    private ImageView btnClose;
    private ImageView ivAchievement;
    private TextView tvAchievementName;
    private TextView tvAchievementHint;

    private int thisName;
    private int thisDescription;
    private int thisImage;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_achievements_details, container, false);

        btnBack = root.findViewById(R.id.clBtnBack);
        btnClose = root.findViewById(R.id.ivClose);
        tvAchievementHint = root.findViewById(R.id.tvAchievementHint);
        tvAchievementName = root.findViewById(R.id.tvAchievementName);
        ivAchievement = root.findViewById(R.id.ivAchievement);

        getParentFragmentManager().setFragmentResultListener(REQUEST_KEY, this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                thisDescription = bundle.getInt(BUNDLE_KEY_ACHIEVEMENTS_DESCRIPTIONS);
                thisName = bundle.getInt(BUNDLE_KEY_ACHIEVEMENTS_NAME);
                thisImage = bundle.getInt(BUNDLE_KEY_ACHIEVEMENTS_IMAGE);

                tvAchievementHint.setText(thisDescription);
                tvAchievementName.setText(thisName);
                ivAchievement.setImageResource(thisImage);

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().onBackPressed();
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().onBackPressed();
            }
        });

        requireActivity().findViewById(R.id.nav_view).setVisibility(View.GONE);
        return root;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        BaseActivity.setStatusBarGradient(requireActivity(), android.R.color.black);
    }

}