package com.furkanayaz.groupspad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import maes.tech.intentanim.CustomIntent;

public class EditMyGroupsActivity extends AppCompatActivity {
    private ImageView imageViewEditMyGroups;
    private RecyclerView recyclerViewEditMyGroups;
    private ArrayList<Group> groupArrayList;
    private EditMyGroupsAdapter editMyGroupsAdapter;

    private AdView adViewEditMyGroups;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_my_groups);

        imageViewEditMyGroups = findViewById(R.id.imageViewEditMyGroups);
        recyclerViewEditMyGroups = findViewById(R.id.recyclerViewEditMyGroups);

        adViewEditMyGroups = findViewById(R.id.adViewEditMyGroups);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        MobileAds.initialize(EditMyGroupsActivity.this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });

        AdRequest adRequest = new AdRequest.Builder().build();

        adViewEditMyGroups.loadAd(adRequest);

        recyclerViewEditMyGroups.setHasFixedSize(true);
        recyclerViewEditMyGroups.setLayoutManager(new LinearLayoutManager(EditMyGroupsActivity.this));

        groupArrayList = new ArrayList<>();

        //Group group = new Group("0","0","Deneme","Deneme","Deneme",String.valueOf(R.drawable.whatsapp),"Nothing","Türkiye");
        //groupArrayList.add(group);

        fetchMyGroups(firebaseUser.getUid());

        editMyGroupsAdapter = new EditMyGroupsAdapter(EditMyGroupsActivity.this,groupArrayList);

        editMyGroupsAdapter.notifyDataSetChanged();

        recyclerViewEditMyGroups.setAdapter(editMyGroupsAdapter);

        imageViewEditMyGroups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void fetchMyGroups(String uid) {
        String url = "https://frknnyz.tk/groupspad/groups_by_uid.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("groups");
                    for (int i = 0;i<jsonArray.length();i++){
                        JSONObject g = jsonArray.getJSONObject(i);
                        String id = g.getString("id");
                        String uid = g.getString("uid");
                        String groupcategory = g.getString("groupcategory");
                        String groupcountry = g.getString("groupcountry");
                        String groupname = g.getString("groupname");
                        String groupdescription = g.getString("groupdescription");
                        String groupuri = g.getString("groupuri");
                        String groupdate = g.getString("groupdate");

                        if (groupuri.contains("https://t.me")){

                            if (groupcountry.equals("0")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"Türkiye");
                                groupArrayList.add(group);
                            }
                            if (groupcountry.equals("1")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"Azerbaycan");
                                groupArrayList.add(group);
                            }
                            if (groupcountry.equals("2")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"Amerika");
                                groupArrayList.add(group);
                            }
                            if (groupcountry.equals("3")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"Ingiltere");
                                groupArrayList.add(group);
                            }
                            if (groupcountry.equals("4")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"Hindistan");
                                groupArrayList.add(group);
                            }
                            if (groupcountry.equals("5")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"Almanya");
                                groupArrayList.add(group);
                            }
                            if (groupcountry.equals("6")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"Fransa");
                                groupArrayList.add(group);
                            }
                            if (groupcountry.equals("7")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"Avustralya");
                                groupArrayList.add(group);
                            }
                            if (groupcountry.equals("8")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"Kanada");
                                groupArrayList.add(group);
                            }
                            if (groupcountry.equals("9")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"Yeni Zelanda");
                                groupArrayList.add(group);
                            }
                            if (groupcountry.equals("10")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"Filipinler");
                                groupArrayList.add(group);
                            }
                            if (groupcountry.equals("11")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"Uganda");
                                groupArrayList.add(group);
                            }
                            if (groupcountry.equals("12")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"Pakistan");
                                groupArrayList.add(group);
                            }
                            if (groupcountry.equals("13")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"Nijerya");
                                groupArrayList.add(group);
                            }
                            if (groupcountry.equals("14")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"Singapur");
                                groupArrayList.add(group);
                            }
                            if (groupcountry.equals("15")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"Irlanda");
                                groupArrayList.add(group);
                            }
                            if (groupcountry.equals("16")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"Guney Afrika");
                                groupArrayList.add(group);
                            }
                            if (groupcountry.equals("17")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"Dominik");
                                groupArrayList.add(group);
                            }
                            if (groupcountry.equals("18")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"Sudan");
                                groupArrayList.add(group);
                            }
                            if (groupcountry.equals("19")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"Madagaskar");
                                groupArrayList.add(group);
                            }
                            if (groupcountry.equals("20")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"Kenya");
                                groupArrayList.add(group);
                            }
                            if (groupcountry.equals("21")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"Uganda");
                                groupArrayList.add(group);
                            }




                        }

                        if (groupuri.contains("https://chat.whatsapp.com")){

                            if (groupcountry.equals("0")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"Türkiye");
                                groupArrayList.add(group);
                            }
                            if (groupcountry.equals("1")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"Azerbaycan");
                                groupArrayList.add(group);
                            }
                            if (groupcountry.equals("2")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"Amerika");
                                groupArrayList.add(group);
                            }
                            if (groupcountry.equals("3")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"Ingiltere");
                                groupArrayList.add(group);
                            }
                            if (groupcountry.equals("4")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"Hindistan");
                                groupArrayList.add(group);
                            }
                            if (groupcountry.equals("5")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"Almanya");
                                groupArrayList.add(group);
                            }
                            if (groupcountry.equals("6")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"Fransa");
                                groupArrayList.add(group);
                            }
                            if (groupcountry.equals("7")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"Avustralya");
                                groupArrayList.add(group);
                            }
                            if (groupcountry.equals("8")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"Kanada");
                                groupArrayList.add(group);
                            }
                            if (groupcountry.equals("9")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"Yeni Zelanda");
                                groupArrayList.add(group);
                            }
                            if (groupcountry.equals("10")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"Filipinler");
                                groupArrayList.add(group);
                            }
                            if (groupcountry.equals("11")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"Uganda");
                                groupArrayList.add(group);
                            }
                            if (groupcountry.equals("12")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"Pakistan");
                                groupArrayList.add(group);
                            }
                            if (groupcountry.equals("13")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"Nijerya");
                                groupArrayList.add(group);
                            }
                            if (groupcountry.equals("14")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"Singapur");
                                groupArrayList.add(group);
                            }
                            if (groupcountry.equals("15")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"Irlanda");
                                groupArrayList.add(group);
                            }
                            if (groupcountry.equals("16")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"Guney Afrika");
                                groupArrayList.add(group);
                            }
                            if (groupcountry.equals("17")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"Dominik");
                                groupArrayList.add(group);
                            }
                            if (groupcountry.equals("18")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"Sudan");
                                groupArrayList.add(group);
                            }
                            if (groupcountry.equals("19")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"Madagaskar");
                                groupArrayList.add(group);
                            }
                            if (groupcountry.equals("20")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"Kenya");
                                groupArrayList.add(group);
                            }
                            if (groupcountry.equals("21")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"Uganda");
                                groupArrayList.add(group);
                            }

                        }

                        if (groupuri.contains("https://groups.bip.ai")){

                            if (groupcountry.equals("0")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"Türkiye");
                                groupArrayList.add(group);
                            }
                            if (groupcountry.equals("1")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"Azerbaycan");
                                groupArrayList.add(group);
                            }
                            if (groupcountry.equals("2")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"Amerika");
                                groupArrayList.add(group);
                            }
                            if (groupcountry.equals("3")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"Ingiltere");
                                groupArrayList.add(group);
                            }
                            if (groupcountry.equals("4")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"Hindistan");
                                groupArrayList.add(group);
                            }
                            if (groupcountry.equals("5")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"Almanya");
                                groupArrayList.add(group);
                            }
                            if (groupcountry.equals("6")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"Fransa");
                                groupArrayList.add(group);
                            }
                            if (groupcountry.equals("7")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"Avustralya");
                                groupArrayList.add(group);
                            }
                            if (groupcountry.equals("8")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"Kanada");
                                groupArrayList.add(group);
                            }
                            if (groupcountry.equals("9")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"Yeni Zelanda");
                                groupArrayList.add(group);
                            }
                            if (groupcountry.equals("10")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"Filipinler");
                                groupArrayList.add(group);
                            }
                            if (groupcountry.equals("11")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"Uganda");
                                groupArrayList.add(group);
                            }
                            if (groupcountry.equals("12")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"Pakistan");
                                groupArrayList.add(group);
                            }
                            if (groupcountry.equals("13")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"Nijerya");
                                groupArrayList.add(group);
                            }
                            if (groupcountry.equals("14")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"Singapur");
                                groupArrayList.add(group);
                            }
                            if (groupcountry.equals("15")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"Irlanda");
                                groupArrayList.add(group);
                            }
                            if (groupcountry.equals("16")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"Guney Afrika");
                                groupArrayList.add(group);
                            }
                            if (groupcountry.equals("17")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"Dominik");
                                groupArrayList.add(group);
                            }
                            if (groupcountry.equals("18")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"Sudan");
                                groupArrayList.add(group);
                            }
                            if (groupcountry.equals("19")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"Madagaskar");
                                groupArrayList.add(group);
                            }
                            if (groupcountry.equals("20")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"Kenya");
                                groupArrayList.add(group);
                            }
                            if (groupcountry.equals("21")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"Uganda");
                                groupArrayList.add(group);
                            }
                        }

                        if (groupuri.contains("https://signal.goup")){

                            if (groupcountry.equals("0")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"Türkiye");
                                groupArrayList.add(group);
                            }
                            if (groupcountry.equals("1")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"Azerbaycan");
                                groupArrayList.add(group);
                            }
                            if (groupcountry.equals("2")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"Amerika");
                                groupArrayList.add(group);
                            }
                            if (groupcountry.equals("3")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"Ingiltere");
                                groupArrayList.add(group);
                            }
                            if (groupcountry.equals("4")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"Hindistan");
                                groupArrayList.add(group);
                            }
                            if (groupcountry.equals("5")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"Almanya");
                                groupArrayList.add(group);
                            }
                            if (groupcountry.equals("6")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"Fransa");
                                groupArrayList.add(group);
                            }
                            if (groupcountry.equals("7")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"Avustralya");
                                groupArrayList.add(group);
                            }
                            if (groupcountry.equals("8")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"Kanada");
                                groupArrayList.add(group);
                            }
                            if (groupcountry.equals("9")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"Yeni Zelanda");
                                groupArrayList.add(group);
                            }
                            if (groupcountry.equals("10")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"Filipinler");
                                groupArrayList.add(group);
                            }
                            if (groupcountry.equals("11")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"Uganda");
                                groupArrayList.add(group);
                            }
                            if (groupcountry.equals("12")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"Pakistan");
                                groupArrayList.add(group);
                            }
                            if (groupcountry.equals("13")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"Nijerya");
                                groupArrayList.add(group);
                            }
                            if (groupcountry.equals("14")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"Singapur");
                                groupArrayList.add(group);
                            }
                            if (groupcountry.equals("15")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"Irlanda");
                                groupArrayList.add(group);
                            }
                            if (groupcountry.equals("16")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"Guney Afrika");
                                groupArrayList.add(group);
                            }
                            if (groupcountry.equals("17")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"Dominik");
                                groupArrayList.add(group);
                            }
                            if (groupcountry.equals("18")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"Sudan");
                                groupArrayList.add(group);
                            }
                            if (groupcountry.equals("19")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"Madagaskar");
                                groupArrayList.add(group);
                            }
                            if (groupcountry.equals("20")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"Kenya");
                                groupArrayList.add(group);
                            }
                            if (groupcountry.equals("21")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"Uganda");
                                groupArrayList.add(group);
                            }
                        }

                    }

                    editMyGroupsAdapter = new EditMyGroupsAdapter(EditMyGroupsActivity.this,groupArrayList);
                    recyclerViewEditMyGroups.setAdapter(editMyGroupsAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("uid",uid);
                return params;
            }
        };

        Volley.newRequestQueue(EditMyGroupsActivity.this).add(stringRequest);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(EditMyGroupsActivity.this,MainActivity.class);
        startActivity(intent);
        CustomIntent.customType(EditMyGroupsActivity.this,"right-to-left");
        finish();
    }

}