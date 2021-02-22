package com.maritech.arterium.ui.patients;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.maritech.arterium.R;
import com.maritech.arterium.common.ContentState;
import com.maritech.arterium.data.models.PatientModel;
import com.maritech.arterium.ui.base.BaseFragment;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

import io.card.payment.CardIOActivity;
import io.card.payment.CreditCard;
import okhttp3.ResponseBody;

public class PatientCardFragment extends BaseFragment {
    private static final int SCAN_REQUEST_CODE = 660;
    public static final String PATIENT_MODEL_KEY = "patientModel";

    TextView patientCardNumberValue;
    TextView toolbarTitle;
    ImageView ivArrow;
    ImageView ivMyProfileLogo;

    PatientModel model = null;
    PatientsViewModel viewModel;

    private final SimpleDateFormat dateFormat =
            new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());

    @Override
    protected int getContentView() {
        return R.layout.fragment_patient_card;
    }

    @Override
    public void onViewCreated(@NonNull View root, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(PatientsViewModel.class);

        if (getArguments() != null) {
            model = getArguments().getParcelable(PATIENT_MODEL_KEY);
        }

        toolbarTitle = root.findViewById(R.id.patientCardToolbar).findViewById(R.id.tvToolbarTitle);
        toolbarTitle.setText(R.string.patient_card);
        ivMyProfileLogo = root.findViewById(R.id.ivMyProfileLogo);
        ivArrow = root.findViewById(R.id.patientCardToolbar).findViewById(R.id.ivArrow);
        ivArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().onBackPressed();
            }
        });

        setPersonalCardData(root);
        setMedicalCardData(root);
