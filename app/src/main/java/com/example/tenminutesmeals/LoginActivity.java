package com.example.tenminutesmeals;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    // تعريف العناصر
    EditText etUsername, etPassword;
    Button btnLogin, btnGoToRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login); // سيظهر خط أحمر هنا مؤقتاً

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // منطق تسجيل الدخول
                String user = etUsername.getText().toString(); // نأخذ الاسم اللي كتبه المستخدم

                if (!user.isEmpty()) {
                    // 1. الانتقال من صفحة Login إلى صفحة Main
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);

                    // 2. تمرير البيانات (اسم المستخدم) للشاشة التالية
                    // هذا هو المطلوب في فقرة "data passing between two activities"
                    intent.putExtra("USER_NAME", user);

                    startActivity(intent); // تنفيذ الانتقال
                } else {
                    // إظهار Toast في حال لم يكتب المستخدم اسمه[cite: 1]
                    Toast.makeText(LoginActivity.this, "Please enter your name", Toast.LENGTH_SHORT).show();
                }
            }
        }); //  setOnClickListener
    } //  onCreate
} //  LoginActivity LoginActivity