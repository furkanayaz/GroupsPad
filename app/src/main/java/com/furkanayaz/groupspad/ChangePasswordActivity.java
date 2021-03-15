package com.furkanayaz.groupspad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

import maes.tech.intentanim.CustomIntent;

public class ChangePasswordActivity extends AppCompatActivity {
    private Toolbar toolbarChangePassword;
    private ImageView imageViewChangePasswordBack;

    private TextInputEditText textInputEditTextChangePasswordDefaultPassword;
    private TextInputEditText textInputEditTextChangePasswordNewPassword;
    private TextInputEditText textInputEditTextChangePasswordConfirmNewPassword;

    private CardView cardViewChangePasswordSave;

    private TextView textView22;

    private ProgressBar progressBarChangePassword;

    private AdView adViewChangePassword;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore firebaseFirestore;

    private String firestoreNameLastname;
    private String firestoreEmail;
    private String firestoreCountry;
    private String defaultFirestorePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        toolbarChangePassword = findViewById(R.id.toolbarChangePassword);
        imageViewChangePasswordBack = findViewById(R.id.imageViewChangePasswordBack);

        textInputEditTextChangePasswordDefaultPassword = findViewById(R.id.textInputEditTextChangePasswordDefaultPassword);
        textInputEditTextChangePasswordNewPassword = findViewById(R.id.textInputEditTextChangePasswordNewPassword);
        textInputEditTextChangePasswordConfirmNewPassword = findViewById(R.id.textInputEditTextChangePasswordConfirmNewPassword);

        cardViewChangePasswordSave = findViewById(R.id.cardViewChangePasswordSave);

        textView22 = findViewById(R.id.textView22);

        progressBarChangePassword = findViewById(R.id.progressBarChangePassword);

        adViewChangePassword = findViewById(R.id.adViewChangePassword);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();

        MobileAds.initialize(ChangePasswordActivity.this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });

        AdRequest adRequest = new AdRequest.Builder().build();

        adViewChangePassword.loadAd(adRequest);

        imageViewChangePasswordBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        cardViewChangePasswordSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String defaultpassword = textInputEditTextChangePasswordDefaultPassword.getText().toString().trim();
                String newpassword = textInputEditTextChangePasswordNewPassword.getText().toString().trim();
                String confirmpassword = textInputEditTextChangePasswordConfirmNewPassword.getText().toString().trim();

                firebaseFirestore.collection(firebaseUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                firestoreNameLastname = documentSnapshot.get("namelastname").toString();
                                firestoreEmail = documentSnapshot.get("email").toString();
                                firestoreCountry = documentSnapshot.get("country").toString();
                                defaultFirestorePassword = documentSnapshot.get("password").toString();
                            }
                        }
                    }
                });

                if (defaultpassword.equals(defaultFirestorePassword) && newpassword.equals(confirmpassword) && !(newpassword.length() <= 5)){

                    progressBarChangePassword.setVisibility(View.VISIBLE);
                    cardViewChangePasswordSave.setVisibility(View.INVISIBLE);

                    Map<String,Object> updatePassword = new HashMap<>();
                    updatePassword.put("namelastname",firestoreNameLastname);
                    updatePassword.put("email",firestoreEmail);
                    updatePassword.put("country",firestoreCountry);
                    updatePassword.put("password",newpassword);

                    firebaseFirestore.collection(firebaseUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()){
                                for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                    firebaseFirestore.collection(firebaseUser.getUid()).document(documentSnapshot.getId()).delete();
                                }
                            }
                        }
                    });

                    firebaseUser.updatePassword(newpassword);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            firebaseFirestore.collection(firebaseUser.getUid()).add(updatePassword).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {

                                    if (textView22.getText().toString().trim().contains("Kaydet")){
                                        Toast.makeText(ChangePasswordActivity.this,"Şifreniz başarıyla güncellendi",Toast.LENGTH_LONG).show();
                                    }else {
                                        Toast.makeText(ChangePasswordActivity.this,"Your password has been successfully updated",Toast.LENGTH_LONG).show();
                                    }

                                    onBackPressed();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    if (textView22.getText().toString().trim().contains("Kaydet")){
                                        Toast.makeText(ChangePasswordActivity.this,"Şifreniz güncellenirken hata meydana geldi",Toast.LENGTH_LONG).show();
                                    }else {
                                        Toast.makeText(ChangePasswordActivity.this,"Error occurred while updating your password",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                            progressBarChangePassword.setVisibility(View.INVISIBLE);
                            cardViewChangePasswordSave.setVisibility(View.VISIBLE);

                        }
                    },6000);



                }else if (textView22.getText().toString().trim().contains("Kaydet")){
                    Toast.makeText(ChangePasswordActivity.this,"Şifreniz az veya eksik karakter içerdiğinden değiştirilemedi",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(ChangePasswordActivity.this,"Your password could not be changed because it contains low or missing characters.",Toast.LENGTH_LONG).show();
                }

            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ChangePasswordActivity.this,MainActivity.class);
        startActivity(intent);
        CustomIntent.customType(ChangePasswordActivity.this,"right-to-left");
        finish();
    }
}