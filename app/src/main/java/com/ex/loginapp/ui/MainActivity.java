package com.ex.loginapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.ex.loginapp.R;
import com.ex.loginapp.auth.AuthService;
import com.ex.loginapp.auth.DefaultAuthService;
import com.ex.loginapp.data.PreferencesHelper;
import com.ex.loginapp.model.AuthenticationResult;
import com.ex.loginapp.model.User;
import com.ex.loginapp.util.Validator;

public class MainActivity extends AppCompatActivity {
    private EditText etUsername, etPassword;
    private Button btnLogin;
    private CheckBox cbRemember;
    private TextView tvForgot;

    private PreferencesHelper preferencesHelper;
    private AuthService authService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();

        // إحقاق الاعتماديات (manual dependency injection)
        preferencesHelper = new PreferencesHelper(this);
        authService = new DefaultAuthService();

        loadRememberedUser();

        btnLogin.setOnClickListener(v -> onLoginClicked());
        tvForgot.setOnClickListener(v ->
                Toast.makeText(this, "ميزة استرجاع كلمة المرور غير مفعلة بعد", Toast.LENGTH_SHORT).show()
        );
    }

    private void bindViews() {
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        cbRemember = findViewById(R.id.cbRemember);
        tvForgot = findViewById(R.id.tvForgotPassword);
    }

    private void loadRememberedUser() {
        User remembered = preferencesHelper.getRememberedUser();
        if (remembered != null) {
            etUsername.setText(remembered.getUsername());
            etPassword.setText(remembered.getPassword());
            cbRemember.setChecked(true);
        }
    }

    private void onLoginClicked() {
        User user = new User(etUsername.getText().toString().trim(),
                etPassword.getText().toString().trim());

        String validationError = Validator.validate(user);
        if (validationError != null) {
            Toast.makeText(this, validationError, Toast.LENGTH_SHORT).show();
            return;
        }

        AuthenticationResult result = authService.authenticate(user);
        Toast.makeText(this, result.getMessage(), Toast.LENGTH_SHORT).show();

        if (result.isSuccess()) {
            if (cbRemember.isChecked()) {
                preferencesHelper.saveRememberedUser(user);
            } else {
                preferencesHelper.clearRememberedUser();
            }
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        }
    }
}
