package com.maritech.arterium.ui.patients.add_new_personal;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.maritech.arterium.R;
import com.maritech.arterium.common.ContentState;
import com.maritech.arterium.data.sharePref.Pref;
import com.maritech.arterium.databinding.ActivityAddNewPersonalBinding;
import com.maritech.arterium.ui.barcode.BarcodeActivity;
import com.maritech.arterium.ui.base.BaseActivity;
import com.maritech.arterium.ui.patients.PatientsViewModel;
import com.maritech.arterium.utils.ToastUtil;
import com.nhaarman.supertooltips.ToolTip;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class AddNewPersonalActivity extends BaseActivity<ActivityAddNewPersonalBinding> {

    public static final int SCAN_REQUEST_CODE = 364;

    private Boolean isTwoStep = false;

    ToolTip toolTip = new ToolTip()
            .withText("Використати автозаповнення")
            .withColor(Color.WHITE)
            .withShadow();

    PatientsViewModel viewModel;

    Map<String, RequestBody> map = new HashMap<>();
    MultipartBody.Part image;

    private final SimpleDateFormat dateFormat =
            new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_new_personal;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(PatientsViewModel.class);

        binding.toolbar.tvHint.setVisibility(View.VISIBLE);
        binding.toolbar.tvToolbarTitle.setText("Новий пацієнт");
        binding.toolbar.tvHint.setText("Медичні дані");

        binding.btnNextOne.setOnClickListener(v -> validateFieldsOne());

        binding.btnNextTwo.setOnClickListener(v -> validateFieldsTwo());

        binding.ivAvatar.setOnClickListener(v -> selectImage());

        binding.toolbar.ivLeft.setOnClickListener(v -> autoFill());

        binding.ivCamera.setOnClickListener(v -> {
            int result =
                    ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);

            if (result != PackageManager.PERMISSION_GRANTED) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, 101);
                }
            } else {
                startActivityForResult(
                        new Intent(this, BarcodeActivity.class), SCAN_REQUEST_CODE
                );
            }
        });

        binding.toolbar.ivArrow.setOnClickListener(v -> {
            if (isTwoStep) {
                isTwoStep = false;
                binding.toolbar.viewTwo.setActivated(false);

                binding.clProgressStepOne.setVisibility(View.VISIBLE);
                binding.clProgressStepTwo.setVisibility(View.GONE);

                binding.toolbar.ivLeft.setVisibility(View.GONE);

            } else {
                this.onBackPressed();
            }
            binding.tooltip.setVisibility(View.GONE);

        });

        observeViewModel();
    }

    private void observeViewModel() {
        viewModel.createPatient.observe(this, patientCreateModel -> {
                    setResult(RESULT_OK, new Intent());
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
    }

    private void createPatient() {
        int drugProgramId = loadDrugProgramId();
        map.put("drug_program_id", toRequestBody(String.valueOf(drugProgramId)));
        viewModel.createPatient(map, image);
    }

    private void validateFieldsOne() {
        if (binding.ccInputName.getText() == null ||
                binding.ccInputName.getText().toString().isEmpty()) {
            ToastUtil.show(this, "Введіть ім'я пацієнта");
            return;
        }

        if (binding.ccInputSecondName.getText() == null ||
                binding.ccInputSecondName.toString().isEmpty()) {
            ToastUtil.show(this, "Введіть прізвище пацієнта");
            return;
        } else {
            String lastName = binding.ccInputName.getText() + " " + binding.ccInputSecondName.getText();
            map.put("name", toRequestBody(lastName));
        }

        Editable phone = binding.ccInputPhoneNumber.getText();
        if (phone != null &&
                !phone.toString().isEmpty() &&
                phone.toString().replace(" ", "").length() > 5) {
            map.put("phone", toRequestBody(phone.toString().replace(" ", "")));
        }

        if (binding.ccInputCardNumber.getText() == null ||
                binding.ccInputCardNumber.getText().isEmpty()) {
            ToastUtil.show(this, "Введіть номер картки");
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

        String s = binding.ccChooseDoze.getSelectedValue();
        if (s == null || s.isEmpty()) {
            ToastUtil.show(this, "Виберіть дозу");
            return;
        } else {
            map.put("dose", toRequestBody(binding.ccChooseDoze.getSelectedValue()));
        }

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

        createPatient();
    }

    private int loadDrugProgramId() {
        return Pref.getInstance().getDrugProgramId(this);
    }

    private RequestBody toRequestBody(String value) {
        return RequestBody.create(MediaType.parse("multipart/form-data"), value);
    }

    private void navigateSecondPage() {
        binding.toolbar.viewTwo.setActivated(true);
        isTwoStep = true;

        binding.clProgressStepOne.setVisibility(View.GONE);
        binding.clProgressStepTwo.setVisibility(View.VISIBLE);

        binding.tooltip.showToolTipForView(toolTip, binding.toolbar.ivLeft);
        binding.toolbar.ivLeft.setVisibility(View.VISIBLE);
        binding.tooltip.setVisibility(View.VISIBLE);
    }

    private void selectImage() {
        final CharSequence[] options = {"Сфотографувати", "Вибрати з галереї"};

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        builder.setTitle("Фотографія");
        builder.setItems(options, (dialog, item) -> {
            if (options[item].equals("Сфотографувати")) {

                int result =
                        ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);

                if (result != PackageManager.PERMISSION_GRANTED) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, 102);
                    }
                } else {
                    Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);
                }
            }
            if (options[item].equals("Вибрати з галереї")) {
                Intent pickPhoto = new Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                );
                startActivityForResult(pickPhoto, 1);

            }
        });
        builder.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 101: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startActivityForResult(
                            new Intent(this, BarcodeActivity.class),
                            SCAN_REQUEST_CODE
                    );

                    binding.ccInputCardNumber.setInput("Номер картки", String.valueOf(requestCode));

                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
            }
            case 102: {
                Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePicture, 0);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        binding.ivAvatar.setImageBitmap(selectedImage);

                        persistImage(selectedImage);
                    }

                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        if (selectedImage != null) {
                            Cursor cursor = this.getContentResolver().query(
                                    selectedImage,
                                    filePathColumn,
                                    null,
                                    null,
                                    null
                            );
                            if (cursor != null) {
                                cursor.moveToFirst();

                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                String picturePath = cursor.getString(columnIndex);

                                Bitmap image = BitmapFactory.decodeFile(picturePath);
                                binding.ivAvatar.setImageBitmap(image);
                                cursor.close();
                                persistImage(image);
                            }
                        }

                    }
                    break;
            }
        }
    }

    private void persistImage(Bitmap bitmap) {
        File filesDir = this.getFilesDir();
        File imageFile = new File(filesDir, "avatar.jpg");

        OutputStream os;
        try {
            os = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.flush();
            os.close();

            RequestBody requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), imageFile);

            image = MultipartBody.Part.createFormData(
                    "imagenPerfil", imageFile.getName(), requestFile
            );
        } catch (Exception e) {
            Log.e(this.getClass().getSimpleName(), "Error writing bitmap", e);
        }
    }

    public void autoFill() {
        binding.toolbar.viewTwo.setActivated(true);
        isTwoStep = true;
        binding.ccInputDateInfarct
                .setInput("Дата інфаркту", dateFormat.format(new Date()));
        binding.ccDateOfStartDrug
                .setInput("Дата призначення препарату", dateFormat.format(new Date()));
        binding.ccInputFraction
                .setInput("Фракція викиду", "111");
        binding.ccInputFecesStart
                .setInput("Рівень калію на початку лікування, ммоль/л", "111");
        binding.ccInputFecesEnd
                .setInput("Рівень калію в кінці лікування, ммоль/л", "111");
        binding.ccInputWeight
                .setInput("Вага, кг", "10");
        binding.ccInputHeight
                .setInput("Рiст, см", "10");


        binding.tooltip.setVisibility(View.GONE);
    }

//    private boolean isPhoneValid() {
//        if (binding.ccInputPhoneNumber.getText() != null) {
//            String phone = binding.ccInputPhoneNumber.getText().toString();
//
//            return phone.matches("^\\+380\\d{9}$");
//        } else {
//            return false;
//        }
//    }

}

