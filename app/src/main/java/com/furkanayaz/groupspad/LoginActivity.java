package com.furkanayaz.groupspad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import maes.tech.intentanim.CustomIntent;

public class LoginActivity extends AppCompatActivity {
    private Toolbar toolbarLogin;
    private ImageView imageViewLoginBack;
    private CardView cardViewLogin;
    private TextView textViewLoginForgotPassword;
    private TextInputEditText textInputEditTextLoginEmail,textInputEditTextLoginPassword;
    private ProgressBar progressBarLogin;

    private SharedPreferences sharedPreferencesCountry;
    private SharedPreferences.Editor editorCountry;

    private AdView adViewLogin;

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        toolbarLogin = findViewById(R.id.toolbarLogin);
        imageViewLoginBack = findViewById(R.id.imageViewLoginBack);

        cardViewLogin = findViewById(R.id.cardViewLogin);

        textViewLoginForgotPassword = findViewById(R.id.textViewLoginForgotPassword);

        textInputEditTextLoginEmail = findViewById(R.id.textInputEditTextLoginEmail);
        textInputEditTextLoginPassword = findViewById(R.id.textInputEditTextLoginPassword);

        progressBarLogin = findViewById(R.id.progressBarLogin);

        setSupportActionBar(toolbarLogin);

        adViewLogin = findViewById(R.id.adViewLogin);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();


        MobileAds.initialize(LoginActivity.this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });

        AdRequest adRequest = new AdRequest.Builder().build();

        adViewLogin.loadAd(adRequest);

        imageViewLoginBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        textViewLoginForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textViewLoginForgotPassword.getText().toString().trim().contains("Şifremi")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setTitle("GROUPSPAD ŞİFREMİ UNUTTUM");
                    View view = LayoutInflater.from(LoginActivity.this).inflate(R.layout.alert_forgotpassword,null,false);
                    TextInputEditText textInputEditTextForgotPassword = view.findViewById(R.id.textInputEditTextForgotPassword);
                    builder.setPositiveButton("GÖNDER", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String email = textInputEditTextForgotPassword.getText().toString().trim();

                            if (email.length()>=10 || Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                                firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            Toast.makeText(LoginActivity.this,"Şifre sıfırlama bağlantısı e-posta adresinize başarıyla gönderildi",Toast.LENGTH_LONG).show();
                                        }else {
                                            Toast.makeText(LoginActivity.this,"E-Posta adresi kayıtlı olamadığından şifre sıfırlama bağlantısı gönderilemedi",Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }else {
                                Toast.makeText(LoginActivity.this,"Bilgilerinizi kontrol ediniz",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    builder.setNegativeButton("İPTAL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.setView(view);
                    builder.create().show();
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setTitle("GROUPSPAD FORGOT MY PASSWORD");
                    View view = LayoutInflater.from(LoginActivity.this).inflate(R.layout.alert_forgotpassword,null,false);
                    TextInputEditText textInputEditTextForgotPassword = view.findViewById(R.id.textInputEditTextForgotPassword);
                    builder.setPositiveButton("SEND", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String email = textInputEditTextForgotPassword.getText().toString().trim();

                            if (email.length()>=10 || Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                                firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            Toast.makeText(LoginActivity.this,"Password reset link has been successfully sent to your email address",Toast.LENGTH_LONG).show();
                                        }else {
                                            Toast.makeText(LoginActivity.this,"The password reset link could not be sent because the e-mail address could not be registered",Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }else {
                                Toast.makeText(LoginActivity.this,"Check your information",Toast.LENGTH_LONG).show();
                            }


                        }
                    });
                    builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.setView(view);
                    builder.create().show();
                }


            }
        });

        cardViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBarLogin.setVisibility(View.VISIBLE);
                cardViewLogin.setVisibility(View.INVISIBLE);

                String email = textInputEditTextLoginEmail.getText().toString().trim();
                String password = textInputEditTextLoginPassword.getText().toString().trim();

                if (!email.isEmpty() || !password.isEmpty()){
                    firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                firebaseAuth = FirebaseAuth.getInstance();
                                firebaseUser = firebaseAuth.getCurrentUser();
                                firebaseFirestore = FirebaseFirestore.getInstance();
                                firebaseFirestore.collection(firebaseUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()){
                                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                                String country = documentSnapshot.get("country").toString();

                                                sharedPreferencesCountry = getSharedPreferences("country",MODE_PRIVATE);
                                                editorCountry = sharedPreferencesCountry.edit();
                                                editorCountry.clear();
                                                editorCountry.putString("country",country);
                                                editorCountry.commit();

                                            }
                                        }
                                    }
                                });

                                progressBarLogin.setVisibility(View.INVISIBLE);
                                cardViewLogin.setVisibility(View.VISIBLE);

                                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                startActivity(intent);
                                CustomIntent.customType(LoginActivity.this,"fadein-to-fadeout");
                                finish();

                            }else {
                                Toast.makeText(LoginActivity.this,"Hesabınıza giriş yapılamadı",Toast.LENGTH_LONG).show();

                                progressBarLogin.setVisibility(View.INVISIBLE);
                                cardViewLogin.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                }else {
                    Toast.makeText(LoginActivity.this,"Bilgilerinizi kontrol ediniz",Toast.LENGTH_LONG).show();
                    progressBarLogin.setVisibility(View.INVISIBLE);
                    cardViewLogin.setVisibility(View.VISIBLE);
                }




            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intent);
        CustomIntent.customType(LoginActivity.this,"right-to-left");
        finish();
    }
}