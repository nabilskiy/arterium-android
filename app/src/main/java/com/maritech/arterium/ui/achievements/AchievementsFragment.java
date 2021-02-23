package com.maritech.arterium.ui.achievements;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.maritech.arterium.R;
import com.maritech.arterium.databinding.FragmentAchievementBinding;
import com.maritech.arterium.ui.achievements.data.AchievementsContent;
import com.maritech.arterium.ui.achievements.holder.AchievementsAdapter;
import com.maritech.arterium.ui.base.BaseActivity;
import com.maritech.arterium.ui.base.BaseFragment;

import java.util.ArrayList;

public class AchievementsFragment extends BaseFragment<FragmentAchievementBinding> {

    static final String BUNDLE_KEY_ACHIEVEMENTS_NAME = "achievements_name";
    static final String BUNDLE_KEY_ACHIEVEMENTS_IMAGE = "achievements_image";
    static final String BUNDLE_KEY_ACHIEVEMENTS_DESCRIPTIONS = "achievements_descriptions";
    static final String REQUEST_KEY = "achievementsDetails";

    private final AchievementsNavigator navigator = new AchievementsNavigator();

    @Override
    protected int getContentView() {
        return R.layout.fragment_achievement;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<AchievementsContent> dataList = new ArrayList<>();
        prepareList(dataList);

        RecyclerView mRecyclerView = view.findViewById(R.id.rvAchievement);
        AchievementsAdapter mAdapter = new AchievementsAdapter(dataList, (position, object) -> {
            Bundle result = new Bundle();
            result.putInt(BUNDLE_KEY_ACHIEVEMENTS_IMAGE, object.getIdImage());
            result.putInt(BUNDLE_KEY_ACHIEVEMENTS_NAME, object.getIdName());
            result.putInt(BUNDLE_KEY_ACHIEVEMENTS_DESCRIPTIONS, object.getIdDescription());

            getParentFragmentManager().setFragmentResult(REQUEST_KEY, result);
            navigator.goToAchievementDetails(navController);
        });
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);

        binding.ivClose.setOnClickListener(v -> requireActivity().onBackPressed());

        BaseActivity.setStatusBarGradient(requireActivity(), android.R.color.black);
        Log.e("Bottom", this.getClass().getName());
    }

    @Override
    public void onPause() {
        super.onPause();

        Log.e("onPause", this.getClass().getName());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        BaseActivity.setStatusBarGradientDark(requireActivity(), BaseActivity.fetchPrimaryDarkColor(requireActivity()));
    }

    private void prepareList(ArrayList<AchievementsContent> dataList) {
        dataList.add(new AchievementsContent(R.drawable.ic_achievement_closed, R.string.ukrainian, R.string.ukrainian));
        dataList.add(new AchievementsContent(R.drawable.ic_achievement_closed, R.string.ukrainian, R.string.ukrainian));
        dataList.add(new AchievementsContent(R.drawable.ic_achievement_closed, R.string.english, R.string.ukrainian));

        dataList.add(new AchievementsContent(R.drawable.ic_achievement_closed, R.string.ukrainian, R.string.ukrainian));
        dataList.add(new AchievementsContent(R.drawable.ic_achievement_closed, R.string.english, R.string.ukrainian));
        dataList.add(new AchievementsContent(R.drawable.ic_achievement_closed, R.string.ukrainian, R.string.ukrainian));

        dataList.add(new AchievementsContent(R.drawable.ic_achievement_closed, R.string.ukrainian, R.string.ukrainian));
        dataList.add(new AchievementsContent(R.drawable.ic_achievement_closed, R.string.ukrainian, R.string.ukrainian));
        dataList.add(new AchievementsContent(R.drawable.ic_achievement_closed, R.string.ukrainian, R.string.ukrainian));

        dataList.add(new AchievementsContent(R.drawable.ic_achievement_closed, R.string.ukrainian, R.string.ukrainian));
        dataList.add(new AchievementsContent(R.drawable.ic_achievement_closed, R.string.ukrainian, R.string.ukrainian));
        dataList.add(new AchievementsContent(R.drawable.ic_achievement_closed, R.string.ukrainian, R.string.ukrainian));

        dataList.add(new AchievementsContent(R.drawable.ic_achievement_closed, R.string.ukrainian, R.string.ukrainian));
        dataList.add(new AchievementsContent(R.drawable.ic_achievement_closed, R.string.ukrainian, R.string.ukrainian));
        dataList.add(new AchievementsContent(R.drawable.ic_achievement_closed, R.string.ukrainian, R.string.ukrainian));

        dataList.add(new AchievementsContent(R.drawable.ic_achievement_closed, R.string.ukrainian, R.string.ukrainian));
        dataList.add(new AchievementsContent(R.drawable.ic_achievement_closed, R.string.ukrainian, R.string.ukrainian));
        dataList.add(new AchievementsContent(R.drawable.ic_achievement_closed, R.string.ukrainian, R.string.ukrainian));

        dataList.add(new AchievementsContent(R.drawable.ic_achievement_closed, R.string.ukrainian, R.string.ukrainian));

    }
}