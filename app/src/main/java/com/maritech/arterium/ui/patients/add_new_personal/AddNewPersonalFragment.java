package com.maritech.arterium.ui.patients.add_new_personal;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import com.maritech.arterium.R;
import com.maritech.arterium.common.ContentState;
import com.maritech.arterium.data.sharePref.Pref;
import com.maritech.arterium.databinding.FragmentAddNewPersonalBinding;
import com.maritech.arterium.ui.barcode.BarcodeActivity;
import com.maritech.arterium.ui.base.BaseFragment;
import com.maritech.arterium.ui.patients.PatientsViewModel;
import com.maritech.arterium.ui.widgets.CustomInput;
import com.maritech.arterium.ui.widgets.CustomTabComponent;
import com.maritech.arterium.utils.ToastUtil;
import com.nhaarman.supertooltips.ToolTip;
import com.nhaarman.supertooltips.ToolTipRelativeLayout;
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
import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class AddNewPersonalFragment extends BaseFragment{

    public static final int SCAN_REQUEST_CODE = 364;
    private static final String TAG = "scan_card";

    private TextView tvTabOne;
    private TextView tvTabTwo;

    private View viewProgressOne;
    private View viewProgressTwo;
    private TextView btnNextOne;
    private TextView btnNextTwo;
    private Boolean isTwoStep = false;
    private ImageView btnBack;
    private ImageView btnAuto;

    private TextView tvToolbarTitle;
    private TextView tvHint;
    private TextView tvToolTip;

    private ConstraintLayout clProgressStepOne;
    private ConstraintLayout clProgressStepTwo;
    private ConstraintLayout clInfoUser;

    private ImageView ivAvatar;
    private CustomInput ccInputName;
    private CustomInput ccInputSecondName;
    private CustomInput ccInputPhoneNumber;
    private CustomInput ccInputCardNumber;
    private RadioGroup radioGroup;
    private CustomTabComponent ccChooseDoze;

    private CustomInput ccInputDateInfarct;
    private CustomInput ccDateOfStartDrug;
    private CustomInput ccInputFraction;
    private CustomInput ccInputFecesStart;
    private CustomInput ccInputFecesEnd;
    private CustomInput ccInputWeight;
    private CustomInput ccInputHeight;

    private ImageView ivCamera;

    ToolTipRelativeLayout vTooltip;

    ToolTip toolTip = new ToolTip()
            .withText("Використати автозаповнення")
            .withColor(Color.WHITE)
            .withShadow();

    AddNewPersonalNavigator navigator = new AddNewPersonalNavigator();

    PatientsViewModel viewModel = new PatientsViewModel();
    FragmentAddNewPersonalBinding binding;

    Map<String, RequestBody> map = new HashMap<>();
    MultipartBody.Part image;

    private final SimpleDateFormat dateFormat =
            new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    @Override
    protected int getContentView() {
        return R.layout.fragment_add_new_personal;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, getContentView(), container, false);

        View root = binding.getRoot();

        clProgressStepOne = root.findViewById(R.id.clProgressStepOne);
        clProgressStepTwo = root.findViewById(R.id.clProgressStepTwo);
        tvToolbarTitle = root.findViewById(R.id.toolbar).findViewById(R.id.tvToolbarTitle);
        tvHint = root.findViewById(R.id.toolbar).findViewById(R.id.tvHint);
        tvHint.setVisibility(View.VISIBLE);
        radioGroup = root.findViewById(R.id.radioGroup);
        btnNextOne = root.findViewById(R.id.btnNextOne);
        btnNextTwo = root.findViewById(R.id.btnNextTwo);
        viewProgressOne = root.findViewById(R.id.toolbar).findViewById(R.id.viewOne);
        viewProgressOne.setActivated(true);
        viewProgressTwo = root.findViewById(R.id.toolbar).findViewById(R.id.viewTwo);
        btnBack = root.findViewById(R.id.toolbar).findViewById(R.id.ivRight);
        btnAuto = root.findViewById(R.id.toolbar).findViewById(R.id.ivLeft);
        ivCamera = root.findViewById(R.id.ivCamera);
        ccInputCardNumber = root.findViewById(R.id.ccInputCardNumber);
        tvToolTip = root.findViewById(R.id.tvToolTip);
        vTooltip = root.findViewById(R.id.tooltip);

        ivAvatar = root.findViewById(R.id.ivAvatar);
        ccInputName = root.findViewById(R.id.ccInputName);
        ccInputSecondName = root.findViewById(R.id.ccInputSecondName);
        ccInputPhoneNumber = root.findViewById(R.id.ccInputPhoneNumber);
        ccInputCardNumber = root.findViewById(R.id.ccInputCardNumber);
        radioGroup = root.findViewById(R.id.radioGroup);
        ccChooseDoze = root.findViewById(R.id.ccChooseDoze);

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

        btnNextOne.setOnClickListener(v -> {
            validateFieldsOne();
        });

        btnNextTwo.setOnClickListener(v -> {
            validateFieldsTwo();
        });

        ivAvatar.setOnClickListener(v -> selectImage());

        btnAuto.setOnClickListener(v -> autoFill());

        ivCamera.setOnClickListener(v -> {
            int result =
                    ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA);

            if(result != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, 101);
            } else {
                startActivityForResult(
                        new Intent(getActivity(), BarcodeActivity.class), SCAN_REQUEST_CODE
                );
            }
        });

        btnBack.setOnClickListener(v -> {
            if (isTwoStep) {
                isTwoStep = false;
                viewProgressTwo.setActivated(false);

                clProgressStepOne.setVisibility(View.VISIBLE);
                clProgressStepTwo.setVisibility(View.GONE);

                btnAuto.setVisibility(View.GONE);

            } else {
                requireActivity().onBackPressed();
            }
            vTooltip.setVisibility(View.GONE);

        });
