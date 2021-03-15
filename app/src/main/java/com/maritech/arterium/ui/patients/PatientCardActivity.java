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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.maritech.arterium.R;
import com.maritech.arterium.common.ContentState;
import com.maritech.arterium.data.models.PatientModel;
import com.maritech.arterium.data.sharePref.Pref;
import com.maritech.arterium.databinding.ActivityPatientCardBinding;
import com.maritech.arterium.ui.base.BaseActivity;
import com.maritech.arterium.ui.patients.add_new_personal.AddNewPersonalActivity;
import com.maritech.arterium.utils.DateTimeUtil;

import java.sql.Date;
import java.text.SimpleDateFormat;
import io.card.payment.CardIOActivity;
import io.card.payment.CreditCard;

import static com.maritech.arterium.ui.patients.add_new_personal.AddNewPersonalActivity.PATIENT_MODEL_KEY;

public class PatientCardActivity extends BaseActivity<ActivityPatientCardBinding> {

    private static final int SCAN_REQUEST_CODE = 660;
    private static final int EDIT_REQUEST_CODE = 680;
    public static final String PATIENT_ID_KEY = "patientId";

    final int PROGRAM_RENIAL = 1;
    final int PROGRAM_GLIPTAR = 2;
    final int PROGRAM_SAGRADA = 4;

    private int programId;
    private int patientId;
    PatientModel model = null;
    PatientsViewModel viewModel;

    private SimpleDateFormat dateFormat;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_patient_card;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(PatientsViewModel.class);

        programId = Pref.getInstance().getDrugProgramId(this);

        if (getIntent() != null) {
            patientId = getIntent().getIntExtra(PATIENT_ID_KEY, -1);
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

        dateFormat =
                new SimpleDateFormat("dd MMMM yyyy", DateTimeUtil.getLocale(this));

        observeViewModel();

        getPatient();
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

        viewModel.patientById.observe(this, patientResponse -> {
            model = patientResponse.getData();

            loadImage();

            setPersonalCardData();

            if (programId == PROGRAM_RENIAL) {
                binding.clPatientMedicalDataRenial.setVisibility(View.VISIBLE);
                setMedicalCardDataRenial();
            }
            if (programId == PROGRAM_GLIPTAR) {
                binding.clPatientMedicalDataGliptar.setVisibility(View.VISIBLE);
                setMedicalCardDataGliptar();
            }
            if (programId == PROGRAM_SAGRADA) {
                binding.clPatientMedicalDataSagrada.setVisibility(View.VISIBLE);
                setMedicalCardDataSagrada();
            }

        });

        viewModel.patientByIdState.observe(this, contentState -> {
            binding.progressBar.setVisibility(
                    contentState == ContentState.LOADING ? View.VISIBLE : View.GONE
            );

            if (contentState == ContentState.CONTENT) {
                binding.content.setVisibility(View.VISIBLE);
            }
        });
    }

    private void getPatient() {
        if (patientId != -1) {
            viewModel.getPatientById(patientId);
        }
    }

    @SuppressLint("CutPasteId")
    private void setMedicalCardDataSagrada() {
        ImageView medicalDataIcon = findViewById(R.id.patientMedicalDataSagrada)
                .findViewById(R.id.ivPatientDataIcon);
        medicalDataIcon.setBackgroundResource(R.drawable.ic_medical_data);
        TextView medicalTitle = findViewById(R.id.patientMedicalDataSagrada)
                .findViewById(R.id.tvPatientData);
        medicalTitle.setText(R.string.medical_data);

        TextView ccInputOptionOks = findViewById(R.id.ccInputOptionOks)
                .findViewById(R.id.tvPatientDataListTitle);
        TextView ccInputOptionOksValue = findViewById(R.id.ccInputOptionOks)
                .findViewById(R.id.tvPatientDataListValue);
        ccInputOptionOks.setText(getString(R.string.option_oks));
        ccInputOptionOksValue.setText(model.getOptionOks());

        TextView ccInputDateOks = findViewById(R.id.ccInputDateOks)
                .findViewById(R.id.tvPatientDataListTitle);
        TextView ccInputDateOksValue = findViewById(R.id.ccInputDateOks)
                .findViewById(R.id.tvPatientDataListValue);
        ccInputDateOks.setText(getString(R.string.date_oks));

        if (model.getDateOks() != null) {
            long millis = model.getHearthAttackDate() * 1000;
            ccInputDateOksValue.setText(dateFormat.format(new Date(millis)));
        } else {
            ccInputDateOksValue.setText("");
        }

        TextView ccInputCoronary = findViewById(R.id.ccInputCoronary)
                .findViewById(R.id.tvPatientDataListTitle);
        TextView ccInputCoronaryValue = findViewById(R.id.ccInputCoronary)
                .findViewById(R.id.tvPatientDataListValue);
        ccInputCoronary.setText(getString(R.string.coronary_angiography));
        ccInputCoronaryValue.setText(model.getCoronaryAngiography());

        TextView ccInputOcclusionZone = findViewById(R.id.ccInputOcclusionZone)
                .findViewById(R.id.tvPatientDataListTitle);
        TextView ccInputOcclusionZoneValue = findViewById(R.id.ccInputOcclusionZone)
                .findViewById(R.id.tvPatientDataListValue);
        ccInputOcclusionZone.setText(getString(R.string.occlusion_zone));
        ccInputOcclusionZoneValue.setText(model.getOcclusionZone());

        TextView ccInputOcclusionDegree = findViewById(R.id.ccInputOcclusionDegree)
                .findViewById(R.id.tvPatientDataListTitle);
        TextView ccInputOcclusionDegreeValue = findViewById(R.id.ccInputOcclusionDegree)
                .findViewById(R.id.tvPatientDataListValue);
        ccInputOcclusionDegree.setText(getString(R.string.occlusion_degree));
        ccInputOcclusionDegreeValue.setText(model.getOcclusionDegree());

        TextView ccInputCoronaryDominanceType = findViewById(R.id.ccInputCoronaryDominanceType)
                .findViewById(R.id.tvPatientDataListTitle);
        TextView ccInputCoronaryDominanceTypeValue = findViewById(R.id.ccInputCoronaryDominanceType)
                .findViewById(R.id.tvPatientDataListValue);
        ccInputCoronaryDominanceType.setText(getString(R.string.occlusion_degree));
        ccInputCoronaryDominanceTypeValue.setText(model.getCoronaryDominanceType());

    }

