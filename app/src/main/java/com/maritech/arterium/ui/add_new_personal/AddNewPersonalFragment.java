package com.maritech.arterium.ui.add_new_personal;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;

import com.maritech.arterium.R;
import com.maritech.arterium.ui.base.BaseFragment;
import com.maritech.arterium.ui.widgets.CustomInput;
import com.tomergoldst.tooltips.ToolTip;
import com.tomergoldst.tooltips.ToolTipsManager;


import io.card.payment.CardIOActivity;
import io.card.payment.CreditCard;

public class AddNewPersonalFragment extends BaseFragment {

    private static final int SCAN_REQUEST_CODE = 364;

    private TextView tvTabOne;
    private TextView tvTabTwo;

    private View viewProgressOne;
    private View viewProgressTwo;
    private RadioGroup radioGroup;
    private TextView btnNextOne;
    private Boolean isTwoStep = false;
    private ImageView btnBack;
    private ImageView btnAuto;

    private TextView tvToolbarTitle;
    private TextView tvHint;
    private TextView tvToolTip;

    private ConstraintLayout clProgressStepOne;
    private ConstraintLayout clProgressStepTwo;
    private ConstraintLayout clInfoUser;

    private ImageView ivCamera;

    private CustomInput ccInputCardNumber;

    AddNewPersonalNavigator navigator = new AddNewPersonalNavigator();
    private AddNewPersonalViewModel addNewPersonalViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        addNewPersonalViewModel = new ViewModelProvider(this).get(AddNewPersonalViewModel.class);
        View root = inflater.inflate(R.layout.fragment_add_new_personal, container, false);

        clProgressStepOne = root.findViewById(R.id.clProgressStepOne);
        clProgressStepTwo = root.findViewById(R.id.clProgressStepTwo);
        tvToolbarTitle = root.findViewById(R.id.toolbar).findViewById(R.id.tvToolbarTitle);
        tvHint = root.findViewById(R.id.toolbar).findViewById(R.id.tvHint);
        tvHint.setVisibility(View.VISIBLE);
        radioGroup = root.findViewById(R.id.radioGroup);
        btnNextOne = root.findViewById(R.id.btnNextOne);
        viewProgressOne = root.findViewById(R.id.toolbar).findViewById(R.id.viewOne);
        viewProgressOne.setActivated(true);
        viewProgressTwo = root.findViewById(R.id.toolbar).findViewById(R.id.viewTwo);
        btnBack = root.findViewById(R.id.toolbar).findViewById(R.id.ivRight);
        btnAuto = root.findViewById(R.id.toolbar).findViewById(R.id.ivLeft);
        ivCamera = root.findViewById(R.id.ivCamera);
        ccInputCardNumber = root.findViewById(R.id.ccInputCardNumber);
        tvToolTip = root.findViewById(R.id.tvToolTip);

        ToolTipsManager mToolTipsManager;
        mToolTipsManager = new ToolTipsManager();
        mToolTipsManager.findAndDismiss(tvToolTip);
        ToolTip.Builder builder = new ToolTip.Builder(getContext(), tvToolTip, clProgressStepTwo, "Tip message", ToolTip.POSITION_ABOVE);
        builder.setAlign(ToolTip.ALIGN_RIGHT);
        builder.setTextAppearance(R.style.sf_pro_text_14);
        builder.setBackgroundColor(getResources().getColor(R.color.white));
        mToolTipsManager.show(builder.build());

        tvTabOne = root.findViewById(R.id.tvOne);
        tvTabTwo = root.findViewById(R.id.tvTwo);

        tvTabOne.setActivated(true);


        tvToolbarTitle.setText("Новий пацієнт");
        tvHint.setText("Медичні дані");


        btnNextOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewProgressTwo.setActivated(true);
                isTwoStep = true;

                clProgressStepOne.setVisibility(View.GONE);
                clProgressStepTwo.setVisibility(View.VISIBLE);

                btnAuto.setVisibility(View.VISIBLE);

            }
        });

        ivCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onScanPress(v);

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTwoStep) {
                    isTwoStep = false;
                    viewProgressTwo.setActivated(false);

                    clProgressStepOne.setVisibility(View.VISIBLE);
                    clProgressStepTwo.setVisibility(View.GONE);

                    btnAuto.setVisibility(View.GONE);

                } else {
                    requireActivity().onBackPressed();
                }


            }
        });


        requireActivity().findViewById(R.id.nav_view).setVisibility(View.GONE);
        return root;
    }

    public void autoFill(){

    }

    public void onScanPress(View v) {
        Intent scanIntent = new Intent(getContext(), CardIOActivity.class);

        // customize these values to suit your needs.
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, true); // default: false
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, false); // default: false
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_POSTAL_CODE, false); // default: false

        // MY_SCAN_REQUEST_CODE is arbitrary and is only used within this activity.
        startActivityForResult(scanIntent, SCAN_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SCAN_REQUEST_CODE) {
            if (data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
                CreditCard scanResult = data.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT);
                ccInputCardNumber.setInput("Номер картки", scanResult.cardNumber);
            }
        }

    }
}

