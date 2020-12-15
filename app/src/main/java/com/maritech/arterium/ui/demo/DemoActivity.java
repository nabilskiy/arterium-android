package com.maritech.arterium.ui.demo;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.maritech.arterium.R;
import com.maritech.arterium.ui.base.BaseActivity;
import com.maritech.arterium.ui.dialogs.dialog_with_recycler.DialogWithRecycler;

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


        showDialog();

    }
    public void showDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        DialogWithRecycler newFragment = new DialogWithRecycler();
        newFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.TransparentDialog);
        newFragment.show(fragmentManager, "dialog");
    }
}