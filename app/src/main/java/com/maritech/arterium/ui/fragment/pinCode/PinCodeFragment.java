package com.maritech.arterium.ui.fragment.pinCode;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;

import com.alimuzaffar.lib.pin.PinEntryEditText;
import com.maritech.arterium.R;
import com.maritech.arterium.ui.base.BaseFragment;

public class PinCodeFragment extends BaseFragment {

    PinCodeNavigator navigator = new PinCodeNavigator();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_pin, container, false);

        PinEntryEditText input = root.findViewById(R.id.verificationCodeInput);
        input.setOnPinEnteredListener(content -> Log.d("!!!", "pin：" + content));
        final PinEntryEditText pinEntry = (PinEntryEditText) input.findViewById(R.id.verificationCodeInput);
        input.requestFocus();
        if (pinEntry != null) {
            pinEntry.setOnPinEnteredListener(new PinEntryEditText.OnPinEnteredListener() {
                @Override
                public void onPinEntered(CharSequence str) {
                    if (str.toString().equals("1111")) {
                        hideKeyboard(getActivity());
                        navigator.goToDashboardDoctorAfterEnterPin(navController);
//                        navigator.goToDashboardMpAfterEnterPin(navController);
                    } else {
                        pinEntry.setText(null);
                    }
                }
            });
        }
        requireActivity().findViewById(R.id.nav_view).setVisibility(View.GONE);
        return root;
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
