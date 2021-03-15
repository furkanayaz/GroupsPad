package com.furkanayaz.groupspad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import maes.tech.intentanim.CustomIntent;

public class UserAgreementActivity extends AppCompatActivity {
    private Toolbar toolbarUserAgreement;
    private ImageView imageViewUserAgreementBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_agreement);

        toolbarUserAgreement = findViewById(R.id.toolbarUserAgreement);
        imageViewUserAgreementBack = findViewById(R.id.imageViewUserAgreementBack);

        imageViewUserAgreementBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(UserAgreementActivity.this,MainActivity.class);
        startActivity(intent);
        CustomIntent.customType(UserAgreementActivity.this,"right-to-left");
        finish();
    }
}