    @SuppressLint("CutPasteId")
    private void setMedicalCardDataGliptar() {
        ImageView medicalDataIcon = findViewById(R.id.patientMedicalDataGliptar)
                .findViewById(R.id.ivPatientDataIcon);
        medicalDataIcon.setBackgroundResource(R.drawable.ic_medical_data);
        TextView medicalTitle = findViewById(R.id.patientMedicalDataGliptar)
                .findViewById(R.id.tvPatientData);
        medicalTitle.setText(R.string.medical_data);

        TextView ccInputLevelHba1c = findViewById(R.id.ccInputLevelHba1c)
                .findViewById(R.id.tvPatientDataListTitle);
        TextView ccInputLevelHba1cValue = findViewById(R.id.ccInputLevelHba1c)
                .findViewById(R.id.tvPatientDataListValue);
        ccInputLevelHba1c.setText(getString(R.string.level_hba1c));
        ccInputLevelHba1cValue.setText(model.getLevelHba1c());

        TextView ccInputFastingGlycemia = findViewById(R.id.ccInputFastingGlycemia)
                .findViewById(R.id.tvPatientDataListTitle);
        TextView ccInputFastingGlycemiaValue = findViewById(R.id.ccInputFastingGlycemia)
                .findViewById(R.id.tvPatientDataListValue);
        ccInputFastingGlycemia.setText(getString(R.string.fasting_glycemia));
        ccInputFastingGlycemiaValue.setText(model.getFastingGlycemia());

        TextView ccInputPostprandialGlycemia = findViewById(R.id.ccInputPostprandialGlycemia)
                .findViewById(R.id.tvPatientDataListTitle);
        TextView ccInputPostprandialGlycemiaValue = findViewById(R.id.ccInputPostprandialGlycemia)
                .findViewById(R.id.tvPatientDataListValue);
        ccInputPostprandialGlycemia.setText(getString(R.string.postprandial_glycemia));
        ccInputPostprandialGlycemiaValue.setText(model.getPostprandialGlycemia());

        TextView ccInputIndexHomaIr = findViewById(R.id.ccInputIndexHomaIr)
                .findViewById(R.id.tvPatientDataListTitle);
        TextView ccInputIndexHomaIrValue = findViewById(R.id.ccInputIndexHomaIr)
                .findViewById(R.id.tvPatientDataListValue);
        ccInputIndexHomaIr.setText(getString(R.string.index_homa_ir));
        ccInputIndexHomaIrValue.setText(model.getIndexHomaIr());

        TextView ccInputSad = findViewById(R.id.ccInputSad)
                .findViewById(R.id.tvPatientDataListTitle);
        TextView ccInputSadValue = findViewById(R.id.ccInputSad)
                .findViewById(R.id.tvPatientDataListValue);
        ccInputSad.setText(getString(R.string.sad));
        ccInputSadValue.setText(model.getSad());

        TextView ccInputDad = findViewById(R.id.ccInputDad)
                .findViewById(R.id.tvPatientDataListTitle);
        TextView ccInputDadValue = findViewById(R.id.ccInputDad)
                .findViewById(R.id.tvPatientDataListValue);
        ccInputDad.setText(getString(R.string.dad));
        ccInputDadValue.setText(model.getDad());

        TextView ccInputImt = findViewById(R.id.ccInputImt)
                .findViewById(R.id.tvPatientDataListTitle);
        TextView ccInputImtValue = findViewById(R.id.ccInputImt)
                .findViewById(R.id.tvPatientDataListValue);
        ccInputImt.setText(getString(R.string.imt));
        ccInputImtValue.setText(model.getImt());
    }

    @SuppressLint("CutPasteId")
    public void setMedicalCardDataRenial(){
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
        TextView dose;
        TextView doseValue;

        //medical data
        ImageView medicalDataIcon = findViewById(R.id.patientMedicalData)
                .findViewById(R.id.ivPatientDataIcon);
        medicalDataIcon.setBackgroundResource(R.drawable.ic_medical_data);
        TextView medicalTitle = findViewById(R.id.patientMedicalData)
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

    @SuppressLint("CutPasteId")
    public void setPersonalCardData(){
        TextView sex;
        TextView sexValue;
        TextView weight;
        TextView weightValue;
        TextView growth;
        TextView growthValue;
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

        sex = findViewById(R.id.patientSex).findViewById(R.id.tvPatientDataListTitle);
        sex.setText(R.string.gender);
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
        patientCardNumber.setText(getString(R.string.card_number));

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