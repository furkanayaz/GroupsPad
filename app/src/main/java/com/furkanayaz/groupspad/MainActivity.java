package com.furkanayaz.groupspad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private Fragment tempFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkInternetConnectionMain();

        bottomNavigationView = findViewById(R.id.bottomNavigationView);


        bottomNavigationView.setSelectedItemId(R.id.action_explore);

        getSupportFragmentManager().beginTransaction().add(R.id.fragmentHolder,new FragmentExplore()).commit();


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.action_account){
                    tempFragment = new FragmentManageAccount();
                }

                if (item.getItemId() == R.id.action_categories){
                    tempFragment = new FragmentCategories();
                }

                if (item.getItemId() == R.id.action_addgroup){
                    tempFragment = new FragmentAddGroup();
                }

                if (item.getItemId() == R.id.action_settings){
                    tempFragment = new FragmentSettings();
                }

                if (item.getItemId() == R.id.action_explore){
                    tempFragment = new FragmentExplore();
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentHolder,tempFragment).commit();

                return true;

            }
        });

    }

    public void checkInternetConnectionMain() {
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected() == true){
            /*//Create a snackbar
            View layout = findViewById(android.R.id.content);
            Snackbar snackbar = Snackbar.make(layout,"İnternet bağlı",Snackbar.LENGTH_LONG);
            snackbar.setDuration(5000);
            snackbar.show();*/

        }else {

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
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

        }
    }

}