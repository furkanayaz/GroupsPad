package com.furkanayaz.groupspad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import maes.tech.intentanim.CustomIntent;

public class SignUpActivity extends AppCompatActivity {
    private Toolbar toolbarSignUp;
    private ImageView imageViewSignUpBack;
    private TextView textViewSignUpAlreadyHaveAnAccount;

    private TextInputEditText textInputEditTextSignUpNameLastname,textInputEditTextSignUpEmail,textInputEditTextSignUpPassword;
    private Spinner spinnerSignUpCountries;

    private ArrayList<String> countriesArrayList = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapter;

    private CardView cardViewSignUp;
    private ProgressBar progressBarSignUp;

    private SharedPreferences sharedPreferencesCountry;
    private SharedPreferences.Editor editorCountry;

    private AdView adViewSignUp;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore firebaseFirestore;

    private int signupcontroller = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        toolbarSignUp = findViewById(R.id.toolbarSignUp);
        imageViewSignUpBack = findViewById(R.id.imageViewSignUpBack);
        textViewSignUpAlreadyHaveAnAccount = findViewById(R.id.textViewSignUpAlreadyHaveAnAccount);

        textInputEditTextSignUpNameLastname = findViewById(R.id.textInputEditTextSignUpNameLastname);
        textInputEditTextSignUpEmail = findViewById(R.id.textInputEditTextSignUpEmail);
        textInputEditTextSignUpPassword = findViewById(R.id.textInputEditTextSignUpPassword);

        spinnerSignUpCountries = findViewById(R.id.spinnerSignUpCountries);

        cardViewSignUp = findViewById(R.id.cardViewSignUp);

        progressBarSignUp = findViewById(R.id.progressBarSignUp);

        adViewSignUp = findViewById(R.id.adViewSignUp);

        setSupportActionBar(toolbarSignUp);

        countriesArrayList.add("Turkey");
        countriesArrayList.add("Azerbaijan");
        countriesArrayList.add("United States of America");
        countriesArrayList.add("United Kingdom");
        countriesArrayList.add("India");
        countriesArrayList.add("Germany");
        countriesArrayList.add("France");
        countriesArrayList.add("Australia");
        countriesArrayList.add("Canada");
        countriesArrayList.add("New Zealand");
        countriesArrayList.add("Philippines");
        countriesArrayList.add("Uganda");
        countriesArrayList.add("Pakistan");
        countriesArrayList.add("Nigeria");
        countriesArrayList.add("Singapore");
        countriesArrayList.add("Ireland");
        countriesArrayList.add("South Africa");
        countriesArrayList.add("Dominicia");
        countriesArrayList.add("Sudan");
        countriesArrayList.add("Madagascar");
        countriesArrayList.add("Kenya");

        arrayAdapter = new ArrayAdapter<String>(SignUpActivity.this, android.R.layout.simple_list_item_1,android.R.id.text1,countriesArrayList);

        spinnerSignUpCountries.setAdapter(arrayAdapter);

        MobileAds.initialize(SignUpActivity.this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });

        AdRequest adRequest = new AdRequest.Builder().build();

        adViewSignUp.loadAd(adRequest);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        imageViewSignUpBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        textViewSignUpAlreadyHaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
                startActivity(intent);
                CustomIntent.customType(SignUpActivity.this,"fadein-to-fadeout");
                finish();
            }
        });

        cardViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String namelastname = textInputEditTextSignUpNameLastname.getText().toString().trim();
                String email = textInputEditTextSignUpEmail.getText().toString().trim();
                String country = countriesArrayList.get(spinnerSignUpCountries.getSelectedItemPosition());
                String password = textInputEditTextSignUpPassword.getText().toString().trim();


                if (!(namelastname.length() <=4) && Patterns.EMAIL_ADDRESS.matcher(email).matches() && !(password.length() <=5)){
                    firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){

                                sharedPreferencesCountry = getSharedPreferences("country",MODE_PRIVATE);
                                editorCountry = sharedPreferencesCountry.edit();
                                editorCountry.clear();
                                editorCountry.putString("country",country);
                                editorCountry.commit();

                                progressBarSignUp.setVisibility(View.VISIBLE);
                                cardViewSignUp.setVisibility(View.INVISIBLE);

                                firebaseUser = firebaseAuth.getCurrentUser();
                                firebaseFirestore = FirebaseFirestore.getInstance();
                                String uid = firebaseUser.getUid();

                                Map<String,Object> user = new HashMap<>();

                                user.put("namelastname",namelastname);
                                user.put("email",email);
                                user.put("country",country);
                                user.put("password",password);

                                firebaseFirestore.collection(uid)
                                        .add(user)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                signupcontroller = 1;
                                                progressBarSignUp.setVisibility(View.INVISIBLE);
                                                cardViewSignUp.setVisibility(View.VISIBLE);
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                        Toast.makeText(SignUpActivity.this,"Hesabınız oluşturulurken hata meydana geldi",Toast.LENGTH_SHORT).show();


                                    }
                                });

                                Intent intent = new Intent(SignUpActivity.this,MainActivity.class);
                                startActivity(intent);
                                CustomIntent.customType(SignUpActivity.this,"fadein-to-fadeout");
                                finish();

                            }else {

                                if (!(signupcontroller == 1)){
                                    Toast.makeText(SignUpActivity.this,"Bir şeyler ters gittiğinden işleminiz gerçekleştirilemedi. Bu e-postaya kayıtlı başka bir hesap olabilir.",Toast.LENGTH_LONG).show();
                                }

                            }

                        }
                    });
                }else {
                    Toast.makeText(SignUpActivity.this,"Bilgileriniz az karakter içerdiğinden üyeliğiniz doğrulanamadı",Toast.LENGTH_LONG).show();
                }

            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(SignUpActivity.this,MainActivity.class);
        startActivity(intent);
        CustomIntent.customType(SignUpActivity.this,"right-to-left");
        finish();
    }
}