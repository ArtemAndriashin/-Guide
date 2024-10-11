package com.example.shawermaguide;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

public class ProfileActivity extends AppCompatActivity {

    //main_1
    private ImageView profilePhoto;
    private TextView username;
    private TextView ratingNumber;

    //main_2
    private TextView personalData;
    private TextView reviews;
    private TextView favorites;

    //main_3
    private TextView distribution;

    //main_4
    private TextView exitButton;
    private TextView deleteButton;

    //main_4
    private TextView mainMenu;
    private TextView historyMenu;
    private TextView profileMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference docRef = db.collection("users").document(userId);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    String userLogin = documentSnapshot.getString("username");
                    username = findViewById(R.id.username);
                    username.setText(userLogin);
                }
            }
        });

        setContentView(R.layout.activity_profile);

        personalData = findViewById(R.id.personalData);
        reviews = findViewById(R.id.reviews);
        favorites = findViewById(R.id.favorites);

        distribution = findViewById(R.id.distribution);

        exitButton = findViewById(R.id.exitButton);
        deleteButton = findViewById(R.id.deleteButton);

        mainMenu = findViewById(R.id.main_menu);
        historyMenu = findViewById(R.id.history_menu);
        profileMenu = findViewById(R.id.profile_menu);


        personalData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, PersonalDataActivity.class));
            }
        });

        reviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, ReviewsActivity.class));
            }
        });

        favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, FavoritesActivity.class));
            }
        });

        distribution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, DistributionActivity.class));
            }
        });

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLogoutConfirmationDialog();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteUser();
            }
        });

        mainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
            }
        });

        historyMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, HistoryActivity.class));
            }
        });

        profileMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void showLogoutConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Вийти з облікового запису")
                .setMessage("Ви впевнені, що хочете вийти з облікового запису?")
                .setPositiveButton("Підтвердити", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        logoutUser();
                    }
                })
                .setNegativeButton("Скасувати", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setCancelable(false)
                .show();
    }

    private void logoutUser() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(ProfileActivity.this, AuthorizationActivity.class));
        finish();
    }

    private void deleteUser() {
        new AlertDialog.Builder(this)
                .setTitle("Видалення облікового запису")
                .setMessage("Ви впевнені, що хочете видалити ваш обліковий запис?")
                .setPositiveButton("Підтвердити", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        if (user != null) {
                            user.delete();
                            startActivity(new Intent(ProfileActivity.this, AuthorizationActivity.class));
                        }
                    }
                })
                .setNegativeButton("Скасувати", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setCancelable(false)
                .show();
    }
}