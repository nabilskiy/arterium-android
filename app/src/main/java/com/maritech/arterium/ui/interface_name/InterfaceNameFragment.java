package com.maritech.arterium.ui.interface_name;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.maritech.arterium.R;
import com.maritech.arterium.databinding.FragmentInterfaceNameBinding;
import com.maritech.arterium.ui.base.BaseFragment;
import com.maritech.arterium.ui.base.BaseNavigator;

public class InterfaceNameFragment extends BaseFragment<FragmentInterfaceNameBinding> {

    BaseNavigator navigator = new BaseNavigator();

    @Override
    protected int getContentView() {
        return R.layout.fragment_interface_name;
    }

    @Override
    public void onViewCreated(@NonNull View root, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);

        binding.interfaceToolbar.ivRight.setVisibility(View.INVISIBLE);

        binding.russian.tvInterfaceName.setText(R.string.russian);
        binding.english.tvInterfaceName.setText(R.string.english);
        binding.ukrainian.tvInterfaceName.setText(R.string.ukrainian);

        binding.interfaceToolbar.ivArrow.setOnClickListener(v -> requireActivity().onBackPressed());

        setCheckboxStatus(binding.ukrainian.cbShowHide);
        changeInterfaceName();
    }

    public void setCheckboxStatus(CheckBox view) {
        for (int i = 0; i < binding.clNamesList.getChildCount(); i++) {
            CheckBox checkBox = binding.clNamesList.getChildAt(i).findViewById(R.id.cbShowHide);
            if (view.isChecked() && view == checkBox) {
                binding.clNamesList.getChildAt(i).findViewById(R.id.cbShowHide).setVisibility(View.VISIBLE);
            } else {
                binding.clNamesList.getChildAt(i).findViewById(R.id.cbShowHide).setVisibility(View.INVISIBLE);
                checkBox.setChecked(false);
            }
        }
    }

    public void changeInterfaceName() {
        for (int i = 0; i < binding.clNamesList.getChildCount(); i++) {
            binding.clNamesList.getChildAt(i).setOnClickListener(view -> {
                CheckBox checkBox = view.findViewById(R.id.cbShowHide);
                checkBox.setChecked(true);
                setCheckboxStatus(checkBox);
            });

        }
    }
}