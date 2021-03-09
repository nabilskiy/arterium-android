package com.maritech.arterium.ui.notifications;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import com.maritech.arterium.R;
import com.maritech.arterium.common.ContentState;
import com.maritech.arterium.data.models.NotificationResponse;
import com.maritech.arterium.databinding.FragmentNotificationsBinding;
import com.maritech.arterium.ui.base.BaseFragment;
import com.maritech.arterium.ui.notifications.holder.NotificationsAdapter;
import java.util.ArrayList;

public class NotificationsFragment extends BaseFragment<FragmentNotificationsBinding> {

    private NotificationsViewModel notificationsViewModel;

    private NotificationsAdapter adapter;

    private final ArrayList<NotificationResponse.Data> notificationList = new ArrayList<>();

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
            if (!object.isRead()) {
                notificationList.get(position).setRead(true);
                adapter.notifyItemChanged(position);

                readNotification(object);
            }
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

        notificationsViewModel.readLiveData.observe(lifecycleOwner, model -> {
            if (!model.isRead()) {
                setMessageUnread(model.getId());
            }
        });

        notificationsViewModel.readContentState.observe(lifecycleOwner, contentState -> {

        });
    }

    private void getNotifications() {
        notificationsViewModel.getNotifications();
    }

    private void readNotification(NotificationResponse.Data data) {
        notificationsViewModel.readNotification(data);
    }

    private void setMessageUnread(String messageId) {
        for (int i = 0; i < notificationList.size(); i++) {
            if (notificationList.get(i).getMessage().equalsIgnoreCase(messageId)) {
                notificationList.get(i).setRead(false);
                adapter.notifyItemChanged(i);
                break;
            }
        }
    }
}