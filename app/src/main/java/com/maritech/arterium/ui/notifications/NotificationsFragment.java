package com.maritech.arterium.ui.notifications;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import com.maritech.arterium.R;
import com.maritech.arterium.common.ContentState;
import com.maritech.arterium.data.models.NotificationModel;
import com.maritech.arterium.databinding.FragmentNotificationsBinding;
import com.maritech.arterium.ui.base.BaseFragment;
import com.maritech.arterium.ui.notifications.holder.NotificationsAdapter;
import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends BaseFragment<FragmentNotificationsBinding> {

    private NotificationsViewModel notificationsViewModel;

    private NotificationsAdapter adapter;
    private NotificationsAdapter readAdapter;

    private final ArrayList<NotificationModel> newNotifications = new ArrayList<>();
    private final ArrayList<NotificationModel> earlierNotifications = new ArrayList<>();

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

        adapter = new NotificationsAdapter(newNotifications, (position, object) -> {
            if (!object.isRead()) {
                setMessageUnread(object.getId());

                readNotification(object);
            }
        });

        readAdapter = new NotificationsAdapter(earlierNotifications, (position, object) -> {

        });

        binding.rvUnreadNotifications.setAdapter(adapter);
        binding.rvReadNotifications.setAdapter(readAdapter);

        binding.toolbar.ivArrow.setOnClickListener(v -> requireActivity().onBackPressed());

        observeViewModel();

        getNotifications();
    }

    private void observeViewModel() {
        notificationsViewModel.responseLiveData.observe(lifecycleOwner,
                notificationResponse -> splitNotification(notificationResponse.getData()));

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

        });

        notificationsViewModel.readContentState.observe(lifecycleOwner, contentState -> {

        });
    }

    private void getNotifications() {
        notificationsViewModel.getNotifications();
    }

    private void readNotification(NotificationModel data) {
        notificationsViewModel.readNotification(data);
    }

    private void splitNotification(List<NotificationModel> notificationList) {
        for (int i = 0; i < notificationList.size(); i++) {
            if (notificationList.get(i).isRead()) {
                earlierNotifications.add(notificationList.get(i));
            } else {
                newNotifications.add(notificationList.get(i));
            }
        }

        adapter.notifyDataSetChanged();
        readAdapter.notifyDataSetChanged();

        setVisibilityByListSize();
    }

    private void setMessageUnread(String messageId) {
        int position = 0;
        for (int i = 0; i < newNotifications.size(); i++) {
            if (newNotifications.get(i).getId().equalsIgnoreCase(messageId)) {
                position = i;
                break;
            }
        }


        earlierNotifications.add(0, newNotifications.get(position));
        earlierNotifications.get(0).setRead(true);
        readAdapter.notifyItemInserted(0);

        newNotifications.remove(position);
        adapter.notifyItemRemoved(position);

        setVisibilityByListSize();
    }

    private void setVisibilityByListSize() {
        if (newNotifications.size() > 0) {
            binding.unreadLayout.setVisibility(View.VISIBLE);
            binding.unreadCountTv.setText(String.valueOf(newNotifications.size()));
        } else {
            binding.unreadLayout.setVisibility(View.GONE);
        }

        if (earlierNotifications.size() > 0) {
            binding.tvBefore.setVisibility(View.VISIBLE);
        } else {
            binding.tvBefore.setVisibility(View.GONE);
        }
    }
}