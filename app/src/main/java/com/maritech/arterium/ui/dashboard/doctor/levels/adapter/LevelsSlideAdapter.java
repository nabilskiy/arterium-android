package com.maritech.arterium.ui.dashboard.doctor.levels.adapter;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.maritech.arterium.R;
import com.maritech.arterium.data.models.LevelModel;
import com.maritech.arterium.databinding.FragmentLevelBinding;
import com.maritech.arterium.ui.base.BaseFragment;

import java.util.List;

public class LevelsSlideAdapter extends FragmentStateAdapter {

    private final List<LevelModel> models;

    public LevelsSlideAdapter(@NonNull FragmentActivity fragmentActivity,
                              List<LevelModel> models) {
        super(fragmentActivity);
        this.models = models;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return PageFragment.getInstance(models.get(position));
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public static class PageFragment extends BaseFragment<FragmentLevelBinding> {

        private static final String MODEL_KEY = "modelKey";
        private LevelModel model;

        public static PageFragment getInstance(LevelModel model) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(MODEL_KEY, model);

            PageFragment fragment = new PageFragment();
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        protected int getContentView() {
            return R.layout.fragment_level;
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            if (getArguments() != null) {
                model = getArguments().getParcelable(MODEL_KEY);
            }
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            int drawable;
            int backgroundColor;
            String level;

            if (model.getCode().equalsIgnoreCase("b")) {
                drawable = R.drawable.level_b;
                backgroundColor = Color.parseColor("#7B9CA2");
                level = getString(R.string.level_b);
            } else if (model.getCode().equalsIgnoreCase("c")) {
                drawable = R.drawable.level_c;
                backgroundColor = Color.parseColor("#846275");
                level = getString(R.string.level_с);
            } else {
                drawable = R.drawable.level_a;
                backgroundColor = Color.parseColor("#503a47");
                level = getString(R.string.level_a);
            }

            binding.levelCodeIv.setImageDrawable(
                    ContextCompat.getDrawable(requireContext(), drawable)
            );
            binding.levelCardView.setCardBackgroundColor(backgroundColor);
            binding.levelTv.setText(level);

            String condition = String.valueOf(model.getConditionFrom());

            if (String.valueOf(model.getConditionTo()).contains("999")) {
                condition = condition + "<";
            } else {
                condition = String.valueOf(model.getConditionTo());
            }
            binding.levelMonthTv.setText(condition);
            binding.levelPrimaryTv.setText(model.getPrimaryPay());
            binding.levelSecondaryTv.setText(model.getSecondaryPay());
        }
    }
}