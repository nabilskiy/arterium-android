package com.maritech.arterium.ui.dialogs.dialog_new_account;

import android.app.Dialog;
import android.content.Context;
import android.net.sip.SipSession;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;

import com.maritech.arterium.R;
import com.maritech.arterium.ui.dashboardRm.DashboardRmFragment;
import com.maritech.arterium.ui.dashboardRm.DashboardRmNavigator;
import com.maritech.arterium.ui.dashboardRm.DialogListener;

import java.sql.ResultSet;

public class DialogNewAccount extends Dialog {

    ConstraintLayout clOne;
    ConstraintLayout clTwo;

    Boolean isAddDoctor;

    SipSession.Listener listener = null;
    TextView btnClose;


     DialogListener data;



    public DialogNewAccount(Context context, DialogListener data) {
        super(context, R.style.ChooseProgramDialog);
        this.data = data;
        initView(context);
    }

    private void initView(Context context) {
        setContentView(R.layout.dialog_new_account);

        clOne = ((ConstraintLayout) findViewById(R.id.clOne));
        clTwo = ((ConstraintLayout) findViewById(R.id.clTwo));

        btnClose = ((TextView) findViewById(R.id.tvBack));


        clOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.addDoctor();

                dismiss();
            }
        });

        clTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();
            }
        });


        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        setCanceledOnTouchOutside(true);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.gravity = Gravity.BOTTOM;
        attributes.y = 50;

        getWindow().setAttributes(attributes);
    }

}
