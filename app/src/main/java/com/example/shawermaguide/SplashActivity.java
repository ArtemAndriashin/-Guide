package com.example.shawermaguide;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        firebaseAuth = FirebaseAuth.getInstance();

        new Handler().postDelayed(new Runnable() {
            FirebaseUser currentUser = firebaseAuth.getCurrentUser();
            @Override
            public void run() {
                if(currentUser != null){
                    startActivity(new Intent(SplashActivity.this, ProfileActivity.class));
                    finish();
                }else{
                    startActivity(new Intent(SplashActivity.this, AuthorizationActivity.class));
                }
            }
        },1000);
    }
}