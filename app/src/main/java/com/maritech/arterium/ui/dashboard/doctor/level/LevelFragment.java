package com.maritech.arterium.ui.dashboard.doctor.level;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.maritech.arterium.R;
import com.maritech.arterium.databinding.FragmentLevelBinding;

public class LevelFragment extends BottomSheetDialogFragment {

    OnLevelCloseListener levelCloseListener;

    FragmentLevelBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.ChooseProgramDialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_level, container, false);

        binding.backIv.setOnClickListener(v -> {
            if (levelCloseListener != null) {
                levelCloseListener.onClose();
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    public void setListener(OnLevelCloseListener levelCloseListener) {
        this.levelCloseListener = levelCloseListener;
    }

    public interface OnLevelCloseListener {
        void onClose();
    }
}
