package com.furkanayaz.groupspad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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

import maes.tech.intentanim.CustomIntent;

public class SubCategoryGroupsActivity extends AppCompatActivity {
    private Toolbar toolbarSubCategoryGroups;
    private ImageView imageViewBackSubCategoryGroups;
    private TextView textViewSubCategoryGroupsName,textViewSubCategoryGroupsDescription,textViewSubCategoryGroupsCategoryName;
    private RecyclerView recyclerViewSubCategoryGroups;
    private GroupAdapter adapter;
    private ArrayList<Group> subCategoryGroups;
    private Category category;

    private SharedPreferences sharedPreferencesLocation;
    private SharedPreferences sharedPreferencesCountry;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category_groups);

        toolbarSubCategoryGroups = findViewById(R.id.toolbarSubCategoryGroups);
        imageViewBackSubCategoryGroups = findViewById(R.id.imageViewBackSubCategoryGroups);
        textViewSubCategoryGroupsName = findViewById(R.id.textViewSubCategoryGroupsName);
        textViewSubCategoryGroupsDescription = findViewById(R.id.textViewSubCategoryGroupsDescription);
        textViewSubCategoryGroupsCategoryName = findViewById(R.id.textViewSubCategoryGroupsCategoryName);

        setSupportActionBar(toolbarSubCategoryGroups);

        recyclerViewSubCategoryGroups = findViewById(R.id.recyclerViewSubCategoryGroups);

        recyclerViewSubCategoryGroups.setHasFixedSize(true);

        recyclerViewSubCategoryGroups.setLayoutManager(new LinearLayoutManager(SubCategoryGroupsActivity.this));

        subCategoryGroups = new ArrayList<>();

        category = (Category) getIntent().getSerializableExtra("categoryobject");

        sharedPreferencesCountry = getSharedPreferences("country",MODE_PRIVATE);
        String country = sharedPreferencesCountry.getString("country","allcountry");
        //Default Türkiye gelirse Hindistanlı bir kullanıcı uygulamaya oturum açmadan girdiğinde kendi ülkesindeki grupları göremez. Bu nedenle default allcountry olması daha uygun olur.
        //all country olunca firebaseAuth null ise sadece kendi ülkendeki grupları gör seçeneği işaretlenmesin. O kısıma dikkat et. Null kullanıcı o seçeneği işaretleyemez.
        sharedPreferencesLocation = getSharedPreferences("locationstatus", Context.MODE_PRIVATE);

        int location = sharedPreferencesLocation.getInt("location",1);

        if (location == 1){ //Üye olan kullanıcı için lokasyon switch'nin açık olup olmadığını kontrol eder ve ona göre veri gönderir. Üye olmayan için ise switch ayarlarda gözükmediği için 1 döndürür ve bu halükarda da zaten tüm grupları getirmiş olur.
            fetchAllGroups(category.getId());
        }


        if (location == 0){
            if (country.equals("Turkey")){
                int countrynumber = 0;
                fetchGroups(category.getId(),countrynumber);
            }
            if (country.equals("Azerbaijan")){
                int countrynumber = 1;
                fetchGroups(category.getId(),countrynumber);
            }
            if (country.equals("United States of America")){
                int countrynumber = 2;
                fetchGroups(category.getId(),countrynumber);
            }
            if (country.equals("United Kingdom")){
                int countrynumber = 3;
                fetchGroups(category.getId(),countrynumber);
            }
            if (country.equals("India")){
                int countrynumber = 4;
                fetchGroups(category.getId(),countrynumber);
            }
            if (country.equals("Germany")){
                int countrynumber = 5;
                fetchGroups(category.getId(),countrynumber);
            }
            if (country.equals("France")){
                int countrynumber = 6;
                fetchGroups(category.getId(),countrynumber);
            }
            if (country.equals("Australia")){
                int countrynumber = 7;
                fetchGroups(category.getId(),countrynumber);
            }
            if (country.equals("Canada")){
                int countrynumber = 8;
                fetchGroups(category.getId(),countrynumber);
            }
            if (country.equals("New Zealand")){
                int countrynumber = 9;
                fetchGroups(category.getId(),countrynumber);
            }
            if (country.equals("Philippines")){
                int countrynumber = 10;
                fetchGroups(category.getId(),countrynumber);
            }
            if (country.equals("Uganda")){
                int countrynumber = 11;
                fetchGroups(category.getId(),countrynumber);
            }
            if (country.equals("Pakistan")){
                int countrynumber = 12;
                fetchGroups(category.getId(),countrynumber);
            }
            if (country.equals("Nigeria")){
                int countrynumber = 13;
                fetchGroups(category.getId(),countrynumber);
            }
            if (country.equals("Singapore")){
                int countrynumber = 14;
                fetchGroups(category.getId(),countrynumber);
            }
            if (country.equals("Ireland")){
                int countrynumber = 15;
                fetchGroups(category.getId(),countrynumber);
            }
            if (country.equals("South Africa")){
                int countrynumber = 16;
                fetchGroups(category.getId(),countrynumber);
            }
            if (country.equals("Dominicia")){
                int countrynumber = 17;
                fetchGroups(category.getId(),countrynumber);
            }
            if (country.equals("Sudan")){
                int countrynumber = 18;
                fetchGroups(category.getId(),countrynumber);
            }
            if (country.equals("Madagascar")){
                int countrynumber = 19;
                fetchGroups(category.getId(),countrynumber);
            }
            if (country.equals("Kenya")){
                int countrynumber = 20;
                fetchGroups(category.getId(),countrynumber);
            }
        }


        textViewSubCategoryGroupsName.setText(category.getCategoryname());

        textViewSubCategoryGroupsCategoryName.setText(category.getCategoryname()+" ");

        imageViewBackSubCategoryGroups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void fetchAllGroups(String id) {
        String url = "https://frknnyz.tk/groupspad/groups_by_category_id_allcountry.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Log.e("Response: ",response);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray groups = jsonObject.getJSONArray("groups");

                    for (int i = 0;i<groups.length();i++){
                        JSONObject g = groups.getJSONObject(i);

                        String id = g.getString("id");
                        String uid = g.getString("uid");
                        String groupcategory = g.getString("groupcategory");
                        String groupcountry = g.getString("groupcountry");
                        String groupname = g.getString("groupname");
                        String groupdescription = g.getString("groupdescription");
                        String groupuri = g.getString("groupuri");
                        String groupdate = g.getString("groupdate");


                        if (groupuri.contains("https://t.me")){

                            if (textViewSubCategoryGroupsDescription.getText().toString().trim().contains("kategorisi")){
                                if (groupcountry.equals("0")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"Türkiye");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("1")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"Azerbaycan");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("2")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"Amerika");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("3")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"İngiltere");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("4")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"Hindistan");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("5")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"Almanya");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("6")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"Fransa");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("7")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"Avustralya");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("8")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"Kanada");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("9")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"Yeni Zelanda");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("10")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"Filipinler");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("11")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"Uganda");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("12")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"Pakistan");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("13")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"Nijerya");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("14")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"Singapur");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("15")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"İrlanda");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("16")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"Güney Afrika");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("17")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"Dominik");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("18")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"Sudan");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("19")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"Madagaskar");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("20")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"Kenya");
                                    subCategoryGroups.add(group);
                                }
                                /*if (groupcountry.equals("21")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"Uganda");
                                    subCategoryGroups.add(group);
                                }*/
                            }else {
                                if (groupcountry.equals("0")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"Turkey");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("1")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"Azerbaijan");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("2")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"USA");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("3")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"United Kingdom");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("4")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"India");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("5")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"Germany");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("6")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"France");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("7")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"Australia");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("8")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"Canada");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("9")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"New Zealand");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("10")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"Philippines");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("11")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"Uganda");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("12")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"Pakistan");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("13")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"Nigeria");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("14")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"Singapore");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("15")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"Ireland");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("16")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"South Africa");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("17")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"Dominicia");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("18")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"Sudan");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("19")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"Madagascar");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("20")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"Kenya");
                                    subCategoryGroups.add(group);
                                }
                                /*if (groupcountry.equals("21")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"Uganda");
                                    subCategoryGroups.add(group);
                                }*/
                            }


                        }

                        if (groupuri.contains("https://chat.whatsapp.com")){

                            if (textViewSubCategoryGroupsDescription.getText().toString().trim().contains("kategorisi")){
                                if (groupcountry.equals("0")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"Türkiye");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("1")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"Azerbaycan");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("2")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"Amerika");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("3")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"Ingiltere");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("4")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"Hindistan");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("5")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"Almanya");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("6")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"Fransa");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("7")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"Avustralya");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("8")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"Kanada");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("9")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"Yeni Zelanda");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("10")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"Filipinler");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("11")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"Uganda");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("12")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"Pakistan");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("13")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"Nijerya");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("14")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"Singapur");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("15")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"Irlanda");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("16")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"Guney Afrika");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("17")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"Dominik");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("18")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"Sudan");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("19")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"Madagaskar");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("20")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"Kenya");
                                    subCategoryGroups.add(group);
                                }
                                /*if (groupcountry.equals("21")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"Uganda");
                                    subCategoryGroups.add(group);
                                }*/
                            }else {
                                if (groupcountry.equals("0")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"Turkey");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("1")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"Azerbaijan");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("2")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"USA");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("3")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"United Kingdom");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("4")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"India");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("5")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"Germany");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("6")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"France");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("7")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"Australia");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("8")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"Canada");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("9")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"New Zealand");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("10")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"Philippines");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("11")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"Uganda");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("12")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"Pakistan");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("13")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"Nigeria");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("14")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"Singapore");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("15")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"Ireland");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("16")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"South Africa");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("17")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"Dominicia");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("18")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"Sudan");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("19")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"Madagascar");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("20")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"Kenya");
                                    subCategoryGroups.add(group);
                                }
                                /*if (groupcountry.equals("21")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"Uganda");
                                    subCategoryGroups.add(group);
                                }*/
                            }

                        }

                        if (groupuri.contains("https://groups.bip.ai")){

                            if (textViewSubCategoryGroupsDescription.getText().toString().trim().contains("kategorisi")){
                                if (groupcountry.equals("0")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"Türkiye");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("1")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"Azerbaycan");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("2")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"Amerika");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("3")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"Ingiltere");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("4")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"Hindistan");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("5")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"Almanya");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("6")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"Fransa");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("7")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"Avustralya");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("8")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"Kanada");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("9")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"Yeni Zelanda");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("10")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"Filipinler");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("11")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"Uganda");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("12")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"Pakistan");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("13")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"Nijerya");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("14")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"Singapur");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("15")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"Irlanda");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("16")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"Guney Afrika");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("17")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"Dominik");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("18")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"Sudan");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("19")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"Madagaskar");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("20")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"Kenya");
                                    subCategoryGroups.add(group);
                                }
                                /*if (groupcountry.equals("21")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"Uganda");
                                    subCategoryGroups.add(group);
                                }*/
                            }else {
                                if (groupcountry.equals("0")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"Turkey");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("1")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"Azerbaijan");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("2")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"USA");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("3")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"United Kingdom");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("4")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"India");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("5")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"Germany");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("6")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"France");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("7")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"Australia");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("8")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"Canada");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("9")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"New Zealand");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("10")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"Philippines");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("11")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"Uganda");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("12")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"Pakistan");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("13")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"Nigeria");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("14")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"Singapore");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("15")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"Ireland");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("16")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"South Africa");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("17")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"Dominicia");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("18")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"Sudan");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("19")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"Madagascar");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("20")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"Kenya");
                                    subCategoryGroups.add(group);
                                }
                                /*if (groupcountry.equals("21")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"Uganda");
                                    subCategoryGroups.add(group);
                                }*/
                            }

                        }

                        if (groupuri.contains("https://signal.goup")){

                            if (textViewSubCategoryGroupsDescription.getText().toString().trim().contains("kategorisi")){
                                if (groupcountry.equals("0")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"Türkiye");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("1")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"Azerbaycan");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("2")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"Amerika");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("3")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"Ingiltere");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("4")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"Hindistan");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("5")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"Almanya");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("6")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"Fransa");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("7")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"Avustralya");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("8")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"Kanada");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("9")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"Yeni Zelanda");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("10")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"Filipinler");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("11")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"Uganda");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("12")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"Pakistan");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("13")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"Nijerya");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("14")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"Singapur");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("15")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"Irlanda");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("16")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"Guney Afrika");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("17")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"Dominik");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("18")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"Sudan");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("19")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"Madagaskar");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("20")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"Kenya");
                                    subCategoryGroups.add(group);
                                }
                                /*if (groupcountry.equals("21")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"Uganda");
                                    subCategoryGroups.add(group);
                                }*/
                            }else {
                                if (groupcountry.equals("0")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"Turkey");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("1")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"Azerbaijan");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("2")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"USA");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("3")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"United Kingdom");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("4")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"India");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("5")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"Germany");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("6")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"France");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("7")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"Australia");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("8")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"Canada");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("9")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"New Zealand");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("10")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"Philippines");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("11")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"Uganda");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("12")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"Pakistan");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("13")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"Nigeria");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("14")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"Singapore");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("15")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"Ireland");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("16")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"South Africa");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("17")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"Dominicia");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("18")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"Sudan");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("19")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"Madagascar");
                                    subCategoryGroups.add(group);
                                }
                                if (groupcountry.equals("20")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"Kenya");
                                    subCategoryGroups.add(group);
                                }
                                /*if (groupcountry.equals("21")){
                                    Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"Uganda");
                                    subCategoryGroups.add(group);
                                }*/
                            }

                        }

                    }

                    adapter = new GroupAdapter(subCategoryGroups,SubCategoryGroupsActivity.this);
                    recyclerViewSubCategoryGroups.setAdapter(adapter);

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

                Map<String, String> params = new HashMap<>();

                params.put("id",id);

                return params;
            }
        };

        Volley.newRequestQueue(SubCategoryGroupsActivity.this).add(stringRequest);
    }


    private void fetchGroups(String id,int countrynumber) {
        String url = "https://frknnyz.tk/groupspad/groups_by_category_id.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Log.e("Response: ",response);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray groups = jsonObject.getJSONArray("groups");

                    for (int i = 0;i<groups.length();i++){
                        JSONObject g = groups.getJSONObject(i);

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
                                subCategoryGroups.add(group);
                            }
                            if (groupcountry.equals("1")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"Azerbaycan");
                                subCategoryGroups.add(group);
                            }
                            if (groupcountry.equals("2")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"Amerika");
                                subCategoryGroups.add(group);
                            }
                            if (groupcountry.equals("3")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"Ingiltere");
                                subCategoryGroups.add(group);
                            }
                            if (groupcountry.equals("4")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"Hindistan");
                                subCategoryGroups.add(group);
                            }
                            if (groupcountry.equals("5")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"Almanya");
                                subCategoryGroups.add(group);
                            }
                            if (groupcountry.equals("6")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"Fransa");
                                subCategoryGroups.add(group);
                            }
                            if (groupcountry.equals("7")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"Avustralya");
                                subCategoryGroups.add(group);
                            }
                            if (groupcountry.equals("8")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"Kanada");
                                subCategoryGroups.add(group);
                            }
                            if (groupcountry.equals("9")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"Yeni Zelanda");
                                subCategoryGroups.add(group);
                            }
                            if (groupcountry.equals("10")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"Filipinler");
                                subCategoryGroups.add(group);
                            }
                            if (groupcountry.equals("11")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"Uganda");
                                subCategoryGroups.add(group);
                            }
                            if (groupcountry.equals("12")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"Pakistan");
                                subCategoryGroups.add(group);
                            }
                            if (groupcountry.equals("13")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"Nijerya");
                                subCategoryGroups.add(group);
                            }
                            if (groupcountry.equals("14")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"Singapur");
                                subCategoryGroups.add(group);
                            }
                            if (groupcountry.equals("15")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"Irlanda");
                                subCategoryGroups.add(group);
                            }
                            if (groupcountry.equals("16")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"Guney Afrika");
                                subCategoryGroups.add(group);
                            }
                            if (groupcountry.equals("17")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"Dominik");
                                subCategoryGroups.add(group);
                            }
                            if (groupcountry.equals("18")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"Sudan");
                                subCategoryGroups.add(group);
                            }
                            if (groupcountry.equals("19")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"Madagaskar");
                                subCategoryGroups.add(group);
                            }
                            if (groupcountry.equals("20")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"Kenya");
                                subCategoryGroups.add(group);
                            }
                            if (groupcountry.equals("21")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.telegram),groupdate,"Uganda");
                                subCategoryGroups.add(group);
                            }




                        }

                        if (groupuri.contains("https://chat.whatsapp.com")){

                            if (groupcountry.equals("0")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"Türkiye");
                                subCategoryGroups.add(group);
                            }
                            if (groupcountry.equals("1")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"Azerbaycan");
                                subCategoryGroups.add(group);
                            }
                            if (groupcountry.equals("2")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"Amerika");
                                subCategoryGroups.add(group);
                            }
                            if (groupcountry.equals("3")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"Ingiltere");
                                subCategoryGroups.add(group);
                            }
                            if (groupcountry.equals("4")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"Hindistan");
                                subCategoryGroups.add(group);
                            }
                            if (groupcountry.equals("5")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"Almanya");
                                subCategoryGroups.add(group);
                            }
                            if (groupcountry.equals("6")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"Fransa");
                                subCategoryGroups.add(group);
                            }
                            if (groupcountry.equals("7")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"Avustralya");
                                subCategoryGroups.add(group);
                            }
                            if (groupcountry.equals("8")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"Kanada");
                                subCategoryGroups.add(group);
                            }
                            if (groupcountry.equals("9")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"Yeni Zelanda");
                                subCategoryGroups.add(group);
                            }
                            if (groupcountry.equals("10")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"Filipinler");
                                subCategoryGroups.add(group);
                            }
                            if (groupcountry.equals("11")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"Uganda");
                                subCategoryGroups.add(group);
                            }
                            if (groupcountry.equals("12")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"Pakistan");
                                subCategoryGroups.add(group);
                            }
                            if (groupcountry.equals("13")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"Nijerya");
                                subCategoryGroups.add(group);
                            }
                            if (groupcountry.equals("14")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"Singapur");
                                subCategoryGroups.add(group);
                            }
                            if (groupcountry.equals("15")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"Irlanda");
                                subCategoryGroups.add(group);
                            }
                            if (groupcountry.equals("16")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"Guney Afrika");
                                subCategoryGroups.add(group);
                            }
                            if (groupcountry.equals("17")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"Dominik");
                                subCategoryGroups.add(group);
                            }
                            if (groupcountry.equals("18")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"Sudan");
                                subCategoryGroups.add(group);
                            }
                            if (groupcountry.equals("19")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"Madagaskar");
                                subCategoryGroups.add(group);
                            }
                            if (groupcountry.equals("20")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"Kenya");
                                subCategoryGroups.add(group);
                            }
                            if (groupcountry.equals("21")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.whatsapp),groupdate,"Uganda");
                                subCategoryGroups.add(group);
                            }

                        }

                        if (groupuri.contains("https://groups.bip.ai")){

                            if (groupcountry.equals("0")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"Türkiye");
                                subCategoryGroups.add(group);
                            }
                            if (groupcountry.equals("1")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"Azerbaycan");
                                subCategoryGroups.add(group);
                            }
                            if (groupcountry.equals("2")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"Amerika");
                                subCategoryGroups.add(group);
                            }
                            if (groupcountry.equals("3")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"Ingiltere");
                                subCategoryGroups.add(group);
                            }
                            if (groupcountry.equals("4")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"Hindistan");
                                subCategoryGroups.add(group);
                            }
                            if (groupcountry.equals("5")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"Almanya");
                                subCategoryGroups.add(group);
                            }
                            if (groupcountry.equals("6")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"Fransa");
                                subCategoryGroups.add(group);
                            }
                            if (groupcountry.equals("7")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"Avustralya");
                                subCategoryGroups.add(group);
                            }
                            if (groupcountry.equals("8")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"Kanada");
                                subCategoryGroups.add(group);
                            }
                            if (groupcountry.equals("9")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"Yeni Zelanda");
                                subCategoryGroups.add(group);
                            }
                            if (groupcountry.equals("10")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"Filipinler");
                                subCategoryGroups.add(group);
                            }
                            if (groupcountry.equals("11")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"Uganda");
                                subCategoryGroups.add(group);
                            }
                            if (groupcountry.equals("12")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"Pakistan");
                                subCategoryGroups.add(group);
                            }
                            if (groupcountry.equals("13")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"Nijerya");
                                subCategoryGroups.add(group);
                            }
                            if (groupcountry.equals("14")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"Singapur");
                                subCategoryGroups.add(group);
                            }
                            if (groupcountry.equals("15")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"Irlanda");
                                subCategoryGroups.add(group);
                            }
                            if (groupcountry.equals("16")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"Guney Afrika");
                                subCategoryGroups.add(group);
                            }
                            if (groupcountry.equals("17")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"Dominik");
                                subCategoryGroups.add(group);
                            }
                            if (groupcountry.equals("18")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"Sudan");
                                subCategoryGroups.add(group);
                            }
                            if (groupcountry.equals("19")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"Madagaskar");
                                subCategoryGroups.add(group);
                            }
                            if (groupcountry.equals("20")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"Kenya");
                                subCategoryGroups.add(group);
                            }
                            if (groupcountry.equals("21")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.bip),groupdate,"Uganda");
                                subCategoryGroups.add(group);
                            }
                        }

                        if (groupuri.contains("https://signal.goup")){

                            if (groupcountry.equals("0")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"Türkiye");
                                subCategoryGroups.add(group);
                            }
                            if (groupcountry.equals("1")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"Azerbaycan");
                                subCategoryGroups.add(group);
                            }
                            if (groupcountry.equals("2")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"Amerika");
                                subCategoryGroups.add(group);
                            }
                            if (groupcountry.equals("3")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"Ingiltere");
                                subCategoryGroups.add(group);
                            }
                            if (groupcountry.equals("4")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"Hindistan");
                                subCategoryGroups.add(group);
                            }
                            if (groupcountry.equals("5")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"Almanya");
                                subCategoryGroups.add(group);
                            }
                            if (groupcountry.equals("6")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"Fransa");
                                subCategoryGroups.add(group);
                            }
                            if (groupcountry.equals("7")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"Avustralya");
                                subCategoryGroups.add(group);
                            }
                            if (groupcountry.equals("8")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"Kanada");
                                subCategoryGroups.add(group);
                            }
                            if (groupcountry.equals("9")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"Yeni Zelanda");
                                subCategoryGroups.add(group);
                            }
                            if (groupcountry.equals("10")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"Filipinler");
                                subCategoryGroups.add(group);
                            }
                            if (groupcountry.equals("11")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"Uganda");
                                subCategoryGroups.add(group);
                            }
                            if (groupcountry.equals("12")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"Pakistan");
                                subCategoryGroups.add(group);
                            }
                            if (groupcountry.equals("13")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"Nijerya");
                                subCategoryGroups.add(group);
                            }
                            if (groupcountry.equals("14")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"Singapur");
                                subCategoryGroups.add(group);
                            }
                            if (groupcountry.equals("15")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"Irlanda");
                                subCategoryGroups.add(group);
                            }
                            if (groupcountry.equals("16")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"Guney Afrika");
                                subCategoryGroups.add(group);
                            }
                            if (groupcountry.equals("17")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"Dominik");
                                subCategoryGroups.add(group);
                            }
                            if (groupcountry.equals("18")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"Sudan");
                                subCategoryGroups.add(group);
                            }
                            if (groupcountry.equals("19")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"Madagaskar");
                                subCategoryGroups.add(group);
                            }
                            if (groupcountry.equals("20")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"Kenya");
                                subCategoryGroups.add(group);
                            }
                            if (groupcountry.equals("21")){
                                Group group = new Group(uid,groupcategory,groupname,groupdescription,groupuri,String.valueOf(R.drawable.signal),groupdate,"Uganda");
                                subCategoryGroups.add(group);
                            }
                        }

                    }

                    adapter = new GroupAdapter(subCategoryGroups,SubCategoryGroupsActivity.this);
                    recyclerViewSubCategoryGroups.setAdapter(adapter);

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

                Map<String, String> params = new HashMap<>();

                params.put("id",id);
                params.put("groupcountry",String.valueOf(countrynumber));

                return params;
            }
        };

        Volley.newRequestQueue(SubCategoryGroupsActivity.this).add(stringRequest);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(SubCategoryGroupsActivity.this,MainActivity.class);
        startActivity(intent);
        CustomIntent.customType(SubCategoryGroupsActivity.this,"right-to-left");
        finish();
    }

}