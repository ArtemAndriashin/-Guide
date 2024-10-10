package com.example.shawermaguide;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText, usernameEditText, passwordRepeatEditText, dataBirthEditText;
    private Button registerButton;
    private FirebaseAuth firebaseAuth;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        emailEditText = findViewById(R.id.emailEditText);
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        passwordRepeatEditText = findViewById(R.id.passwordRepeatEditText);
        dataBirthEditText = findViewById(R.id.dataBirthEditText);
        registerButton = findViewById(R.id.registerButton);
        firebaseAuth = FirebaseAuth.getInstance();
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String repeatedPassword = passwordRepeatEditText.getText().toString().trim();
        String login = usernameEditText.getText().toString().trim();
        String dataBirth = dataBirthEditText.getText().toString().trim();

        if (email.isEmpty() || !email.contains("@")) {
            Toast.makeText(RegistrationActivity.this, "Перевірте поле Email", Toast.LENGTH_SHORT).show();
        }else if(login.isEmpty()){
            Toast.makeText(RegistrationActivity.this, "Введіть логін", Toast.LENGTH_SHORT).show();
        }else if (login.length() > 26){
            Toast.makeText(RegistrationActivity.this, "Довжина логіну має бути не більше 25 символів", Toast.LENGTH_SHORT).show();
        }else if (password.isEmpty() || password.length() < 6) {
            Toast.makeText(RegistrationActivity.this, "Пароль надто короткий", Toast.LENGTH_SHORT).show();
        }else if (!repeatedPassword.equals(password)){
            Toast.makeText(RegistrationActivity.this, "Паролі повинні збігатися", Toast.LENGTH_SHORT).show();
        }else {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            db = FirebaseFirestore.getInstance();

                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            String userId = user.getUid();

                            Map<String, Object> data = new HashMap<>();
                            data.put("username", login);
                            data.put("email", email);
                            data.put("dataOfBirth", dataBirth);
                            data.put("rating", "0.00");

                            db.collection("users").document(userId)
                                    .set(data)
                                    .addOnSuccessListener(aVoid -> Log.d(TAG, "DocumentSnapshot successfully written!"))
                                    .addOnFailureListener(e -> Log.w(TAG, "Error writing document", e));;

                            Toast.makeText(RegistrationActivity.this, "Реєстрація успішна!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegistrationActivity.this, ProfileActivity.class));
                            finish();
                        } else {
                            Toast.makeText(RegistrationActivity.this, "Помилка реєстрації!", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}
