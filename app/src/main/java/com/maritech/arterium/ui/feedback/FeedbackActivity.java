package com.maritech.arterium.ui.feedback;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import androidx.lifecycle.ViewModelProvider;
import com.maritech.arterium.R;
import com.maritech.arterium.common.ContentState;
import com.maritech.arterium.databinding.ActivityFeedbackBinding;
import com.maritech.arterium.ui.base.BaseActivity;
import com.maritech.arterium.utils.ToastUtil;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public class FeedbackActivity extends BaseActivity<ActivityFeedbackBinding> {

    FeedbackViewModel viewModel;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(FeedbackViewModel.class);

        binding.toolbar.tvToolbarTitle.setText(getString(R.string.feedback));
        binding.toolbar.ivRight.setVisibility(View.GONE);
        binding.toolbar.ivArrow.setOnClickListener(v -> finish());

        binding.etTitle.addTextChangedListener(textWatcher);
        binding.etText.addTextChangedListener(textWatcher);

        binding.btnSend.setOnClickListener(v -> {
            String title = binding.etTitle.getText().toString();
            String text = binding.etText.getText().toString();

            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("title", title)
                    .addFormDataPart("message", text)
                    .build();

            sendFeedback(requestBody);
        });

        observeViewModel();
    }

    private void observeViewModel() {
        viewModel.responseLiveData.observe(this, response -> {
            binding.progressBar.setVisibility(View.GONE);

            if (response.isSuccess()) {

                binding.etTitle.setText("");
                binding.etText.setText("");

                ToastUtil.show(
                        FeedbackActivity.this,
                        getString(R.string.feedback_send_success)
                );
            } else {
                ToastUtil.show(
                        FeedbackActivity.this,
                        getString(R.string.feedback_send_failure)
                );
            }
        });

        viewModel.contentState.observe(this, contentState -> {
            if (contentState == ContentState.LOADING) {
                binding.progressBar.setVisibility(View.VISIBLE);
                binding.etTitle.setEnabled(false);
                binding.etText.setEnabled(false);
                binding.btnSend.setEnabled(false);

            } else {
                binding.progressBar.setVisibility(View.GONE);
                binding.etTitle.setEnabled(true);
                binding.etText.setEnabled(true);
                binding.btnSend.setEnabled(true);
            }
        });
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            String title = binding.etTitle.getText().toString();
            String text = binding.etText.getText().toString();

            binding.btnSend.setEnabled(title.length() > 5 && text.length() > 10);
        }
    };

    private void sendFeedback(RequestBody body) {
        viewModel.sendFeedback(body);
    }
}