package com.maritech.arterium.ui.dashboard.doctor.levels;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;
import com.maritech.arterium.R;
import com.maritech.arterium.common.ContentState;
import com.maritech.arterium.data.models.LevelModel;
import com.maritech.arterium.databinding.FragmentLevelContainerBinding;
import com.maritech.arterium.ui.base.BaseFragment;
import com.maritech.arterium.ui.dashboard.doctor.levels.adapter.LevelsSlideAdapter;

import java.util.ArrayList;
import java.util.List;

public class LevelFragment extends BaseFragment<FragmentLevelContainerBinding> {

    private static final String PROGRAM_ID_KEY = "programIdKey";

    private LevelsViewModel levelsViewModel;

    private List<LevelModel> models = new ArrayList<>();
    private int drugProgramId;

    private LevelsSlideAdapter adapter;
    boolean isManualSlide = false;

    @Override
    protected int getContentView() {
        return R.layout.fragment_level_container;
    }

    public static LevelFragment getInstance(int programId) {
        Bundle bundle = new Bundle();
        bundle.putInt(PROGRAM_ID_KEY, programId);
        LevelFragment fragment = new LevelFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            drugProgramId = getArguments().getInt(PROGRAM_ID_KEY);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        levelsViewModel = new ViewModelProvider(this).get(LevelsViewModel.class);

        initSlideAdapter();

        observeViewModel();

        getLevels();
    }

    private void observeViewModel() {
        levelsViewModel.contentState.observe(lifecycleOwner, contentState -> {
            binding.progressBar.setVisibility(
                    contentState == ContentState.LOADING ? View.VISIBLE : View.GONE
            );

            binding.emptyContainer.setVisibility(
                    contentState == ContentState.EMPTY ? View.VISIBLE : View.GONE
            );
        });

        levelsViewModel.responseLiveData.observe(lifecycleOwner, levelModels -> {
            models.clear();
            models.addAll(levelModels);
            adapter.notifyDataSetChanged();

            addDots(0);
        });
    }

    private void getLevels() {
        levelsViewModel.getLevels(drugProgramId);
    }

    public void addDots(int i) {
        TextView[] mDots = new TextView[models.size()];
        binding.dots.removeAllViews();

        if (mDots.length > 0) {
            for (int x = 0; x < mDots.length; x++) {
                mDots[x] = new TextView(requireContext());
                mDots[x].setText(Html.fromHtml("&#8226;"));
                mDots[x].setTextSize(35);
                mDots[x].setTextColor(getResources().getColor(R.color.light_grey));

                binding.dots.addView(mDots[x]);
            }

            mDots[i].setTextColor(getResources().getColor(R.color.purple_200));
        }
    }

    private void initSlideAdapter() {
        adapter = new LevelsSlideAdapter(requireActivity(), models);
        binding.viewpager.setAdapter(adapter);
        binding.viewpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                isManualSlide = true;
            }

            @Override
            public void onPageSelected(int position) {
                if (isManualSlide) {
                    addDots(position);
                }
            }
        });
    }
}
