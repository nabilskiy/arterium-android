package com.maritech.arterium.ui.achievements;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.maritech.arterium.MainActivity;
import com.maritech.arterium.R;
import com.maritech.arterium.databinding.ItemAchievementsBinding;
import com.maritech.arterium.ui.achievements.data.AchievementsContent;
import com.maritech.arterium.ui.base.BaseActivity;
import com.maritech.arterium.ui.base.BaseAdapter;
import com.maritech.arterium.ui.base.BaseFragment;
import com.maritech.arterium.ui.profile.HomeViewModel;

import java.util.ArrayList;

public class AchievementsFragment extends BaseFragment {

    private HomeViewModel homeViewModel;
    private GridLayoutManager layoutManager;
    ImageView btnClose;

    AchievementsNavigator navigator = new AchievementsNavigator();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_achievement, container, false);

        btnClose = root.findViewById(R.id.ivClose);

        ArrayList<AchievementsContent> dataList = new ArrayList<AchievementsContent>();
        prepareList(dataList);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().onBackPressed();
            }
        });
        layoutManager = new GridLayoutManager(getContext(), 3);
        BaseAdapter adapter = new BaseAdapter(ItemAchievementsBinding.class, AchievementsContent.class);
        RecyclerView rcv = (RecyclerView) root.findViewById(R.id.rvAchievement);
        rcv.setLayoutManager(layoutManager);
        rcv.setAdapter(adapter);

        adapter.setDataList(dataList);
        requireActivity().findViewById(R.id.nav_view).setVisibility(View.GONE);
        return root;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseActivity.setStatusBarGradient(requireActivity(), android.R.color.black);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        BaseActivity.setStatusBarGradient(requireActivity(), android.R.color.transparent);
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
