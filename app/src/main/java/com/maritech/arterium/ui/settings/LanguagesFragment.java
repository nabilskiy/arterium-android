package com.maritech.arterium.ui.settings;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.maritech.arterium.R;
import com.maritech.arterium.data.sharePref.Pref;
import com.maritech.arterium.databinding.FragmentLanguagesBinding;
import com.maritech.arterium.ui.ActivityActionViewModel;
import com.maritech.arterium.ui.base.BaseFragment;
import com.maritech.arterium.ui.base.BaseNavigator;

public class LanguagesFragment extends BaseFragment<FragmentLanguagesBinding> {

    BaseNavigator navigator = new BaseNavigator();

    String ru = "ru";
    String en = "en";
    String ua = "uk";

    @Override
    protected int getContentView() {
        return R.layout.fragment_languages;
    }

    @Override
    public void onViewCreated(@NonNull View root, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);
        actionViewModel =
                new ViewModelProvider(requireActivity()).get(ActivityActionViewModel.class);

        binding.interfaceToolbar.ivRight.setVisibility(View.INVISIBLE);
        String lang = Pref.getInstance().getLanguage(requireContext());
        setLangSelected(lang);
        binding.tvUa.setOnClickListener(v -> {
            Pref.getInstance().setLanguage(requireContext(), ua);
            setLangSelected(ua);
            actionViewModel.onRecreate.setValue(true);
        });

        binding.tvRu.setOnClickListener(v -> {
            Pref.getInstance().setLanguage(requireContext(), ru);
            setLangSelected(ru);
            actionViewModel.onRecreate.setValue(true);
        });

        binding.tvEn.setOnClickListener(v -> {
            Pref.getInstance().setLanguage(requireContext(), en);
            setLangSelected(en);
            actionViewModel.onRecreate.setValue(true);
        });

        binding.interfaceToolbar.ivArrow.setOnClickListener(v -> requireActivity().onBackPressed());
    }

    private void setLangSelected(String lang) {
        binding.ivUa.setVisibility(lang.equalsIgnoreCase(ua) ? View.VISIBLE : View.INVISIBLE);
        binding.ivRu.setVisibility(lang.equalsIgnoreCase(ru) ? View.VISIBLE : View.INVISIBLE);
        binding.ivEn.setVisibility(lang.equalsIgnoreCase(en) ? View.VISIBLE : View.INVISIBLE);
    }

}