package com.maritech.arterium.ui.add_new_personal;

import android.graphics.Color;
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

import com.bobekos.bobek.scanner.BarcodeView;
import com.maritech.arterium.R;
import com.maritech.arterium.ui.base.BaseFragment;
import com.maritech.arterium.ui.widgets.CustomInput;
import com.nhaarman.supertooltips.ToolTip;
import com.nhaarman.supertooltips.ToolTipRelativeLayout;

import io.reactivex.disposables.Disposable;


public class AddNewPersonalFragment extends BaseFragment{

//    private static final int SCAN_REQUEST_CODE = 364;
    private static final String TAG = "scan_card";

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

    private CustomInput ccInputDateInfarct;
    private CustomInput ccDateOfStartDrug;
    private CustomInput ccInputFraction;
    private CustomInput ccInputFecesStart;
    private CustomInput ccInputFecesEnd;
    private CustomInput ccInputWeight;
    private CustomInput ccInputHeight;


    private ImageView ivCamera;

    private Disposable mDisposable;
    BarcodeView barcodeView;
    ToolTipRelativeLayout vTooltip;

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
        vTooltip = root.findViewById(R.id.tooltip);

        ccInputDateInfarct = root.findViewById(R.id.ccInputDateInfarct);
        ccDateOfStartDrug = root.findViewById(R.id.ccDateOfStartDrug);
        ccInputFraction = root.findViewById(R.id.ccInputFraction);
        ccInputFecesStart = root.findViewById(R.id.ccInputFecesStart);
        ccInputFecesEnd = root.findViewById(R.id.ccInputFecesEnd);
        ccInputWeight = root.findViewById(R.id.ccInputWeight);
        ccInputHeight = root.findViewById(R.id.ccInputHeight);

        tvTabOne = root.findViewById(R.id.tvOne);
        tvTabTwo = root.findViewById(R.id.tvTwo);

        tvTabOne.setActivated(true);

        tvToolbarTitle.setText("Новий пацієнт");
        tvHint.setText("Медичні дані");

        ToolTip toolTip = new ToolTip()
                .withText("Використати автозаповнення")
                .withColor(Color.WHITE)
                .withShadow();


        btnNextOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewProgressTwo.setActivated(true);
                isTwoStep = true;

                clProgressStepOne.setVisibility(View.GONE);
                clProgressStepTwo.setVisibility(View.VISIBLE);

                vTooltip.showToolTipForView(toolTip, btnAuto);
                btnAuto.setVisibility(View.VISIBLE);

            }
        });

        btnAuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewProgressTwo.setActivated(true);
                isTwoStep = true;
                ccInputDateInfarct.setInput("Дата інфаркту", "1");
                ccDateOfStartDrug.setInput("Дата призначення препарату", "1");
                ccInputFraction.setInput("Фракція викиду", "1");
                ccInputFecesStart.setInput("Рівень калію на початку лікування, ммоль/л", "1");
                ccInputFecesEnd.setInput("Рівень калію в кінці лікування, ммоль/л", "1");
                ccInputWeight.setInput("Вага, кг", "1");
                ccInputHeight.setInput("Рiст, см", "1");

                vTooltip.setVisibility(View.GONE);

            }
        });

        ivCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mDisposable = barcodeView
//                        .getObservable()
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe( barcode -> {
//                            ccInputCardNumber.setInput("", barcode.displayValue);
//                        },throwable -> {});


//                                { barcode ->
//                                        //handle barcode object
//                                },
//                                { throwable ->
//                                        //handle exceptions like no available camera for selected facing
//                                });
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

}

