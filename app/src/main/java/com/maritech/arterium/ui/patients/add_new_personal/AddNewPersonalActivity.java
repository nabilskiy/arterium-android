package com.maritech.arterium.ui.patients.add_new_personal;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.github.drjacky.imagepicker.ImagePicker;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.maritech.arterium.R;
import com.maritech.arterium.common.ContentState;
import com.maritech.arterium.data.models.PatientModel;
import com.maritech.arterium.data.sharePref.Pref;
import com.maritech.arterium.databinding.ActivityAddNewPersonalBinding;
import com.maritech.arterium.ui.barcode.BarcodeActivity;
import com.maritech.arterium.ui.base.BaseActivity;
import com.maritech.arterium.ui.patients.PatientsViewModel;
import com.maritech.arterium.utils.ToastUtil;
import com.nhaarman.supertooltips.ToolTip;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class AddNewPersonalActivity extends BaseActivity<ActivityAddNewPersonalBinding> {

    public static final int PATIENT_REQUEST_CODE = 500;

    public static final int CAMERA_PERMISSION_REQUEST_CODE = 101;
    public static final int CARD_PERMISSION_REQUEST_CODE = 102;
    public static final int SCAN_REQUEST_CODE = 364;
    public static final int CAMERA_REQUEST_CODE = 500;
    public static final int GALLERY_REQUEST_CODE = 505;
    public static final String PATIENT_MODEL_KEY = "patientModelKey";

    public static final String EDIT_EXTRA_KEY = "editModeKey";
    private boolean isEditMode;

    private Boolean isTwoStep = false;

    private PatientsViewModel viewModel;
    private PatientModel model;

    private Map<String, RequestBody> map = new HashMap<>();
    private MultipartBody.Part image;

    private final SimpleDateFormat dateFormat =
            new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    ToolTip toolTip;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_new_personal;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(PatientsViewModel.class);

        isEditMode = getIntent().getBooleanExtra(EDIT_EXTRA_KEY, false);

        binding.toolbar.tvHint.setVisibility(View.VISIBLE);
        binding.toolbar.tvHint.setText(getString(R.string.medical_data));

        binding.toolbar.viewOne.setActivated(true);

        binding.btnNextOne.setOnClickListener(v -> {
            validateFieldsOne();
        });

        binding.btnNextTwo.setOnClickListener(v -> validateFieldsTwo());

        binding.ivAvatar.setOnClickListener(v -> selectImage());

        binding.toolbar.ivLeft.setOnClickListener(v -> autoFill());

        binding.ivCamera.setOnClickListener(v -> {
            if (arePermissionsGranted(new String[]{Manifest.permission.CAMERA})) {
                startActivityForResult(
                        new Intent(this, BarcodeActivity.class), SCAN_REQUEST_CODE
                );
            } else {
                requestPermissionsCompat(
                        new String[]{Manifest.permission.CAMERA}, CARD_PERMISSION_REQUEST_CODE
                );
            }
        });

        binding.toolbar.ivArrow.setOnClickListener(v -> {
            if (isTwoStep) {
                isTwoStep = false;
                binding.toolbar.viewTwo.setActivated(false);
                binding.toolbar.viewOne.setActivated(true);

                binding.clProgressStepOne.setVisibility(View.VISIBLE);
                binding.clProgressStepTwo.setVisibility(View.GONE);

                binding.toolbar.ivLeft.setVisibility(View.GONE);

                map = new HashMap<>();
            } else {
                this.onBackPressed();
            }
            binding.tooltip.setVisibility(View.GONE);

        });

        binding.ccChooseDoze.findViewById(R.id.tvOne).setActivated(true);
        binding.ccChooseDoze.findViewById(R.id.tvOne).setOnClickListener(v -> {
            binding.ccChooseDoze.findViewById(R.id.tvOne).setActivated(true);
            binding.ccChooseDoze.findViewById(R.id.tvTwo).setActivated(false);
        });
        binding.ccChooseDoze.findViewById(R.id.tvTwo).setOnClickListener(v -> {
            binding.ccChooseDoze.findViewById(R.id.tvOne).setActivated(false);
            binding.ccChooseDoze.findViewById(R.id.tvTwo).setActivated(true);
        });

        toolTip = new ToolTip()
                .withText(getString(R.string.auto_fill_patient))
                .withColor(Color.WHITE)
                .withShadow();

        if (isEditMode) {
            if (getIntent() != null) {
                model = getIntent().getParcelableExtra(PATIENT_MODEL_KEY);
            }
            binding.toolbar.tvToolbarTitle.setText(getString(R.string.edit));

            fillPatientData();
        } else {
            binding.toolbar.tvToolbarTitle.setText(getString(R.string.new_patient));
        }

        observeViewModel();
    }

    private void observeViewModel() {
        viewModel.createPatient.observe(this, patientCreateModel -> {
                    ToastUtil.show(this, getString(R.string.success_saved));

                    setResult(RESULT_OK, new Intent());

                    hideProgressDialog();
                    finish();
                }
        );

        viewModel.createPatientState.observe(this, contentState -> {
            if (contentState == ContentState.LOADING) {
                showProgressDialog();
            } else {
                hideProgressDialog();
            }
        });

        viewModel.createErrorMessage.observe(
                this,
                error -> ToastUtil.show(this, error)
        );

        viewModel.imageResponse.observe(this, responseBody -> {
            Bitmap bitmap = BitmapFactory.decodeStream(responseBody.byteStream());

            Glide.with(this).load(bitmap)
                    .circleCrop()
                    .into(binding.ivAvatar);
        });

        viewModel.imageState.observe(this, contentState -> {
            if (contentState == ContentState.ERROR) {
                Glide.with(this).load(R.drawable.user_placeholder)
                        .circleCrop()
                        .into(binding.ivAvatar);
            }
        });

        viewModel.deleteImageResponse.observe(this, responseBody -> {
            if (responseBody != null && !responseBody.getHasImg()) {
                model.setHasImg(false);
            }
        });

        viewModel.deleteImageState.observe(this, contentState -> {
            if (contentState == ContentState.ERROR) {
                model.setHasImg(true);
                loadImage();
            }
        });
    }

    private void createPatient() {
        int drugProgramId = loadDrugProgramId();
        map.put("drug_program_id", toRequestBody(String.valueOf(drugProgramId)));
        viewModel.createPatient(map, image);
    }

    private void editPatient() {
        int drugProgramId = loadDrugProgramId();
        map.put("drug_program_id", toRequestBody(String.valueOf(drugProgramId)));
        viewModel.editPatient(model.getId(), map, image);
    }

    private void loadImage() {
        viewModel.getPatientImage(model.getId());
    }

    private void deleteImage() {
        viewModel.deletePatientImage(model.getId());

        Glide.with(this).load(R.drawable.user_placeholder)
                .circleCrop()
                .into(binding.ivAvatar);
    }

    private void validateFieldsOne() {
        if (binding.ccInputName.getText() == null ||
                binding.ccInputName.getText().toString().isEmpty()) {
            ToastUtil.show(this, getString(R.string.enter_full_name));
            return;
        } else {
            String name = binding.ccInputName.getText().toString();

            map.put("name", toRequestBody(name));
        }

        if (binding.ccInputPhoneNumber.getText() != null) {
            String phone = binding.ccInputPhoneNumber
                    .getText().toString().replace(" ", "");

            if (phone.length() == 13) {
                map.put("phone", toRequestBody(phone));
            }
        }

        if (binding.ccInputCardNumber.getText() == null ||
                binding.ccInputCardNumber.getText().isEmpty()) {
            ToastUtil.show(this, getString(R.string.enter_card_number));
            return;
        } else {
            map.put("card_code", toRequestBody(binding.ccInputCardNumber.getText()));
        }

        int selectedId = binding.radioGroup.getCheckedRadioButtonId();

        if (selectedId == R.id.radio_male) {
            map.put("gender", toRequestBody("m"));
        } else {
            map.put("gender", toRequestBody("f"));
        }

        boolean isFirstActivate = binding.ccChooseDoze.findViewById(R.id.tvOne).isActivated();
        String dose = isFirstActivate ? "25" : "50";

        map.put("dose", toRequestBody(dose));

        navigateSecondPage();
    }

    private void validateFieldsTwo() {
        if (binding.ccInputHeight.getText() != null &&
                !binding.ccInputHeight.getText().isEmpty()) {
            map.put("height", toRequestBody(binding.ccInputHeight.getText()));
        }

        if (binding.ccInputWeight.getText() != null &&
                !binding.ccInputWeight.getText().isEmpty()) {
            map.put("weight", toRequestBody(binding.ccInputWeight.getText()));
        }

        if (binding.ccInputDateInfarct.getText() != null &&
                !binding.ccInputDateInfarct.getText().isEmpty()) {
            map.put("hearth_attack_date", toRequestBody(binding.ccInputDateInfarct.getText()));
        }

        if (binding.ccDateOfStartDrug.getText() != null &&
                !binding.ccDateOfStartDrug.getText().isEmpty()) {
            map.put("prescribing_date", toRequestBody(binding.ccDateOfStartDrug.getText()));
        }

        if (binding.ccInputFraction.getText() != null &&
                !binding.ccInputFraction.getText().isEmpty()) {
            map.put("ejection_fraction", toRequestBody(binding.ccInputFraction.getText()));
        }

        if (binding.ccInputFecesStart.getText() != null &&
                !binding.ccInputFecesStart.getText().isEmpty()) {
            map.put("initial_potassium", toRequestBody(binding.ccInputFecesStart.getText()));
        }

        if (binding.ccInputFecesEnd.getText() != null &&
                !binding.ccInputFecesEnd.getText().isEmpty()) {
            map.put("final_potassium", toRequestBody(binding.ccInputFecesEnd.getText()));
        }

        if (isEditMode) {
            editPatient();
        } else {
            createPatient();
        }
    }

    private int loadDrugProgramId() {
        return Pref.getInstance().getDrugProgramId(this);
    }

    private RequestBody toRequestBody(String value) {
        return RequestBody.create(MediaType.parse("multipart/form-data"), value);
    }

    private void navigateSecondPage() {
        binding.toolbar.viewTwo.setActivated(true);
        binding.toolbar.viewOne.setActivated(false);
        isTwoStep = true;

        binding.clProgressStepOne.setVisibility(View.GONE);
        binding.clProgressStepTwo.setVisibility(View.VISIBLE);

        binding.tooltip.showToolTipForView(toolTip, binding.toolbar.ivLeft);
        binding.toolbar.ivLeft.setVisibility(View.VISIBLE);
        binding.tooltip.setVisibility(View.VISIBLE);
    }

    private void selectImage() {
        final CharSequence[] options;

        if (isEditMode && model.getHasImg()) {
            options = new CharSequence[]{
                    getString(R.string.pick_photo),
                    getString(R.string.pick_gallery),
                    getString(R.string.delete_image)
            };
        } else {
            options = new CharSequence[]{
                    getString(R.string.pick_photo),
                    getString(R.string.pick_gallery)};
        }

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        builder.setTitle(getString(R.string.photo_title));
        builder.setItems(options, (dialog, item) -> {
            if (options[item].equals(getString(R.string.pick_photo))) {
                openCamera();
            }
            if (options[item].equals(getString(R.string.pick_gallery))) {
                openGallery();
            }
            if (options[item].equals(getString(R.string.delete_image))) {
                deleteImage();
            }
        });
        builder.show();
    }

    private void openCamera() {
        if (arePermissionsGranted(new String[]{Manifest.permission.CAMERA})) {
            ImagePicker.Companion.with(this)
                    .cameraOnly()
                    .start(CAMERA_REQUEST_CODE);
        } else {
            requestPermissionsCompat(
                    new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE
            );
        }
    }

    private void openGallery() {
        ImagePicker.Companion.with(this)
                .compress(1024)
                .galleryOnly()
                .galleryMimeTypes(new String[]{
                        "image/png",
                        "image/jpg",
                        "image/jpeg"
                })
                .maxResultSize(1080, 1920)
                .start(GALLERY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_CANCELED) {
            if (data != null) {
                if (requestCode == CAMERA_REQUEST_CODE || requestCode == GALLERY_REQUEST_CODE) {
                    File file = ImagePicker.Companion.getFile(data);

                    if (file != null)
                        persistImage(file);
                }

                if (requestCode == SCAN_REQUEST_CODE) {
                    binding.ccInputCardNumber.setInput(
                            getString(R.string.card_number), data.getStringExtra("result")
                    );
                }
            }
        }
    }

    private void persistImage(File imageFile) {
        Glide.with(this).load(imageFile)
                .circleCrop()
                .into(binding.ivAvatar);

        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), imageFile);

        image = MultipartBody.Part.createFormData(
                "img", imageFile.getName(), requestFile
        );
    }

    private void fillPatientData() {
        if (model != null) {
            binding.ccInputName.setText(model.getName());
            String phone = model.getPhone()
                    .replace(getString(R.string.country_code), "");

            binding.ccInputPhoneNumber.setMaskedText(phone);
            binding.ccInputCardNumber.setInput(
                    getString(R.string.card_number), model.getCardCode()
            );

            if (!model.getGender().isEmpty() &&
                    model.getGender().equalsIgnoreCase("m")) {
                binding.radioGroup.check(R.id.radio_male);
            } else {
                binding.radioGroup.check(R.id.radio_female);
            }

            if (!model.getDose().isEmpty() &&
                    model.getDose().equalsIgnoreCase("50")) {

                binding.ccChooseDoze.findViewById(R.id.tvTwo).setActivated(true);
                binding.ccChooseDoze.findViewById(R.id.tvOne).setActivated(false);
            } else {
                binding.ccChooseDoze.findViewById(R.id.tvTwo).setActivated(false);
                binding.ccChooseDoze.findViewById(R.id.tvOne).setActivated(true);
            }

            if (model.getHearthAttackDate() != null) {
                long millis = model.getHearthAttackDate() * 1000;
                binding.ccInputDateInfarct.setInput(
                        getString(R.string.heart_attack), dateFormat.format(new Date(millis))
                );
            }

            if (model.getHearthAttackDate() != null) {
                long millis = model.getPrescribingDate() * 1000;
                binding.ccDateOfStartDrug.setInput(
                        getString(R.string.drug_administration), dateFormat.format(new Date(millis))
                );
            }

            binding.ccInputFraction.setInput(
                    getString(R.string.ejection_fraction), model.getEjectionFraction()
            );
            binding.ccInputFecesStart.setInput(
                    getString(R.string.potassium_start), model.getInitialPotassium()
            );
            binding.ccInputFecesEnd.setInput(
                    getString(R.string.potassium_end), model.getFinalPotassium()
            );
            binding.ccInputWeight.setInput(
                    getString(R.string.weight), String.valueOf(model.getWeight())
            );
            binding.ccInputHeight.setInput(
                    getString(R.string.growth), String.valueOf(model.getHeight())
            );

            loadImage();
        }
    }

    public void autoFill() {
        binding.toolbar.viewTwo.setActivated(true);

        isTwoStep = true;

        binding.ccInputDateInfarct
                .setInput(getString(R.string.heart_attack), dateFormat.format(new Date()));
        binding.ccDateOfStartDrug
                .setInput(getString(R.string.drug_administration), dateFormat.format(new Date()));
        binding.ccInputFraction.setInput(
                getString(R.string.ejection_fraction), getString(R.string.ejection_fraction_value)
        );
        binding.ccInputFecesStart.setInput(
                getString(R.string.potassium_start), getString(R.string.potassium_start_value)
        );
        binding.ccInputFecesEnd.setInput(
                        getString(R.string.potassium_end), getString(R.string.potassium_end_value)
        );
        binding.ccInputWeight.setInput(
                getString(R.string.weight), getString(R.string.weight_value)
        );
        binding.ccInputHeight.setInput(
                getString(R.string.growth), getString(R.string.growth_value)
        );


        binding.tooltip.setVisibility(View.GONE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case CARD_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startActivityForResult(
                            new Intent(this, BarcodeActivity.class),
                            SCAN_REQUEST_CODE
                    );
                } else {
                    Toast.makeText(
                            this, getString(R.string.permission_denied), Toast.LENGTH_SHORT
                    ).show();
                }
            }
            case CAMERA_PERMISSION_REQUEST_CODE: {
                openCamera();
            }
        }
    }

    private boolean arePermissionsGranted(String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED)
                return false;
        }
        return true;
    }

    private void requestPermissionsCompat(String[] permissions, int requestCode) {
        ActivityCompat.requestPermissions(this, permissions, requestCode);
    }

}

