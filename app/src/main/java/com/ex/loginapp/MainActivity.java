package com.ex.loginapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText etUsername, etPassword;
    Button btnLogin;
    CheckBox cbRemember;
    TextView tvForgot;

    // SharedPreferences للتخزين المحلي
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // الربط بين العناصر والـ XML
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        cbRemember = findViewById(R.id.cbRemember);
        tvForgot = findViewById(R.id.tvForgotPassword);

        // استخدام SharedPreferences
        sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        boolean rememberMe = sharedPreferences.getBoolean("rememberMe", false);
        if (rememberMe) {
            // إذا تم اختيار "تذكرني"
            etUsername.setText(sharedPreferences.getString("username", ""));
            etPassword.setText(sharedPreferences.getString("password", ""));
            cbRemember.setChecked(true);
        }

        btnLogin.setOnClickListener(view -> {
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            // التحقق من أن طول كلمة المرور واسم المستخدم لا يتجاوز 6 أحرف
            if (username.length() > 6 || password.length() > 6) {
                Toast.makeText(MainActivity.this, "اسم المستخدم أو كلمة المرور يجب ألا تتجاوز 6 أحرف أو أرقام", Toast.LENGTH_SHORT).show();
                return;
            }

            if (username.equals("admin") && password.equals("1234")) {
                Toast.makeText(MainActivity.this, "تم تسجيل الدخول", Toast.LENGTH_SHORT).show();

                // حفظ بيانات "تذكرني" في SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if (cbRemember.isChecked()) {
                    editor.putBoolean("rememberMe", true);
                    editor.putString("username", username);
                    editor.putString("password", password);
                } else {
                    editor.putBoolean("rememberMe", false);
                }
                editor.apply();

                // الانتقال إلى الصفحة الرئيسية
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(MainActivity.this, "بيانات غير صحيحة", Toast.LENGTH_SHORT).show();
            }
        });

        tvForgot.setOnClickListener(view ->
                Toast.makeText(this, "ميزة استرجاع كلمة المرور غير مفعلة بعد", Toast.LENGTH_SHORT).show());
    }
}
