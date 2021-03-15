package com.furkanayaz.groupspad;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import maes.tech.intentanim.CustomIntent;

public class FragmentManageAccount extends Fragment {
    private TextView textView6,textViewAccountNameLastname,textViewAccountEmail;
    private CardView cardViewEditGroups,cardViewEditProfile,cardViewChangePassword,cardViewSignOut,cardViewOnGooglePlay,cardViewFeedBack,cardViewInstagram,cardViewFacebook,cardViewTwitter;
    private ImageView imageViewLogin,imageViewSignUp;
    private TextView textViewLogin,textViewSignUp;

    private SharedPreferences sharedPreferencesCountry;
    private SharedPreferences.Editor editorCountry;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore firebaseFirestore;

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_manageaccount,container,false);

        textView6 = view.findViewById(R.id.textView6);
        textViewAccountNameLastname = view.findViewById(R.id.textViewAccountNameLastname);
        textViewAccountEmail = view.findViewById(R.id.textViewAccountEmail);

        cardViewEditGroups = view.findViewById(R.id.cardViewEditGroups);
        cardViewEditProfile = view.findViewById(R.id.cardViewEditProfile);
        cardViewChangePassword = view.findViewById(R.id.cardViewChangePassword);
        cardViewSignOut = view.findViewById(R.id.cardViewSignOut);
        cardViewOnGooglePlay = view.findViewById(R.id.cardViewOnGooglePlay);
        cardViewFeedBack = view.findViewById(R.id.cardViewFeedBack);
        cardViewInstagram = view.findViewById(R.id.cardViewInstagram);
        cardViewFacebook = view.findViewById(R.id.cardViewFacebook);
        cardViewTwitter = view.findViewById(R.id.cardViewTwitter);

        imageViewLogin = view.findViewById(R.id.imageViewLogin);
        imageViewSignUp = view.findViewById(R.id.imageViewSignUp);

        textViewLogin = view.findViewById(R.id.textViewLogin);
        textViewSignUp = view.findViewById(R.id.textViewSignUp);

        checkInternetConnectionManageAccount();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();

        if (firebaseUser == null){
            textViewAccountNameLastname.setText("Anonim");
            textViewAccountEmail.setText("anonim.groupspad.com");
        }else {
            imageViewLogin.setImageResource(R.drawable.editprofile);
            imageViewSignUp.setImageResource(R.drawable.changepassword);
            textViewLogin.setText("Profilimi düzenle");
            textViewSignUp.setText("Şifremi değiştir");

            firebaseFirestore.collection(firebaseUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()){
                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                            textViewAccountNameLastname.setText(documentSnapshot.get("namelastname").toString());
                            textViewAccountEmail.setText(documentSnapshot.get("email").toString());
                        }
                    }
                }
            });

        }

        cardViewEditGroups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firebaseUser == null){
                    if (textView6.getText().toString().trim().contains("Hesabım")){
                        Snackbar.make(v,"Oluşturduğunuz grupları görebilmek için üye olmanız gerekmektedir",Snackbar.LENGTH_LONG).setActionTextColor(Color.WHITE).setAction("Üye Ol", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getContext(),SignUpActivity.class);
                                startActivity(intent);
                                CustomIntent.customType(getContext(),"fadein-to-fadeout");
                                getActivity().finish();
                            }
                        }).show();
                    }else {
                        Snackbar.make(v,"You must be a member to see the groups you have created.",Snackbar.LENGTH_LONG).setActionTextColor(Color.WHITE).setAction("Sign Up", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getContext(),SignUpActivity.class);
                                startActivity(intent);
                                CustomIntent.customType(getContext(),"fadein-to-fadeout");
                                getActivity().finish();
                            }
                        }).show();
                    }

                }else {
                    Intent intent = new Intent(getContext(),EditMyGroupsActivity.class);
                    startActivity(intent);
                    CustomIntent.customType(getContext(),"fadein-to-fadeout");
                    getActivity().finish();
                }
            }
        });

        cardViewEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (firebaseUser == null){
                    Intent intent = new Intent(getContext(),LoginActivity.class);
                    startActivity(intent);
                    CustomIntent.customType(getContext(),"left-to-right");
                    getActivity().finish();
                }else {
                    Intent intent = new Intent(getContext(),EditProfileActivity.class);
                    startActivity(intent);
                    CustomIntent.customType(getContext(),"left-to-right");
                    getActivity().finish();
                }
            }
        });

        cardViewChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (firebaseUser == null){
                    Intent intent = new Intent(getContext(),SignUpActivity.class);
                    startActivity(intent);
                    CustomIntent.customType(getContext(),"left-to-right");
                    getActivity().finish();
                }else {
                    Intent intent = new Intent(getContext(),ChangePasswordActivity.class);
                    startActivity(intent);
                    CustomIntent.customType(getContext(),"left-to-right");
                    getActivity().finish();
                }
            }
        });

        cardViewSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (firebaseUser == null){

                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("GROUPSPAD");
                    builder.setMessage("Uygulamadan çıkmak istediğinize emin misiniz ?");
                    builder.setPositiveButton("EVET", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            System.exit(1);
                        }
                    });
                    builder.setNegativeButton("HAYIR", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    builder.create().show();

                }else {

                    sharedPreferencesCountry = getContext().getSharedPreferences("country", Context.MODE_PRIVATE);
                    editorCountry = sharedPreferencesCountry.edit();
                    editorCountry.clear();
                    editorCountry.commit();

                    firebaseAuth.signOut();
                    Intent intent = new Intent(getContext(),MainActivity.class);
                    startActivity(intent);
                    CustomIntent.customType(getContext(),"fadein-to-fadeout");
                    getActivity().finish();
                }



            }
        });

        cardViewOnGooglePlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String appPackageName = getContext().getPackageName(); // getPackageName() from Context or Activity object
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
            }
        });

        cardViewFeedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),FeedBackActivity.class);
                startActivity(intent);
                CustomIntent.customType(getContext(),"left-to-right");
                getActivity().finish();
            }
        });

        cardViewInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.instagram.com/groupspad/";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        cardViewFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.facebook.com/groupspad/";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        cardViewTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://twitter.com/GroupsPad/";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });


        return view;
    }

    private void checkInternetConnectionManageAccount() {
        ConnectivityManager connectivityManager = (ConnectivityManager)getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected() == true){

        }else if (textView6.getText().toString().trim().contains("Hesabım")){
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
