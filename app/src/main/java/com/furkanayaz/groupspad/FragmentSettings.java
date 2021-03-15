package com.furkanayaz.groupspad;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import maes.tech.intentanim.CustomIntent;

public class FragmentSettings extends Fragment {
    private TextView textView;
    private CardView cardViewVersion,cardViewUserAgreement,cardViewPrivacyPolicy,cardViewAboutGroupsPad;
    private Switch switchNotifications,switchLocation;

    private SharedPreferences sharedPreferencesNotifications;
    private SharedPreferences.Editor editorNotifications;
    private SharedPreferences sharedPreferencesLocation;
    private SharedPreferences.Editor editorLocation;

    private AdView adViewSettings;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    private int notifications;
    private int location;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_settings,container,false);

        textView = view.findViewById(R.id.textView);
        cardViewVersion = view.findViewById(R.id.cardViewVersion);
        cardViewUserAgreement = view.findViewById(R.id.cardViewUserAgreement);
        cardViewPrivacyPolicy = view.findViewById(R.id.cardViewPrivacyPolicy);
        cardViewAboutGroupsPad = view.findViewById(R.id.cardViewAboutGroupsPad);

        switchNotifications = view.findViewById(R.id.switchNotifications);
        switchLocation = view.findViewById(R.id.switchLocation);

        adViewSettings = view.findViewById(R.id.adViewSettings);

        checkInternetConnectionSettings();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();




        MobileAds.initialize(getContext(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });

        AdRequest adRequest = new AdRequest.Builder().build();
        adViewSettings.loadAd(adRequest);








        sharedPreferencesNotifications = getContext().getSharedPreferences("notificationstatus", Context.MODE_PRIVATE);
        editorNotifications = sharedPreferencesNotifications.edit();

        sharedPreferencesLocation = getContext().getSharedPreferences("locationstatus", Context.MODE_PRIVATE);
        editorLocation = sharedPreferencesLocation.edit();

        notifications = sharedPreferencesNotifications.getInt("notifications",0);
        location = sharedPreferencesLocation.getInt("location",0);

        if (firebaseUser == null){
            switchLocation.setVisibility(View.INVISIBLE);
        }

        if (notifications == 0){
            switchNotifications.setChecked(true);
        }else {
            switchNotifications.setChecked(false);
        }

        if (location == 0){
            switchLocation.setChecked(true);
        }else {
            switchLocation.setChecked(false);
        }

        cardViewUserAgreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),UserAgreementActivity.class);
                startActivity(intent);
                CustomIntent.customType(getContext(),"left-to-right");
                getActivity().finish();
            }
        });

        cardViewPrivacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),PrivacyPolicyActivity.class);
                startActivity(intent);
                CustomIntent.customType(getContext(),"left-to-right");
                getActivity().finish();
            }
        });

        cardViewAboutGroupsPad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),AboutGroupsPadActivity.class);
                startActivity(intent);
                CustomIntent.customType(getContext(),"left-to-right");
                getActivity().finish();
            }
        });


        switchNotifications.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    editorNotifications.clear();
                    editorNotifications.putInt("notifications",0);
                    editorNotifications.commit();
                }else {
                    editorNotifications.clear();
                    editorNotifications.putInt("notifications",1);
                    editorNotifications.commit();
                }
            }
        });

        switchLocation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    editorLocation.clear();
                    editorLocation.putInt("location",0);
                    editorLocation.commit();
                }else {
                    editorLocation.clear();
                    editorLocation.putInt("location",1);
                    editorLocation.commit();
                }
            }
        });



        return view;
    }

    private void checkInternetConnectionSettings() {
        ConnectivityManager connectivityManager = (ConnectivityManager)getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected() == true){

        }else if (textView.getText().toString().trim().contains("Uygulama")){
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("İNTERNET BAĞLANTISI");
            builder.setMessage("Daha iyi performans için lütfen internet bağlantınızı kontrol ediniz");
            builder.setCancelable(false);
            builder.setPositiveButton("TAMAM", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //System.exit(0);
                }
            });
            builder.create().show();
        }else {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("INTERNET CONNECTION");
            builder.setMessage("Please check your internet connection for better performance.");
            builder.setCancelable(false);
            builder.setPositiveButton("OKAY", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //System.exit(0);
                }
            });
            builder.create().show();
        }
    }


}
