package com.furkanayaz.groupspad;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import maes.tech.intentanim.CustomIntent;

public class FragmentExplore extends Fragment {
    private Toolbar toolbarMainActivity;
    private ImageView imageViewMainActivity;
    private ImageView imageViewNotifications;
    private RecyclerView recyclerViewExplore;

    private ArrayList<Group> groupArrayList;

    private TextView textViewExploreCantSee,textViewExploreSignUp,textViewExploreWelcome,textViewExploreNameLastname;
    private ImageView imageViewExploreSmiling;
    private ImageView imageViewExploreWavingHand;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore firebaseFirestore;

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_explore,container,false);

        toolbarMainActivity = view.findViewById(R.id.toolbarMainActivity);
        imageViewMainActivity = view.findViewById(R.id.imageViewMainActivity);
        imageViewNotifications = view.findViewById(R.id.imageViewNotifications);

        textViewExploreCantSee = view.findViewById(R.id.textViewExploreCantSee);
        textViewExploreSignUp = view.findViewById(R.id.textViewExploreSignUp);
        textViewExploreWelcome = view.findViewById(R.id.textViewExploreWelcome);
        textViewExploreNameLastname = view.findViewById(R.id.textViewExploreNameLastname);
        imageViewExploreSmiling = view.findViewById(R.id.imageViewExploreSmiling);
        imageViewExploreWavingHand = view.findViewById(R.id.imageViewExploreWavingHand);

        recyclerViewExplore = view.findViewById(R.id.recyclerViewExplore);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();

        checkInternetConnectionExplore();

        recyclerViewExplore.setHasFixedSize(true);
        recyclerViewExplore.setLayoutManager(new LinearLayoutManager(getContext()));

        groupArrayList = new ArrayList<>();

        if (firebaseUser != null){
            String uid = firebaseUser.getUid();
            firebaseFirestore.collection(uid).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()){
                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                            textViewExploreNameLastname.setText(documentSnapshot.get("namelastname").toString());

                            fetchExploreGroup();

                        }

                        GroupAdapter groupAdapter = new GroupAdapter(groupArrayList,getContext());
                        recyclerViewExplore.setAdapter(groupAdapter);
                        groupAdapter.notifyDataSetChanged();

                    }
                }
            });

            textViewExploreWelcome.setVisibility(View.VISIBLE);
            textViewExploreNameLastname.setVisibility(View.VISIBLE);
            textViewExploreCantSee.setVisibility(View.INVISIBLE);
            textViewExploreSignUp.setVisibility(View.INVISIBLE);
            imageViewExploreSmiling.setVisibility(View.INVISIBLE);
            imageViewExploreWavingHand.setVisibility(View.VISIBLE);
        }else {
            fetchExploreGroup();
            GroupAdapter groupAdapter = new GroupAdapter(groupArrayList,getContext());
            recyclerViewExplore.setAdapter(groupAdapter);
            groupAdapter.notifyDataSetChanged();
        }

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbarMainActivity);

        imageViewNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),NotificationsActivity.class);
                startActivity(intent);
                CustomIntent.customType(getContext(),"fadein-to-fadeout");
                getActivity().finish();
            }
        });

        textViewExploreSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),SignUpActivity.class);
                startActivity(intent);
                CustomIntent.customType(getContext(),"fadein-to-fadeout");
                getActivity().finish();
            }
        });





        return view;
    }

    private void checkInternetConnectionExplore() {
        ConnectivityManager connectivityManager = (ConnectivityManager)getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected() == true){

        }else if (textViewExploreCantSee.getText().toString().trim().contains("Seni")){
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

    private void fetchExploreGroup() {
        Group group = new Group("","","MOBİL UYGULAMA DESTEK","Yeni geliştiricilerin yayınlayacağı uygulamalarda Play Store veya IOS fark etmeksizin üst sıralara çıkması için destek verilecek tamamen gönüllülük amacıyla kurulmuş bir gruptur","https://t.me/joinchat/G5QOy9P10SCpvf79",String.valueOf(R.drawable.telegram),"2021-03-09 18:56:05","Türkiye");
        groupArrayList.add(group);

        Group group1 = new Group("","","Mobil Uygulama Geliştirme","Mobil Uygulama Geliştirme Yardımlaşma Grubu (Java & Kotlin & Flutter & Swift)","https://t.me/mobiluygulamagelistirme",String.valueOf(R.drawable.telegram),"2021-03-09 18:57:32","Türkiye");
        groupArrayList.add(group1);

        Group group2 = new Group("","","KPSS Soru Test Çöz","Dökümanlar, PDFler ve Arşivleri Ücretsiz Olarak Paylaşıyoruz","https://t.me/kpsstestler",String.valueOf(R.drawable.telegram),"2021-03-09 18:58:18","Türkiye");
        groupArrayList.add(group2);

        Group group3 = new Group("","","Matematik Soru Çözüm ve Ders Çalışma","Matematik Ders Çalışma ve Soru Çözüm Grubu","https://t.me/matematikderscalisma",String.valueOf(R.drawable.telegram),"2021-03-09 18:59:04","Türkiye");
        groupArrayList.add(group3);

        Group group4 = new Group("","","Matematik Soru Çözüm","Matematik Soru Çöz Telegram Grubu","https://t.me/matematiksorucoz",String.valueOf(R.drawable.telegram),"2021-03-09 18:59:45","Türkiye");
        groupArrayList.add(group4);
    }

}

