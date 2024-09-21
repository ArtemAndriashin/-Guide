package com.example.shawermaguide;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthorizationActivity extends AppCompatActivity {
    private EditText emailEditText, passwordEditText;
    private Button loginButton;
    private TextView registerButton;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);



        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);
        firebaseAuth = FirebaseAuth.getInstance();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AuthorizationActivity.this, RegistrationActivity.class));
            }
        });
    }

    private void loginUser() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (email.isEmpty() || !email.contains("@")) {
            Toast.makeText(AuthorizationActivity.this, "Проверьте поле Email", Toast.LENGTH_SHORT).show();
        } else if (password.isEmpty() || password.length() < 6) {
            Toast.makeText(AuthorizationActivity.this, "Пароль слишком короткий", Toast.LENGTH_SHORT).show();
        }

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                Toast.makeText(AuthorizationActivity.this, "Авторизация прошла успешно", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AuthorizationActivity.this, ProfileActivity.class));
                finish();
            } else {
                Toast.makeText(AuthorizationActivity.this, "Ошибка входа", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
