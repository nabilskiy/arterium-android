package com.maritech.arterium.ui.patient_card;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.maritech.arterium.R;

public class PatientCardFragment extends Fragment {
    TextView toolbarTitle;
    ImageView ivArrow;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_patient_card, container, false);

        toolbarTitle = root.findViewById(R.id.patientCardToolbar).findViewById(R.id.tvToolbarTitle);
        toolbarTitle.setText(R.string.patient_card);
        ivArrow = root.findViewById(R.id.patientCardToolbar).findViewById(R.id.ivArrow);
        ivArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().onBackPressed();
            }
        });

        setPersonalCardData(root);
        setMedicalCardData(root);
        requireActivity().findViewById(R.id.nav_view).setVisibility(View.GONE);
        return root;
    }

    public void setMedicalCardData(View root){
        ImageView medicalDataIcon;
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
        heartAttackValue.setText(R.string.heart_attack_value);

        //drug
        drugAdministration = root.findViewById(R.id.dateOfDrugAdministration).findViewById(R.id.tvPatientDataListTitle);
        drugAdministration.setText(R.string.drug_administration);
        drugAdministrationValue = root.findViewById(R.id.dateOfDrugAdministration).findViewById(R.id.tvPatientDataListValue);
        drugAdministrationValue.setText(R.string.drug_administration_value);

        //ejection
        ejectionFraction = root.findViewById(R.id.ejectionFraction).findViewById(R.id.tvPatientDataListTitle);
        ejectionFraction.setText(R.string.ejection_fraction);
        ejectionFractionValue = root.findViewById(R.id.ejectionFraction).findViewById(R.id.tvPatientDataListValue);
        ejectionFractionValue.setText(R.string.ejection_fraction_value);

        //potassium start
        potassiumStart = root.findViewById(R.id.potassiumLevelOfStartTreatment).findViewById(R.id.tvPatientDataListTitle);
        potassiumStart.setText(R.string.potassium_start);
        potassiumStartValue = root.findViewById(R.id.potassiumLevelOfStartTreatment).findViewById(R.id.tvPatientDataListValue);
        potassiumStartValue.setText(R.string.potassium_start_value);

        //potassium end
        potassiumEnd = root.findViewById(R.id.potassiumLevelOfTheEndTreatment).findViewById(R.id.tvPatientDataListTitle);
        potassiumEnd.setText(R.string.potassium_end);
        potassiumEndValue = root.findViewById(R.id.potassiumLevelOfTheEndTreatment).findViewById(R.id.tvPatientDataListValue);
        potassiumEndValue.setText(R.string.potassium_end_value);
        root.findViewById(R.id.potassiumLevelOfTheEndTreatment).findViewById(R.id.vDivideLine).setVisibility(View.INVISIBLE);
    }

    public void setPersonalCardData(View root){
        TextView sex;
        TextView sexValue;
        TextView weight;
        TextView weightValue;
        TextView growth;
        TextView growthValue;
        TextView dose;
        TextView doseValue;

        //sex
        sex = root.findViewById(R.id.patientSex).findViewById(R.id.tvPatientDataListTitle);
        sex.setText(R.string.sex);
        sexValue = root.findViewById(R.id.patientSex).findViewById(R.id.tvPatientDataListValue);
        sexValue.setText(R.string.men);

        //weight
        weight = root.findViewById(R.id.patientWeight).findViewById(R.id.tvPatientDataListTitle);
        weight.setText(R.string.weight);
        weightValue = root.findViewById(R.id.patientWeight).findViewById(R.id.tvPatientDataListValue);
        weightValue.setText(R.string.weight_value);

        //growth
        growth = root.findViewById(R.id.patientGrowth).findViewById(R.id.tvPatientDataListTitle);
        growth.setText(R.string.growth);
        growthValue = root.findViewById(R.id.patientGrowth).findViewById(R.id.tvPatientDataListValue);
        growthValue.setText(R.string.growth_value);

        //dose
        dose = root.findViewById(R.id.patientDrugsDose).findViewById(R.id.tvPatientDataListTitle);
        dose.setText(R.string.dose);
        doseValue = root.findViewById(R.id.patientDrugsDose).findViewById(R.id.tvPatientDataListValue);
        doseValue.setText(R.string.dose_value);
        root.findViewById(R.id.patientDrugsDose).findViewById(R.id.vDivideLine).setVisibility(View.INVISIBLE);
    }
}