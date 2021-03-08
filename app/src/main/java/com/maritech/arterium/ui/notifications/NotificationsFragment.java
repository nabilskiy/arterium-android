package com.maritech.arterium.ui.notifications;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.maritech.arterium.R;
import com.maritech.arterium.common.ContentState;
import com.maritech.arterium.data.models.NotificationResponse;
import com.maritech.arterium.databinding.FragmentNotificationsBinding;
import com.maritech.arterium.ui.base.BaseFragment;
import com.maritech.arterium.ui.notifications.data.NotificationsContent;
import com.maritech.arterium.ui.notifications.holder.NotificationsAdapter;
import com.maritech.arterium.utils.ToastUtil;

import java.util.ArrayList;

public class NotificationsFragment extends BaseFragment<FragmentNotificationsBinding> {

    private NotificationsViewModel notificationsViewModel;

    private NotificationsAdapter adapter;

    private ArrayList<NotificationResponse.Data> notificationList = new ArrayList<>();

    @Override
    protected int getContentView() {
        return R.layout.fragment_notifications;
    }

    @Override
    public void onViewCreated(@NonNull View root,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);

        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding.toolbar.ivRight.setVisibility(View.GONE);
        binding.toolbar.ivArrow.setVisibility(View.GONE);
        binding.toolbar.tvToolbarTitle.setText(getString(R.string.notification));

        adapter = new NotificationsAdapter(notificationList, (position, object) -> {

        });

        binding.rvNotifications.setAdapter(adapter);

        binding.toolbar.ivArrow.setOnClickListener(v -> requireActivity().onBackPressed());

        observeViewModel();

        getNotifications();
    }

    private void observeViewModel() {
        notificationsViewModel.responseLiveData.observe(lifecycleOwner, notificationResponse -> {
            notificationList.clear();
            notificationList.addAll(notificationResponse.getData());
            adapter.notifyDataSetChanged();
        });

        notificationsViewModel.contentState.observe(this, contentState -> {
            if (contentState == ContentState.LOADING) {
                binding.progressBar.setVisibility(View.VISIBLE);
            } else {
                binding.progressBar.setVisibility(View.GONE);
            }

            binding.emptyContainer.setVisibility(
                    contentState == ContentState.ERROR ? View.VISIBLE : View.GONE
            );

            binding.emptyContainer.setVisibility(
                    contentState == ContentState.EMPTY ? View.VISIBLE : View.GONE
            );
        });
    }

    private void getNotifications() {
        notificationsViewModel.getNotifications();
    }

}