//        Log.e("Bottom", this.getClass().getName());
//        baseActivity.findViewById(R.id.bottom_nav_view).setVisibility(View.GONE);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        observeViewModel();
    }

    private void observeViewModel() {
        viewModel.createPatient.observe(getViewLifecycleOwner(), patientCreateModel ->
                navigator.goToDashboard(navController));

        viewModel.createPatientState.observe(getViewLifecycleOwner(), contentState -> {
            if (contentState == ContentState.LOADING) {
                showProgressDialog();
            } else {
                hideProgressDialog();
            }
        });

        viewModel.createErrorMessage.observe(
                getViewLifecycleOwner(),
                error -> ToastUtil.show(requireContext(), error)
        );
    }

    private void createPatient() {
        int drugProgramId = loadDrugProgramId();
        map.put("drug_program_id", toRequestBody(String.valueOf(drugProgramId)));
        viewModel.createPatient(map, image);
    }

    private void validateFieldsOne() {
        if (ccInputName.getText() == null || ccInputName.getText().isEmpty()) {
            ToastUtil.show(requireContext(), "Введіть ім'я пацієнта");
            return;
        }

        if (ccInputSecondName.getText() == null || ccInputSecondName.getText().isEmpty()) {
            ToastUtil.show(requireContext(), "Введіть прізвище пацієнта");
            return;
        } else {
            String lastName = ccInputName.getText() + " " + ccInputSecondName.getText();
            map.put("name", toRequestBody(lastName));
        }

        if (ccInputPhoneNumber.getText() == null || ccInputPhoneNumber.getText().isEmpty()) {
            ToastUtil.show(requireContext(), "Введіть номер телефону");
            return;
        } else {
            map.put("phone", toRequestBody(ccInputPhoneNumber.getText()));
        }

        if (ccInputCardNumber.getText() == null || ccInputCardNumber.getText().isEmpty()) {
            ToastUtil.show(requireContext(), "Введіть номер картки");
            return;
        } else {
            map.put("card_code", toRequestBody(ccInputCardNumber.getText()));
        }

        int selectedId = radioGroup.getCheckedRadioButtonId();

        if (selectedId == R.id.radio_male) {
            map.put("gender", toRequestBody("m"));
        } else {
            map.put("gender", toRequestBody("f"));
        }

        if (ccChooseDoze.getSelectedValue() == null || ccChooseDoze.getSelectedValue().isEmpty()) {
            ToastUtil.show(requireContext(), "Виберіть дозу");
            return;
        } else {
            map.put("dose", toRequestBody(ccChooseDoze.getSelectedValue()));
        }

        navigateSecondPage();
    }

    private void validateFieldsTwo() {
        if (ccInputHeight.getText() != null && !ccInputHeight.getText().isEmpty()) {
            map.put("height", toRequestBody(ccInputHeight.getText()));
        }

        if (ccInputWeight.getText() != null && !ccInputWeight.getText().isEmpty()) {
            map.put("weight", toRequestBody(ccInputWeight.getText()));
        }

        if (ccInputDateInfarct.getText() != null && !ccInputDateInfarct.getText().isEmpty()) {
            map.put("hearth_attack_date", toRequestBody(ccInputDateInfarct.getText()));
        }

        if (ccDateOfStartDrug.getText() != null && !ccDateOfStartDrug.getText().isEmpty()) {
            map.put("prescribing_date", toRequestBody(ccDateOfStartDrug.getText()));
        }

        if (ccInputFraction.getText() != null && !ccInputFraction.getText().isEmpty()) {
            map.put("ejection_fraction", toRequestBody(ccInputFraction.getText()));
        }

        if (ccInputFecesStart.getText() != null && !ccInputFecesStart.getText().isEmpty()) {
            map.put("initial_potassium", toRequestBody(ccInputFecesStart.getText()));
        }

        if (ccInputFecesEnd.getText() != null && !ccInputFecesEnd.getText().isEmpty()) {
            map.put("final_potassium", toRequestBody(ccInputFecesEnd.getText()));
        }

        createPatient();
    }

    private int loadDrugProgramId() {
        return Pref.getInstance().getDrugProgramId(requireContext());
    }

    private RequestBody toRequestBody(String value) {
        return RequestBody.create(MediaType.parse("multipart/form-data"), value);
    }

    private void navigateSecondPage() {
        viewProgressTwo.setActivated(true);
        isTwoStep = true;

        clProgressStepOne.setVisibility(View.GONE);
        clProgressStepTwo.setVisibility(View.VISIBLE);

        vTooltip.showToolTipForView(toolTip, btnAuto);
        btnAuto.setVisibility(View.VISIBLE);
        vTooltip.setVisibility(View.VISIBLE);
    }

    private void selectImage() {
        final CharSequence[] options = { "Сфотографувати", "Вибрати з галереї" };

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Фотографія");

        builder.setItems(options, (dialog, item) -> {
            if (options[item].equals("Сфотографувати")) {

                int result =
                        ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA);

                if(result != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, 102);
                } else {
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);
                }
            }
            if (options[item].equals("Вибрати з галереї")) {
                Intent pickPhoto = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                );
                startActivityForResult(pickPhoto , 1);

            }
        });
        builder.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NotNull String[] permissions,
                                           @NotNull int[] grantResults) {
        switch (requestCode) {
            case 101: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startActivityForResult(new Intent(getActivity(), BarcodeActivity.class), SCAN_REQUEST_CODE);
                    ccInputCardNumber.setInput("Номер картки", String.valueOf(requestCode));
                } else {
                    Toast.makeText(getContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
            }
            case 102: {
                Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePicture, 0);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        ivAvatar.setImageBitmap(selectedImage);

                        persistImage(selectedImage);
                    }

                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage =  data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        if (selectedImage != null) {
                            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                                    filePathColumn, null, null, null);
                            if (cursor != null) {
                                cursor.moveToFirst();

                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                String picturePath = cursor.getString(columnIndex);

                                Bitmap image = BitmapFactory.decodeFile(picturePath);
                                ivAvatar.setImageBitmap(image);
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
        File filesDir = requireContext().getFilesDir();
        File imageFile = new File(filesDir, "avatar.jpg");

        OutputStream os;
        try {
            os = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.flush();
            os.close();

            RequestBody requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), imageFile);
            // MultipartBody.Part is used to send also the actual file name

            image = MultipartBody.Part.createFormData("imagenPerfil", imageFile.getName(), requestFile);
        } catch (Exception e) {
            Log.e(this.getClass().getSimpleName(), "Error writing bitmap", e);
        }
    }

    public void autoFill(){
        viewProgressTwo.setActivated(true);
        isTwoStep = true;
        ccInputDateInfarct.setInput("Дата інфаркту", dateFormat.format(new Date()));
        ccDateOfStartDrug.setInput("Дата призначення препарату", dateFormat.format(new Date()));
        ccInputFraction.setInput("Фракція викиду", "111");
        ccInputFecesStart.setInput("Рівень калію на початку лікування, ммоль/л", "111");
        ccInputFecesEnd.setInput("Рівень калію в кінці лікування, ммоль/л", "111");
        ccInputWeight.setInput("Вага, кг", "10");
        ccInputHeight.setInput("Рiст, см", "10");


        vTooltip.setVisibility(View.GONE);
    }

}

