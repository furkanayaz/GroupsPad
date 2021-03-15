package com.furkanayaz.groupspad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
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

public class FeedBackActivity extends AppCompatActivity {
    private ImageView imageViewFeedBackBack;
    private TextInputEditText textInputEditTextFeedBackNameLastname,textInputEditTextFeedBackEmail,textInputEditTextFeedBackMessage;
    private CardView cardViewFeedBackSend;
    private TextView textViewFeedBackSend;

    private AdView adViewFeedBack;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore firebaseFirestore;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);

        imageViewFeedBackBack = findViewById(R.id.imageViewFeedBackBack);

        textInputEditTextFeedBackNameLastname = findViewById(R.id.textInputEditTextFeedBackNameLastname);
        textInputEditTextFeedBackEmail = findViewById(R.id.textInputEditTextFeedBackEmail);
        textInputEditTextFeedBackMessage = findViewById(R.id.textInputEditTextFeedBackMessage);

        cardViewFeedBackSend = findViewById(R.id.cardViewFeedBackSend);
        textViewFeedBackSend = findViewById(R.id.textViewFeedBackSend);

        adViewFeedBack = findViewById(R.id.adViewFeedBack);

        imageViewFeedBackBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();

        MobileAds.initialize(FeedBackActivity.this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });

        AdRequest adRequest = new AdRequest.Builder().build();

        adViewFeedBack.loadAd(adRequest);

        textInputEditTextFeedBackNameLastname.setClickable(false);
        textInputEditTextFeedBackNameLastname.setLongClickable(false);
        textInputEditTextFeedBackEmail.setClickable(false);
        textInputEditTextFeedBackEmail.setLongClickable(false);

        if (firebaseUser == null){

            textInputEditTextFeedBackNameLastname.setText("Anonim");
            textInputEditTextFeedBackEmail.setText("anonim.groupspad.com");

        }else {
            firebaseFirestore.collection(firebaseUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()){
                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                            String namelastname = documentSnapshot.get("namelastname").toString();
                            String email = documentSnapshot.get("email").toString();

                            textInputEditTextFeedBackNameLastname.setText(namelastname);
                            textInputEditTextFeedBackEmail.setText(email);

                        }
                    }
                }
            });
        }



        cardViewFeedBackSend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (firebaseUser == null){

                    String message = textInputEditTextFeedBackMessage.getText().toString().trim();

                    if (!(message.length() <= 2)){
                        Map<String,Object> feedBackMap = new HashMap<>();
                        feedBackMap.put("namelastname",textInputEditTextFeedBackNameLastname.getText().toString().trim());
                        feedBackMap.put("email",textInputEditTextFeedBackEmail.getText().toString().trim());
                        feedBackMap.put("message",message);

                        firebaseFirestore.collection("anonim").add(feedBackMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                if (textViewFeedBackSend.getText().toString().trim().contains("Gönder")){
                                    Toast.makeText(FeedBackActivity.this,"Geri dönüşünüz başarıyla gönderildi",Toast.LENGTH_LONG).show();
                                }else {
                                    Toast.makeText(FeedBackActivity.this,"Your return has been sent successfully",Toast.LENGTH_LONG).show();
                                }


                                onBackPressed();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                if (textViewFeedBackSend.getText().toString().trim().contains("Gönder")){
                                    Toast.makeText(FeedBackActivity.this,"Geri dönüşünüz gönderilirken hata meydana geldi",Toast.LENGTH_LONG).show();
                                }else {
                                    Toast.makeText(FeedBackActivity.this,"An error occurred while sending your return",Toast.LENGTH_LONG).show();
                                }

                            }
                        });
                    }else {
                        if (textViewFeedBackSend.getText().toString().trim().contains("Gönder")){
                            Toast.makeText(FeedBackActivity.this,"Geri dönüş yapabilmek için içeriğinizi doldurunuz",Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(FeedBackActivity.this,"Fill in your content to return",Toast.LENGTH_LONG).show();
                        }

                    }



                }else {

                    String message = textInputEditTextFeedBackMessage.getText().toString().trim();

                    if (!(message.length() <= 2)){
                        Map<String,Object> feedBackMap = new HashMap<>();
                        feedBackMap.put("namelastname",textInputEditTextFeedBackNameLastname.getText().toString().trim());
                        feedBackMap.put("email",textInputEditTextFeedBackEmail.getText().toString().trim());
                        feedBackMap.put("message",message);

                        firebaseFirestore.collection("feedback").add(feedBackMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                if (textViewFeedBackSend.getText().toString().trim().contains("Gönder")){
                                    Toast.makeText(FeedBackActivity.this,"Geri dönüşünüz başarıyla gönderildi",Toast.LENGTH_LONG).show();
                                }else {
                                    Toast.makeText(FeedBackActivity.this,"Your return has been sent successfully",Toast.LENGTH_LONG).show();
                                }

                                onBackPressed();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                if (textViewFeedBackSend.getText().toString().trim().contains("Gönder")){
                                    Toast.makeText(FeedBackActivity.this,"Geri dönüşünüz gönderilirken hata meydana geldi",Toast.LENGTH_LONG).show();
                                }else {
                                    Toast.makeText(FeedBackActivity.this,"An error occurred while sending your return",Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }else {
                        if (textViewFeedBackSend.getText().toString().trim().contains("Gönder")){
                            Toast.makeText(FeedBackActivity.this,"Geri dönüş yapabilmek için içeriğinizi doldurunuz",Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(FeedBackActivity.this,"Fill in your content to return",Toast.LENGTH_LONG).show();
                        }
                    }



                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(FeedBackActivity.this,MainActivity.class);
        startActivity(intent);
        CustomIntent.customType(FeedBackActivity.this,"right-to-left");
        finish();
    }
}