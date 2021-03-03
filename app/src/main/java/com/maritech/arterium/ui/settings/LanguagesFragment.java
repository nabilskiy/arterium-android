package com.maritech.arterium.ui.settings;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.maritech.arterium.R;
import com.maritech.arterium.data.sharePref.Pref;
import com.maritech.arterium.databinding.FragmentLanguagesBinding;
import com.maritech.arterium.ui.base.BaseFragment;
import com.maritech.arterium.ui.base.BaseNavigator;

public class LanguagesFragment extends BaseFragment<FragmentLanguagesBinding> {

    BaseNavigator navigator = new BaseNavigator();

    String ru = "ru";
    String en = "en";
    String ua = "ua";

    @Override
    protected int getContentView() {
        return R.layout.fragment_languages;
    }

    @Override
    public void onViewCreated(@NonNull View root, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);

        binding.interfaceToolbar.ivRight.setVisibility(View.INVISIBLE);

        String lang = Pref.getInstance().getLanguage(requireContext());
        setLangSelected(lang);

        binding.tvUa.setOnClickListener(v -> {
            Pref.getInstance().setLanguage(requireContext(), ua);
            setLangSelected(ua);
        });

        binding.tvRu.setOnClickListener(v -> {
            Pref.getInstance().setLanguage(requireContext(), ru);
            setLangSelected(ru);
        });

        binding.tvEn.setOnClickListener(v -> {
            Pref.getInstance().setLanguage(requireContext(), en);
            setLangSelected(en);
        });

        binding.interfaceToolbar.ivArrow.setOnClickListener(v -> requireActivity().onBackPressed());
    }

    private void setLangSelected(String lang) {
        binding.ivUa.setVisibility(lang.equalsIgnoreCase(ua) ? View.VISIBLE : View.INVISIBLE);
        binding.ivRu.setVisibility(lang.equalsIgnoreCase(ru) ? View.VISIBLE : View.INVISIBLE);
        binding.ivEn.setVisibility(lang.equalsIgnoreCase(en) ? View.VISIBLE : View.INVISIBLE);
    }

//    public void setCheckboxStatus(CheckBox view) {
//        for (int i = 0; i < binding.clNamesList.getChildCount(); i++) {
//            CheckBox checkBox = binding.clNamesList.getChildAt(i).findViewById(R.id.cbShowHide);
//            if (view.isChecked() && view == checkBox) {
//                binding.clNamesList.getChildAt(i).findViewById(R.id.cbShowHide).setVisibility(View.VISIBLE);
//            } else {
//                binding.clNamesList.getChildAt(i).findViewById(R.id.cbShowHide).setVisibility(View.INVISIBLE);
//                checkBox.setChecked(false);
//            }
//        }
//    }
//
//    public void changeInterfaceName() {
//        for (int i = 0; i < binding.clNamesList.getChildCount(); i++) {
//            binding.clNamesList.getChildAt(i).setOnClickListener(view -> {
//                CheckBox checkBox = view.findViewById(R.id.cbShowHide);
//                checkBox.setChecked(true);
//                setCheckboxStatus(checkBox);
//            });
//
//        }
//    }


}