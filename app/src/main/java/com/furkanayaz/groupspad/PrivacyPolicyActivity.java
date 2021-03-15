package com.furkanayaz.groupspad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import maes.tech.intentanim.CustomIntent;

public class PrivacyPolicyActivity extends AppCompatActivity {
    private Toolbar toolbarPrivacyPolicy;
    private ImageView imageViewPrivacyPolicyBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);

        toolbarPrivacyPolicy = findViewById(R.id.toolbarPrivacyPolicy);
        imageViewPrivacyPolicyBack = findViewById(R.id.imageViewPrivacyPolicyBack);

        imageViewPrivacyPolicyBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(PrivacyPolicyActivity.this,MainActivity.class);
        startActivity(intent);
        CustomIntent.customType(PrivacyPolicyActivity.this,"right-to-left");
        finish();
    }
}