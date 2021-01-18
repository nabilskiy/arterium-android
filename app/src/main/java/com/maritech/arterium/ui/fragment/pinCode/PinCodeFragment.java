package com.maritech.arterium.ui.fragment.pinCode;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentResultListener;

import com.alimuzaffar.lib.pin.PinEntryEditText;
import com.maritech.arterium.R;
import com.maritech.arterium.ui.base.BaseFragment;
import com.maritech.arterium.ui.choose_mp.data.ChooseMpContent;

public class PinCodeFragment extends BaseFragment {

    static final String BUNDLE_KEY = "login";
    static final String REQUEST_KEY = "requestLoginKey";
    private String typeOfUser;
    PinEntryEditText input;

    PinCodeNavigator navigator = new PinCodeNavigator();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_pin, container, false);

        input = root.findViewById(R.id.verificationCodeInput);
        input.setOnPinEnteredListener(content -> Log.d("!!!", "pin：" + content));
        final PinEntryEditText pinEntry = (PinEntryEditText) input.findViewById(R.id.verificationCodeInput);
        input.requestFocus();
        if (pinEntry != null) {
            pinEntry.setOnPinEnteredListener(new PinEntryEditText.OnPinEnteredListener() {
                @Override
                public void onPinEntered(CharSequence str) {
                    if (str.toString().equals("1111")) {
                        hideKeyboard(getActivity());
                        switch (typeOfUser) {
                            case "medical":
                                navigator.goToDashboardMpAfterEnterPin(navController);
                                break;
                            case "regional":
                                navigator.goToDashboardRmAfterEnterPin(navController);
                                break;
                            case "doctor":
                                navigator.goToDashboardDoctorAfterEnterPin(navController);
                                break;
                        }
                    } else {
                        pinEntry.setText(null);
                    }
                }
            });
        }
        requireActivity().findViewById(R.id.nav_view).setVisibility(View.GONE);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

        input.requestFocus();
        InputMethodManager imgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imgr.showSoftInput(input, InputMethodManager.SHOW_IMPLICIT);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getParentFragmentManager().setFragmentResultListener(REQUEST_KEY, this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                typeOfUser = bundle.getString(BUNDLE_KEY);
            }
        });
    }

    public void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
