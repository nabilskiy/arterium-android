package com.maritech.arterium.ui.dialogs.dialog_with_recycler;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.maritech.arterium.R;

public class DialogWithRecycler extends Dialog {

    ConstraintLayout clOne;
    ConstraintLayout clTwo;
    ConstraintLayout clThree;

    ImageView ivBtnCheckOne;
    ImageView ivBtnCheckTwo;
    ImageView ivBtnCheckThree;

    TextView btnClose;
    Toast toast;
    private String data;

    public DialogWithRecycler(Context context, String data) {
        super(context, R.style.ChooseProgramDialog);
        this.data = data;
        initView(context);
    }

    private void initView(Context context) {
        setContentView(R.layout.dialog_with_recycler);

        clOne = ((ConstraintLayout) findViewById(R.id.clOne));
        clTwo = ((ConstraintLayout) findViewById(R.id.clTwo));
        clThree = ((ConstraintLayout) findViewById(R.id.clThree));

        ivBtnCheckOne = ((ImageView) findViewById(R.id.ivBtnCheckOne));
        ivBtnCheckTwo = ((ImageView) findViewById(R.id.ivBtnCheckTwo));
        ivBtnCheckThree = ((ImageView) findViewById(R.id.ivBtnCheckThree));

        btnClose = ((TextView) findViewById(R.id.tvBack));

        ivBtnCheckOne.setActivated(true);

        clOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivBtnCheckOne.setActivated(true);
                ivBtnCheckTwo.setActivated(false);
                ivBtnCheckThree.setActivated(false);

                toast = Toast.makeText(context.getApplicationContext(),
                        "Рениаль – «Ключ до життя»", Toast.LENGTH_SHORT);
                toast.show();

                dismiss();
            }
        });

        clTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivBtnCheckOne.setActivated(false);
                ivBtnCheckTwo.setActivated(true);
                ivBtnCheckThree.setActivated(false);

                toast = Toast.makeText(context.getApplicationContext(),
                        "Гліптар – «Ключ до балансу»", Toast.LENGTH_SHORT);
                toast.show();

                dismiss();
            }
        });

        clThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivBtnCheckOne.setActivated(false);
                ivBtnCheckTwo.setActivated(false);
                ivBtnCheckThree.setActivated(true);

                toast = Toast.makeText(context.getApplicationContext(),
                        "Саграда – «Квиток у майбутнє»", Toast.LENGTH_SHORT);
                toast.show();

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
