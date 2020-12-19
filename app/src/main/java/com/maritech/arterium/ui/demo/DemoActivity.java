package com.maritech.arterium.ui.demo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.maritech.arterium.R;
import com.maritech.arterium.ui.base.BaseActivity;

public class DemoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

//          SWITCH USE
//        switchView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                boolean isOpened = switchView.isOpened();
//            }
//        });
//
//        VerificationCodeInput input = findViewById(R.id.verificationCodeInput);
//        input.setOnCompleteListener(content -> Log.d("!!!", "pin：" + content));
//        final PinEntryEditText pinEntry = (PinEntryEditText) findViewById(R.id.txt_pin_entry);
//        if (pinEntry != null) {
//            pinEntry.setOnPinEnteredListener(new PinEntryEditText.OnPinEnteredListener() {
//                @Override
//                public void onPinEntered(CharSequence str) {
//                    if (str.toString().equals("1234")) {
//                    } else {
//                        pinEntry.setText(null);
//                    }
//                }
//            });
//        }

        showDialog();

    }
    public void showDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        DialogWithRecycler newFragment = new DialogWithRecycler();
        newFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.TransparentDialog);
        newFragment.show(fragmentManager, "dialog");
    }
}