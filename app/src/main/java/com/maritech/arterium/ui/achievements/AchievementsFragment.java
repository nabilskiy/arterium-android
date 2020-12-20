package com.maritech.arterium.ui.achievements;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.maritech.arterium.R;
import com.maritech.arterium.databinding.ItemAchievementsBinding;
import com.maritech.arterium.ui.achievements.data.AchievementsContent;
import com.maritech.arterium.ui.base.BaseAdapter;
import com.maritech.arterium.ui.profile.HomeViewModel;

import java.util.ArrayList;

public class AchievementsFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private GridLayoutManager layoutManager;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_achievement, container, false);

        ArrayList<AchievementsContent> dataList = new ArrayList<AchievementsContent>();
        prepareList(dataList);

        layoutManager = new GridLayoutManager(getContext(), 3);
        BaseAdapter adapter = new BaseAdapter(ItemAchievementsBinding.class, AchievementsContent.class);
        RecyclerView rcv = (RecyclerView) root.findViewById(R.id.rvAchievement);
        rcv.setLayoutManager(layoutManager);
        rcv.setAdapter(adapter);

        adapter.setDataList(dataList);

        return root;
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
