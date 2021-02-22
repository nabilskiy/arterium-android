package com.maritech.arterium.ui.achievements;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

    @Override
    protected int getContentView() {
        return R.layout.fragment_achievements_details;
    }

    @Override
    public void onViewCreated(@NonNull View root, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);

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

        btnBack.setOnClickListener(v -> requireActivity().onBackPressed());

        btnClose.setOnClickListener(v -> requireActivity().onBackPressed());
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        BaseActivity.setStatusBarGradient(requireActivity(), android.R.color.black);
    }

}