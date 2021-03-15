package com.furkanayaz.groupspad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import maes.tech.intentanim.CustomIntent;

public class NotificationsActivity extends AppCompatActivity {
    private Toolbar toolbarNotifications;
    private ImageView imageViewNotificationsBack;

    private RecyclerView recyclerViewNotifications;
    private NotificationAdapter notificationAdapter;
    private List<Notification> notificationList;

    private AdView adViewNotification;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        toolbarNotifications = findViewById(R.id.toolbarNotifications);
        imageViewNotificationsBack = findViewById(R.id.imageViewNotificationsBack);
        recyclerViewNotifications = findViewById(R.id.recyclerViewNotifications);

        adViewNotification = findViewById(R.id.adViewNotification);

        setSupportActionBar(toolbarNotifications);

        recyclerViewNotifications.setHasFixedSize(true);
        recyclerViewNotifications.setLayoutManager(new LinearLayoutManager(NotificationsActivity.this));

        notificationList = new ArrayList<>();

        MobileAds.initialize(NotificationsActivity.this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });

        AdRequest adRequest = new AdRequest.Builder().build();

        adViewNotification.loadAd(adRequest);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("notifications");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot d : snapshot.getChildren()){
                    Notification n = d.getValue(Notification.class);
                    Notification notification = new Notification(n.getNotificationtitle(),n.getNotificationdescription(),n.getNotificationdate());
                    notificationList.add(notification);
                }

                notificationAdapter = new NotificationAdapter(NotificationsActivity.this,notificationList);
                recyclerViewNotifications.setAdapter(notificationAdapter);
                notificationAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        imageViewNotificationsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(NotificationsActivity.this,MainActivity.class);
        startActivity(intent);
        CustomIntent.customType(NotificationsActivity.this,"fadein-to-fadeout");

    }
}