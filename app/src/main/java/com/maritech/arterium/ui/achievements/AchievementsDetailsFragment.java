package com.maritech.arterium.ui.achievements;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.maritech.arterium.R;
import com.maritech.arterium.databinding.FragmentAchievementsDetailsBinding;
import com.maritech.arterium.ui.base.BaseActivity;
import com.maritech.arterium.ui.base.BaseFragment;

public class AchievementsDetailsFragment
        extends BaseFragment<FragmentAchievementsDetailsBinding> {

    static final String BUNDLE_KEY_ACHIEVEMENTS_NAME = "achievements_name";
    static final String BUNDLE_KEY_ACHIEVEMENTS_IMAGE = "achievements_image";
    static final String BUNDLE_KEY_ACHIEVEMENTS_DESCRIPTIONS = "achievements_descriptions";
    static final String REQUEST_KEY = "achievementsDetails";

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

        getParentFragmentManager()
                .setFragmentResultListener(REQUEST_KEY, this, (requestKey, bundle) -> {
                    thisDescription = bundle.getInt(BUNDLE_KEY_ACHIEVEMENTS_DESCRIPTIONS);
                    thisName = bundle.getInt(BUNDLE_KEY_ACHIEVEMENTS_NAME);
                    thisImage = bundle.getInt(BUNDLE_KEY_ACHIEVEMENTS_IMAGE);

                    binding.tvAchievementHint.setText(thisDescription);
                    binding.tvAchievementName.setText(thisName);
                    binding.ivAchievement.setImageResource(thisImage);
                });

        binding.clBtnBack.setOnClickListener(v -> requireActivity().onBackPressed());

        binding.ivClose.setOnClickListener(v -> requireActivity().onBackPressed());
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        BaseActivity.setStatusBarGradient(requireActivity(), android.R.color.black);
    }

}