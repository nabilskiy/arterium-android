package com.maritech.arterium.ui.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.maritech.arterium.R;
import com.maritech.arterium.ui.ActivityActionViewModel;

public abstract class BaseFragment<T extends ViewDataBinding> extends Fragment {

    protected abstract int getContentView();

    public NavController navController = null;

    public BaseActivity baseActivity;
    public LifecycleOwner lifecycleOwner;
    public ActivityActionViewModel actionViewModel;

    public T binding;

    public boolean hasInitializedRootView = false;

    public BaseFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, getContentView(), container, false);
            binding.setLifecycleOwner(getViewLifecycleOwner());
        } else {
            ((ViewGroup)(binding.getRoot())).removeView(binding.getRoot());
        }

        actionViewModel = new ViewModelProvider(this).get(ActivityActionViewModel.class);

        lifecycleOwner = getViewLifecycleOwner();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(requireActivity(), R.id.main_host_fragment);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof BaseActivity) {
            baseActivity = ((BaseActivity) context);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        binding = null;
        baseActivity = null;
    }

    private ProgressDialog dialog;

    public void showProgressDialog() {
        if (dialog == null) {
            dialog = new ProgressDialog(requireContext());
            dialog.setMessage("Подождите..");
            dialog.show();
        }
    }

    public void hideProgressDialog() {
        if (dialog != null) {
            dialog.hide();
        }
    }
}