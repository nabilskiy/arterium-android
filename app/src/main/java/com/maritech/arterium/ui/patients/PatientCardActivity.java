package com.maritech.arterium.ui.patients;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.maritech.arterium.R;
import com.maritech.arterium.common.ContentState;
import com.maritech.arterium.data.models.PatientModel;
import com.maritech.arterium.databinding.ActivityPatientCardBinding;
import com.maritech.arterium.ui.base.BaseActivity;
import com.maritech.arterium.ui.patients.add_new_personal.AddNewPersonalActivity;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;
import io.card.payment.CardIOActivity;
import io.card.payment.CreditCard;

public class PatientCardActivity extends BaseActivity<ActivityPatientCardBinding> {

    private static final int SCAN_REQUEST_CODE = 660;
    private static final int EDIT_REQUEST_CODE = 680;
    public static final String PATIENT_MODEL_KEY = "patientModel";

    PatientModel model = null;
    PatientsViewModel viewModel;

    private final SimpleDateFormat dateFormat =
            new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());

    @Override
    protected int getLayoutId() {
        return R.layout.activity_patient_card;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(PatientsViewModel.class);

        if (getIntent() != null) {
            model = getIntent().getParcelableExtra(PATIENT_MODEL_KEY);
        }

        binding.patientCardToolbar.tvToolbarTitle.setText(R.string.patient_card);
        binding.patientCardToolbar.ivArrow.setOnClickListener(v -> finish());
        binding.patientCardToolbar.ivRight.setOnClickListener(v -> {
            Intent intent = new Intent(
                    PatientCardActivity.this, AddNewPersonalActivity.class
            );
            intent.putExtra(AddNewPersonalActivity.EDIT_EXTRA_KEY, true);
            intent.putExtra(PATIENT_MODEL_KEY, model);
            startActivityForResult(intent, EDIT_REQUEST_CODE);
        });

        setPersonalCardData();

        setMedicalCardData();

        observeViewModel();

        loadImage();
    }

    @SuppressLint("CutPasteId")
    public void setMedicalCardData(){
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
        medicalDataIcon = findViewById(R.id.patientMedicalData)
                .findViewById(R.id.ivPatientDataIcon);
        medicalDataIcon.setBackgroundResource(R.drawable.ic_medical_data);
        medicalTitle = findViewById(R.id.patientMedicalData)
                .findViewById(R.id.tvPatientData);
        medicalTitle.setText(R.string.medical_data);

        // heart attack
        heartAttack = findViewById(R.id.patientHeartAttack)
                .findViewById(R.id.tvPatientDataListTitle);
        heartAttack.setText(R.string.heart_attack);
        heartAttackValue = findViewById(R.id.patientHeartAttack)
                .findViewById(R.id.tvPatientDataListValue);

        if (model.getHearthAttackDate() != null) {
            long millis = model.getHearthAttackDate() * 1000;
            heartAttackValue.setText(dateFormat.format(new Date(millis)));
        }

        //drug
        drugAdministration = findViewById(R.id.dateOfDrugAdministration)
                .findViewById(R.id.tvPatientDataListTitle);
        drugAdministration.setText(R.string.drug_administration);
        drugAdministrationValue = findViewById(R.id.dateOfDrugAdministration)
                .findViewById(R.id.tvPatientDataListValue);

        if (model.getPrescribingDate() != null) {
            long millis = model.getPrescribingDate() * 1000;
            drugAdministrationValue.setText(dateFormat.format(millis));
        }

        //ejection
        ejectionFraction = findViewById(R.id.ejectionFraction)
                .findViewById(R.id.tvPatientDataListTitle);
        ejectionFraction.setText(R.string.ejection_fraction);
        ejectionFractionValue = findViewById(R.id.ejectionFraction)
                .findViewById(R.id.tvPatientDataListValue);
        if (model.getEjectionFraction() != null) {
            ejectionFractionValue.setText(model.getEjectionFraction());
        }

        //potassium start
        potassiumStart = findViewById(R.id.potassiumLevelOfStartTreatment)
                .findViewById(R.id.tvPatientDataListTitle);
        potassiumStart.setText(R.string.potassium_start);
        potassiumStartValue = findViewById(R.id.potassiumLevelOfStartTreatment)
                .findViewById(R.id.tvPatientDataListValue);
        if (model.getInitialPotassium() != null) {
            potassiumStartValue.setText(model.getInitialPotassium());
        }

        //potassium end
        potassiumEnd = findViewById(R.id.potassiumLevelOfTheEndTreatment)
                .findViewById(R.id.tvPatientDataListTitle);
        potassiumEnd.setText(R.string.potassium_end);
        potassiumEndValue = findViewById(R.id.potassiumLevelOfTheEndTreatment)
                .findViewById(R.id.tvPatientDataListValue);
        if (model.getFinalPotassium() != null) {
            potassiumEndValue.setText(model.getFinalPotassium());
        }
        findViewById(R.id.potassiumLevelOfTheEndTreatment)
                .findViewById(R.id.vDivideLine).setVisibility(View.INVISIBLE);
    }

    @SuppressLint("CutPasteId")
    public void setPersonalCardData(){
        TextView sex;
        TextView sexValue;
        TextView weight;
        TextView weightValue;
        TextView growth;
        TextView growthValue;
        TextView dose;
        TextView doseValue;
        TextView patientCardNumber;

        TextView tvPatientCardName = findViewById(R.id.tvPatientCardName);
        tvPatientCardName.setText(model.getName());
        TextView tvPatientCardLastShopping = findViewById(R.id.tvPatientCardLastShopping);

        Long lastPurchases = model.getLastPurchaseAt();
        if (lastPurchases == null) {
            tvPatientCardLastShopping.setText(getString(R.string.without_purchases));
            tvPatientCardLastShopping.setTextColor(Color.parseColor("#FF3347"));
        } else {
            tvPatientCardLastShopping.setText(getString(
                            R.string.last_purchases, dateFormat.format(
                                    new Date(lastPurchases * 1000)
                            ))
            );
            tvPatientCardLastShopping
                    .setTextColor(ContextCompat.getColor(this, R.color.gray));
        }
        TextView tvPatientCardShoppingAmount = findViewById(R.id.tvPatientCardShoppingAmount);
        tvPatientCardShoppingAmount.setText(
                getString(R.string.whole_shopping_items1, model.getPurchasesCount())
        );

        //sex
        sex = findViewById(R.id.patientSex).findViewById(R.id.tvPatientDataListTitle);
        sex.setText(R.string.sex);
        sexValue = findViewById(R.id.patientSex).findViewById(R.id.tvPatientDataListValue);
        if (model.getGender() != null) {
            if (model.getGender().equals("m")) {
                sexValue.setText(R.string.male);
            } else {
                sexValue.setText(R.string.female);
            }
        }

        patientCardNumber = findViewById(R.id.patientCardNumber)
                .findViewById(R.id.tvPatientDataCardTitle);
        patientCardNumber.setText("Номер картки");

        binding.patientCardNumber.tvPatientDataCardValue.setText(model.getCardCode());

        binding.patientCardNumber.ivPatientDataCardIcon.setVisibility(View.GONE);

        //weight
        weight = findViewById(R.id.patientWeight)
                .findViewById(R.id.tvPatientDataListTitle);
        weight.setText(R.string.weight);
        weightValue = findViewById(R.id.patientWeight)
                .findViewById(R.id.tvPatientDataListValue);
        weightValue.setText(String.valueOf(model.getWeight()));

        //growth
        growth = findViewById(R.id.patientGrowth)
                .findViewById(R.id.tvPatientDataListTitle);
        growth.setText(R.string.growth);
        growthValue = findViewById(R.id.patientGrowth)
                .findViewById(R.id.tvPatientDataListValue);
        growthValue.setText(String.valueOf(model.getHeight()));

        //dose
        dose = findViewById(R.id.patientDrugsDose)
                .findViewById(R.id.tvPatientDataListTitle);
        dose.setText(R.string.dose);
        doseValue = findViewById(R.id.patientDrugsDose)
                .findViewById(R.id.tvPatientDataListValue);
        doseValue.setText(String.valueOf(model.getDose()));
        findViewById(R.id.patientDrugsDose)
                .findViewById(R.id.vDivideLine).setVisibility(View.INVISIBLE);
    }

    public void onScanPress(View v) {
        Intent scanIntent = new Intent(this, CardIOActivity.class);

        // customize these values to suit your needs.
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, true);
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, false);
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_POSTAL_CODE, false);

        // MY_SCAN_REQUEST_CODE is arbitrary and is only used within this activity.
        startActivityForResult(scanIntent, SCAN_REQUEST_CODE);
    }

    private void observeViewModel() {
        viewModel.imageResponse.observe(this, responseBody -> {
            Bitmap bitmap = BitmapFactory.decodeStream(responseBody.byteStream());

            Glide.with(this).load(bitmap)
                    .circleCrop()
                    .into(binding.ivMyProfileLogo);
        });
        viewModel.imageState.observe(this, contentState -> {
            if (contentState == ContentState.ERROR) {
                binding.ivMyProfileLogo.setImageResource(R.drawable.user_placeholder);
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
                CreditCard scanResult =
                        data.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT);
                binding.patientCardNumber.tvPatientDataCardValue.setText(scanResult.cardNumber);
            }
        }

        if (requestCode == EDIT_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                setResult(RESULT_OK, new Intent());
                finish();
            }
        }

    }
}