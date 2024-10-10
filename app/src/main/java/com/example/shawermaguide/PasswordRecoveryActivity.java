package com.example.shawermaguide;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PasswordRecoveryActivity extends AppCompatActivity {

    private EditText emailEditText, confirmationCode;
    private Button getCodeButton, confirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_recovery);

        emailEditText = findViewById(R.id.emailEditText);
        confirmationCode = findViewById(R.id.confirmationCode);
        getCodeButton = findViewById(R.id.getCodeButton);
        confirmButton = findViewById(R.id.confirmButton);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PasswordRecoveryActivity.this, NewPasswordActivity.class));
            }
        });
    }
}