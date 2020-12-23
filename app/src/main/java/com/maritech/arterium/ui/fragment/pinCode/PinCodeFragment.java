package com.maritech.arterium.ui.fragment.pinCode;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        if (pinEntry != null) {
            pinEntry.setOnPinEnteredListener(new PinEntryEditText.OnPinEnteredListener() {
                @Override
                public void onPinEntered(CharSequence str) {
                    if (str.toString().equals("1111")) {
                        navigator.goToDashboardRmAfterEnterPin(navController);
                    } else {
                        pinEntry.setText(null);
                    }
                }
            });
        }
        requireActivity().findViewById(R.id.nav_view).setVisibility(View.GONE);
        return root;
    }
}
