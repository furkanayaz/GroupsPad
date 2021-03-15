package com.furkanayaz.groupspad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import maes.tech.intentanim.CustomIntent;

public class AboutGroupsPadActivity extends AppCompatActivity {
    private Toolbar toolbarAboutGroupsPad;
    private ImageView imageViewAboutGroupsPadBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_groups_pad);

        toolbarAboutGroupsPad = findViewById(R.id.toolbarAboutGroupsPad);
        imageViewAboutGroupsPadBack = findViewById(R.id.imageViewAboutGroupsPadBack);

        imageViewAboutGroupsPadBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(AboutGroupsPadActivity.this,MainActivity.class);
        startActivity(intent);
        CustomIntent.customType(AboutGroupsPadActivity.this,"right-to-left");
        finish();
    }
}