//        Log.e("Bottom", this.getClass().getName());
//        baseActivity.findViewById(R.id.bottom_nav_view).setVisibility(View.GONE);

        observeViewModel();

        loadImage();
    }

    @SuppressLint("CutPasteId")
    public void setMedicalCardData(View root){
        ImageView medicalDataIcon;
        TextView tvPatientCardName;
        TextView medicalTitle;
        TextView heartAttack;
        TextView heartAttackValue;
        TextView drugAdministration;
        TextView drugAdministrationValue;
        TextView ejectionFraction;
        TextView ejectionFractionValue;
        TextView potassiumStart;
        TextView potassiumStartValue;
        TextView potassiumEnd;
        TextView potassiumEndValue;

        //medical data
        medicalDataIcon = root.findViewById(R.id.patientMedicalData).findViewById(R.id.ivPatientDataIcon);
        medicalDataIcon.setBackgroundResource(R.drawable.ic_medical_data);
        medicalTitle = root.findViewById(R.id.patientMedicalData).findViewById(R.id.tvPatientData);
        medicalTitle.setText(R.string.medical_data);

        // heart attack
        heartAttack = root.findViewById(R.id.patientHeartAttack).findViewById(R.id.tvPatientDataListTitle);
        heartAttack.setText(R.string.heart_attack);
        heartAttackValue = root.findViewById(R.id.patientHeartAttack).findViewById(R.id.tvPatientDataListValue);

        if (model.getHearthAttackDate() != null) {
            long millis = model.getHearthAttackDate() * 1000;
            heartAttackValue.setText(dateFormat.format(new Date(millis)));
        }


        //drug
        drugAdministration = root.findViewById(R.id.dateOfDrugAdministration).findViewById(R.id.tvPatientDataListTitle);
        drugAdministration.setText(R.string.drug_administration);
        drugAdministrationValue = root.findViewById(R.id.dateOfDrugAdministration).findViewById(R.id.tvPatientDataListValue);

        if (model.getPrescribingDate() != null) {
            long millis = model.getPrescribingDate() * 1000;
            drugAdministrationValue.setText(dateFormat.format(millis));
        }

        //ejection
        ejectionFraction = root.findViewById(R.id.ejectionFraction).findViewById(R.id.tvPatientDataListTitle);
        ejectionFraction.setText(R.string.ejection_fraction);
        ejectionFractionValue = root.findViewById(R.id.ejectionFraction).findViewById(R.id.tvPatientDataListValue);
        if (model.getEjectionFraction() != null) {
            ejectionFractionValue.setText(model.getEjectionFraction());
        }

        //potassium start
        potassiumStart = root.findViewById(R.id.potassiumLevelOfStartTreatment).findViewById(R.id.tvPatientDataListTitle);
        potassiumStart.setText(R.string.potassium_start);
        potassiumStartValue = root.findViewById(R.id.potassiumLevelOfStartTreatment).findViewById(R.id.tvPatientDataListValue);
        if (model.getInitialPotassium() != null) {
            potassiumStartValue.setText(model.getInitialPotassium());
        }

        //potassium end
        potassiumEnd = root.findViewById(R.id.potassiumLevelOfTheEndTreatment).findViewById(R.id.tvPatientDataListTitle);
        potassiumEnd.setText(R.string.potassium_end);
        potassiumEndValue = root.findViewById(R.id.potassiumLevelOfTheEndTreatment).findViewById(R.id.tvPatientDataListValue);
        potassiumStartValue = root.findViewById(R.id.potassiumLevelOfStartTreatment).findViewById(R.id.tvPatientDataListValue);
        if (model.getFinalPotassium() != null) {
            potassiumEndValue.setText(model.getFinalPotassium());
        }
        root.findViewById(R.id.potassiumLevelOfTheEndTreatment).findViewById(R.id.vDivideLine).setVisibility(View.INVISIBLE);
    }

    @SuppressLint("CutPasteId")
    public void setPersonalCardData(View root){
        TextView sex;
        TextView sexValue;
        TextView weight;
        TextView weightValue;
        TextView growth;
        TextView growthValue;
        TextView dose;
        TextView doseValue;
        TextView patientCardNumber;
        ImageView ivPatientDataCardIcon;

        TextView tvPatientCardName = root.findViewById(R.id.tvPatientCardName);
        tvPatientCardName.setText(model.getName());
        TextView tvPatientCardLastShopping = root.findViewById(R.id.tvPatientCardLastShopping);

        Long lastPurchases = model.getLastPurchaseAt();
        if (lastPurchases == null) {
            tvPatientCardLastShopping.setText(requireContext().getString(R.string.without_purchases));
            tvPatientCardLastShopping.setTextColor(Color.parseColor("#FF3347"));
        } else {
            tvPatientCardLastShopping.setText(getString(
                            R.string.last_purchases, dateFormat.format(
                                    new Date(lastPurchases * 1000)
                            ))
            );
            tvPatientCardLastShopping.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray));
        }
        TextView tvPatientCardShoppingAmount = root.findViewById(R.id.tvPatientCardShoppingAmount);
        tvPatientCardShoppingAmount.setText(
                getString(R.string.whole_shopping_items1, model.getPurchasesCount())
        );

        //sex
        sex = root.findViewById(R.id.patientSex).findViewById(R.id.tvPatientDataListTitle);
        sex.setText(R.string.sex);
        sexValue = root.findViewById(R.id.patientSex).findViewById(R.id.tvPatientDataListValue);
        if (model.getGender() != null) {
            if (model.getGender().equals("m")) {
                sexValue.setText(R.string.male);
            } else {
                sexValue.setText(R.string.female);
            }
        }

        patientCardNumber = root.findViewById(R.id.patientCardNumber).findViewById(R.id.tvPatientDataCardTitle);
        patientCardNumber.setText("Номер картки");
        patientCardNumberValue = root.findViewById(R.id.patientCardNumber).findViewById(R.id.tvPatientDataCardValue);
        patientCardNumberValue.setText(model.getCardCode());
        ivPatientDataCardIcon = root.findViewById(R.id.patientCardNumber).findViewById(R.id.ivPatientDataCardIcon);


        //weight
        weight = root.findViewById(R.id.patientWeight).findViewById(R.id.tvPatientDataListTitle);
        weight.setText(R.string.weight);
        weightValue = root.findViewById(R.id.patientWeight).findViewById(R.id.tvPatientDataListValue);
        weightValue.setText(String.valueOf(model.getWeight()));

        //growth
        growth = root.findViewById(R.id.patientGrowth).findViewById(R.id.tvPatientDataListTitle);
        growth.setText(R.string.growth);
        growthValue = root.findViewById(R.id.patientGrowth).findViewById(R.id.tvPatientDataListValue);
        growthValue.setText(String.valueOf(model.getHeight()));

        //dose
        dose = root.findViewById(R.id.patientDrugsDose).findViewById(R.id.tvPatientDataListTitle);
        dose.setText(R.string.dose);
        doseValue = root.findViewById(R.id.patientDrugsDose).findViewById(R.id.tvPatientDataListValue);
        doseValue.setText(String.valueOf(model.getDose()));
        root.findViewById(R.id.patientDrugsDose).findViewById(R.id.vDivideLine).setVisibility(View.INVISIBLE);
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

    private void observeViewModel() {
        viewModel.imageResponse.observe(getViewLifecycleOwner(), responseBody -> {
            Bitmap bitmap = BitmapFactory.decodeStream(responseBody.byteStream());
            ivMyProfileLogo.setImageBitmap(bitmap);
        });
        viewModel.imageState.observe(getViewLifecycleOwner(), new Observer<ContentState>() {
            @Override
            public void onChanged(ContentState contentState) {
                if (contentState == ContentState.ERROR) {
                    ivMyProfileLogo.setImageResource(R.drawable.user_placeholder);
                }
            }
        });
    }

    private void loadImage() {
        viewModel.getPatientImage(model.getId());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SCAN_REQUEST_CODE) {
            if (data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
                CreditCard scanResult = data.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT);
                patientCardNumberValue.setText(scanResult.cardNumber);
            }
        }

    }
}