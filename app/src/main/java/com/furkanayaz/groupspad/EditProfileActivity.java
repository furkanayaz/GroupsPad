package com.furkanayaz.groupspad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

import maes.tech.intentanim.CustomIntent;

public class EditProfileActivity extends AppCompatActivity {
    private Toolbar toolbarEditProfile;
    private ImageView imageViewEditProfileBack;

    private TextInputEditText textInputEditTextEditProfileNameLastname,textInputEditTextEditProfileEmail;
    private CardView cardViewEditProfileSave;
    private TextView textView11;
    private ProgressBar progressBarEditProfile;
    private TextView textViewEditProfileCountry;
    private TextView textViewEditProfilePassword;

    private AdView adViewEditProfile;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        toolbarEditProfile = findViewById(R.id.toolbarEditProfile);
        imageViewEditProfileBack = findViewById(R.id.imageViewEditProfileBack);

        textInputEditTextEditProfileNameLastname = findViewById(R.id.textInputEditTextEditProfileNameLastname);
        textInputEditTextEditProfileEmail = findViewById(R.id.textInputEditTextEditProfileEmail);

        cardViewEditProfileSave = findViewById(R.id.cardViewEditProfileSave);

        textView11 = findViewById(R.id.textView11);

        progressBarEditProfile = findViewById(R.id.progressBarEditProfile);

        textViewEditProfileCountry = findViewById(R.id.textViewEditProfileCountry);
        textViewEditProfilePassword = findViewById(R.id.textViewEditProfilePassword);

        adViewEditProfile = findViewById(R.id.adViewEditProfile);

        setSupportActionBar(toolbarEditProfile);

        textInputEditTextEditProfileEmail.setClickable(false);
        textInputEditTextEditProfileEmail.setLongClickable(false);

        MobileAds.initialize(EditProfileActivity.this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });

        AdRequest adRequest = new AdRequest.Builder().build();

        adViewEditProfile.loadAd(adRequest);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();

        firebaseFirestore.collection(firebaseUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                        String namelastname = documentSnapshot.get("namelastname").toString();
                        String email = documentSnapshot.get("email").toString();
                        String country = documentSnapshot.get("country").toString();
                        String password = documentSnapshot.get("password").toString();

                        textInputEditTextEditProfileNameLastname.setText(namelastname);
                        textInputEditTextEditProfileEmail.setText(email);

                        textViewEditProfileCountry.setText(country);
                        textViewEditProfilePassword.setText(password);
                    }
                }
            }
        });

        imageViewEditProfileBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditProfileActivity.this,MainActivity.class);
                startActivity(intent);
                CustomIntent.customType(EditProfileActivity.this,"right-to-left");
                finish();
            }
        });

        textInputEditTextEditProfileEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (textView11.getText().toString().trim().contains("Kaydet")){
                    Toast.makeText(EditProfileActivity.this,"Maalesef e-posta değişikliği hizmeti sunamıyoruz",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(EditProfileActivity.this,"Sorry, we can't offer email change service",Toast.LENGTH_LONG).show();
                }
            }
        });

        cardViewEditProfileSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String namelastname = textInputEditTextEditProfileNameLastname.getText().toString().trim();
                String email = textInputEditTextEditProfileEmail.getText().toString().trim();
                String country = textViewEditProfileCountry.getText().toString().trim();
                String password = textViewEditProfilePassword.getText().toString().trim();

                if (!(namelastname.length() <= 4)){

                    Map<String,Object> userUpdate = new HashMap<>();

                    userUpdate.put("namelastname",namelastname);
                    userUpdate.put("email",email);
                    userUpdate.put("country",country);
                    userUpdate.put("password",password);

                    firebaseFirestore.collection(firebaseUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()){

                                cardViewEditProfileSave.setVisibility(View.INVISIBLE);
                                progressBarEditProfile.setVisibility(View.VISIBLE);

                                for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                    firebaseFirestore.collection(firebaseUser.getUid()).document(documentSnapshot.getId()).delete();
                                }
                            }
                        }
                    });

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            firebaseFirestore.collection(firebaseUser.getUid()).add(userUpdate).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {

                                    if (textView11.getText().toString().trim().contains("Kaydet")){
                                        Toast.makeText(EditProfileActivity.this,"Bilgileriniz başarıyla güncellendi",Toast.LENGTH_LONG).show();
                                    }else {
                                        Toast.makeText(EditProfileActivity.this,"Your information has been updated successfully",Toast.LENGTH_LONG).show();
                                    }



                                    onBackPressed();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    if (textView11.getText().toString().trim().contains("Kaydet")){
                                        Toast.makeText(EditProfileActivity.this,"Bilgileriniz güncellenirken hata meydana geldi",Toast.LENGTH_LONG).show();
                                    }else {
                                        Toast.makeText(EditProfileActivity.this,"Error occurred while updating your information",Toast.LENGTH_LONG).show();
                                    }


                                }
                            });

                            progressBarEditProfile.setVisibility(View.INVISIBLE);
                            cardViewEditProfileSave.setVisibility(View.VISIBLE);

                        }
                    },6000);



                }

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(EditProfileActivity.this,MainActivity.class);
        startActivity(intent);
        CustomIntent.customType(EditProfileActivity.this,"right-to-left");
        finish();
    }
}