package com.example.shawermaguide;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.ConditionVariable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_window);

        SharedPreferences sp = getSharedPreferences("PC", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        EditText email = findViewById(R.id.email);
        EditText password = findViewById(R.id.password);
        ConstraintLayout button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailText = email.getText().toString();
                String passwordText  = password.getText().toString();
                if(emailText.isEmpty() || !emailText.contains("@"))
                    Toast.makeText(RegisterActivity.this, "Проверьте поле Email", Toast.LENGTH_LONG).show();
                else if (passwordText.isEmpty() || passwordText.length() < 6) {
                    Toast.makeText(RegisterActivity.this, "Пароль должен быть больше 5 символов", Toast.LENGTH_LONG).show();
                }else{
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    Map<String, Object> user = new HashMap<>();
                    user.put("email", emailText);
                    user.put("password", passwordText);

// Add a new document with a generated ID
                    db.collection("users")
                            .add(user)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    editor.putString("Email", emailText).commit();
                                    Intent intent = new Intent(RegisterActivity.this, ProfileActivity.class);
                                    startActivity(intent);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(RegisterActivity.this,"Неполучилось, попробуйте позже!", Toast.LENGTH_LONG).show();
                                }
                            });
                }
            }
        });
    }
}
