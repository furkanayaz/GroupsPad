package com.furkanayaz.groupspad;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

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
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import maes.tech.intentanim.CustomIntent;

public class FragmentAddGroup extends Fragment {
    private TextView textViewAddGroupChooseCategory;

    private Spinner spinnerAddGroupCategory;
    private Spinner spinnerAddGroupCountry;

    private TextInputEditText textInputEditTextAddGroupGroupName,textInputEditTextAddGroupGroupDescription,textInputEditTextAddGroupGroupUri;
    private CardView cardViewAddGroupCreateGroup;

    private ProgressBar progressBarAddGroup;

    private ArrayList<String> categoriesArrayList = new ArrayList<>();
    private ArrayList<String> countriesArrayList = new ArrayList<>();

    private ArrayAdapter<String> categoryArrayAdapter;
    private ArrayAdapter<String> countriesArrayAdapter;

    private AdView adViewAddGroup;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_addgroup,container,false);

        textViewAddGroupChooseCategory = view.findViewById(R.id.textViewAddGroupChooseCategory);

        spinnerAddGroupCategory = view.findViewById(R.id.spinnerAddGroupCategory);
        spinnerAddGroupCountry = view.findViewById(R.id.spinnerAddGroupCountry);

        textInputEditTextAddGroupGroupName = view.findViewById(R.id.textInputEditTextAddGroupGroupName);
        textInputEditTextAddGroupGroupDescription = view.findViewById(R.id.textInputEditTextAddGroupGroupDescription);
        textInputEditTextAddGroupGroupUri = view.findViewById(R.id.textInputEditTextAddGroupGroupUri);

        cardViewAddGroupCreateGroup = view.findViewById(R.id.cardViewAddGroupCreateGroup);

        progressBarAddGroup = view.findViewById(R.id.progressBarAddGroup);
        adViewAddGroup = view.findViewById(R.id.adViewAddGroup);


        checkInternetConnectionAddGroup();


        MobileAds.initialize(getContext(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });

        AdRequest adRequest = new AdRequest.Builder().build();
        adViewAddGroup.loadAd(adRequest);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        if (textViewAddGroupChooseCategory.getText().toString().trim().contains("SELECT")){
            categoriesArrayList.add("Technology");
            categoriesArrayList.add("Software");
            categoriesArrayList.add("Motivational");
            categoriesArrayList.add("Psychology");
            categoriesArrayList.add("Game");
            categoriesArrayList.add("Entartainment");
            categoriesArrayList.add("Education");
            categoriesArrayList.add("Sport");
            categoriesArrayList.add("Conversation");
            categoriesArrayList.add("Travel");
        }else {
            categoriesArrayList.add("Teknoloji");
            categoriesArrayList.add("Yazilim");
            categoriesArrayList.add("Motivasyonel");
            categoriesArrayList.add("Psikolojik");
            categoriesArrayList.add("Oyun");
            categoriesArrayList.add("Eglence");
            categoriesArrayList.add("Egitim");
            categoriesArrayList.add("Spor");
            categoriesArrayList.add("Sohbet");
            categoriesArrayList.add("Seyehat");
        }

        categoryArrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,android.R.id.text1, categoriesArrayList);

        spinnerAddGroupCategory.setAdapter(categoryArrayAdapter);

        if (textViewAddGroupChooseCategory.getText().toString().trim().contains("SELECT")){
            countriesArrayList.add("Turkey");
            countriesArrayList.add("Azerbaijan");
            countriesArrayList.add("United States of America");
            countriesArrayList.add("United Kingdom");
            countriesArrayList.add("India");
            countriesArrayList.add("Germany");
            countriesArrayList.add("France");
            countriesArrayList.add("Australia");
            countriesArrayList.add("Canada");
            countriesArrayList.add("New Zealand");
            countriesArrayList.add("Philippines");
            countriesArrayList.add("Uganda");
            countriesArrayList.add("Pakistan");
            countriesArrayList.add("Nigeria");
            countriesArrayList.add("Singapore");
            countriesArrayList.add("Ireland");
            countriesArrayList.add("South Africa");
            countriesArrayList.add("Dominicia");
            countriesArrayList.add("Sudan");
            countriesArrayList.add("Madagascar");
            countriesArrayList.add("Kenya");
        }else {
            countriesArrayList.add("Türkiye");
            countriesArrayList.add("Azerbaycan");
            countriesArrayList.add("Amerika Birleşik Devletleri");
            countriesArrayList.add("İngiltere");
            countriesArrayList.add("Hindistan");
            countriesArrayList.add("Almanya");
            countriesArrayList.add("Fransa");
            countriesArrayList.add("Avustralya");
            countriesArrayList.add("Kanada");
            countriesArrayList.add("Yeni Zelanda");
            countriesArrayList.add("Filipinler");
            countriesArrayList.add("Uganda");
            countriesArrayList.add("Pakistan");
            countriesArrayList.add("Nijerya");
            countriesArrayList.add("Singapur");
            countriesArrayList.add("İrlanda");
            countriesArrayList.add("Güney Afrika");
            countriesArrayList.add("Dominik");
            countriesArrayList.add("Sudan");
            countriesArrayList.add("Madagaskar");
            countriesArrayList.add("Kenya");
        }

        countriesArrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,android.R.id.text1,countriesArrayList);

        spinnerAddGroupCountry.setAdapter(countriesArrayAdapter);

        cardViewAddGroupCreateGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (firebaseUser == null){
                    if (textViewAddGroupChooseCategory.getText().toString().trim().contains("SELECT")){
                        Snackbar snackbar = Snackbar.make(v,"To create a group, you must be a member of the application.",Snackbar.LENGTH_LONG).setActionTextColor(Color.WHITE).setAction("Sign Up", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getContext(),SignUpActivity.class);
                                startActivity(intent);
                                CustomIntent.customType(getContext(),"fadein-to-fadeout");
                                getActivity().finish();
                            }
                        });

                        snackbar.show();
                    }else {
                        Snackbar snackbar = Snackbar.make(v,"Grup oluşturmak için uygulamaya üye olmanız gerekmektedir",Snackbar.LENGTH_LONG).setActionTextColor(Color.WHITE).setAction("Üye Ol", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getContext(),SignUpActivity.class);
                                startActivity(intent);
                                CustomIntent.customType(getContext(),"fadein-to-fadeout");
                                getActivity().finish();
                            }
                        });

                        snackbar.show();
                    }


                }else {

                    String uid = firebaseUser.getUid().trim();

                    cardViewAddGroupCreateGroup.setVisibility(View.INVISIBLE);
                    progressBarAddGroup.setVisibility(View.VISIBLE);

                    int groupcategory = spinnerAddGroupCategory.getSelectedItemPosition();
                    int groupcountry = spinnerAddGroupCountry.getSelectedItemPosition();
                    String groupname = textInputEditTextAddGroupGroupName.getText().toString().trim();
                    String groupdescription = textInputEditTextAddGroupGroupDescription.getText().toString().trim();
                    String groupuri = textInputEditTextAddGroupGroupUri.getText().toString().trim();

                    if (!(groupname.length()<=3) && !(groupdescription.length()<=5) && !groupuri.isEmpty() && Patterns.WEB_URL.matcher(groupuri).matches()){

                        if (groupuri.contains("https://t.me/joinchat/") || groupuri.contains("t.me/joinchat/G5QOy9P10SCpvf79")){
                            //https://t.me/joinchat/G5QOy9P10SCpvf79

                            if (!(groupuri.length()<=25)){
                                addGroup(uid,groupcategory,groupcountry,groupname,groupdescription,groupuri);
                            }else if (textViewAddGroupChooseCategory.getText().toString().trim().contains("SELECT")){
                                Toast.makeText(getContext(),"Check the Telegram link",Toast.LENGTH_LONG).show();
                                visualObjectRefresh();
                            }else {
                                Toast.makeText(getContext(),"Telegram bağlantı linkini kontrol ediniz",Toast.LENGTH_LONG).show();
                                visualObjectRefresh();
                            }

                        }/*else {
                            Toast.makeText(getContext(),"Telegram bağlantı linkini kontrol ediniz",Toast.LENGTH_LONG).show();
                            visualObjectRefresh();
                        }*/

                        if (groupuri.contains("https://chat.whatsapp.com/") || groupuri.contains("chat.whatsapp.com/")){
                            //https://chat.whatsapp.com/CAKSThxzuV5Lb2FtpJVyPo

                            if (!(groupuri.length()<=30)){
                                addGroup(uid,groupcategory,groupcountry,groupname,groupdescription,groupuri);
                            }else if (textViewAddGroupChooseCategory.getText().toString().trim().contains("SELECT")){
                                Toast.makeText(getContext(),"Check the Whatsapp link",Toast.LENGTH_LONG).show();
                                visualObjectRefresh();
                            }else {
                                Toast.makeText(getContext(),"Whatsapp bağlantı linkini kontrol ediniz",Toast.LENGTH_LONG).show();
                                visualObjectRefresh();
                            }

                        }/*else {
                            Toast.makeText(getContext(),"Whatsapp bağlantı linkini kontrol ediniz",Toast.LENGTH_LONG).show();
                            visualObjectRefresh();
                        }*/

                        if (groupuri.contains("https://groups.bip.ai/share/") || groupuri.contains("groups.bip.ai/share/")){
                            //https://groups.bip.ai/share/cwnH9XarVl6BA7BUFzVH4MOVy4lsyk80

                            if (!(groupuri.length()<=35)){
                                addGroup(uid,groupcategory,groupcountry,groupname,groupdescription,groupuri);
                            }else if (textViewAddGroupChooseCategory.getText().toString().trim().contains("SELECT")){
                                Toast.makeText(getContext(),"Check the bip link",Toast.LENGTH_LONG).show();
                                visualObjectRefresh();
                            }else {
                                Toast.makeText(getContext(),"Bip bağlantı linkini kontrol ediniz",Toast.LENGTH_LONG).show();
                                visualObjectRefresh();
                            }

                        }/*else {
                            Toast.makeText(getContext(),"Bip bağlantı linkini kontrol ediniz",Toast.LENGTH_LONG).show();
                            visualObjectRefresh();
                        }*/

                        if (groupuri.contains("https://signal.group/") || groupuri.contains("signal.group/")){
                            //https://signal.group/#CjQKIOanBea_b_XwP22L9-Y1vcG8NU2NmCakrfuKkxB0YMXhEhAunHtfV59D4NZxQ22v7ME3

                            if (!(groupuri.length()<=40)){
                                addGroup(uid,groupcategory,groupcountry,groupname,groupdescription,groupuri);
                            }else if (textViewAddGroupChooseCategory.getText().toString().trim().contains("SELECT")){
                                Toast.makeText(getContext(),"Check the signal link",Toast.LENGTH_LONG).show();
                                visualObjectRefresh();
                            }else {
                                Toast.makeText(getContext(),"Signal bağlantı linkini kontrol ediniz",Toast.LENGTH_LONG).show();
                                visualObjectRefresh();
                            }

                        }/*else {
                            Toast.makeText(getContext(),"Signal bağlantı linkini kontrol ediniz",Toast.LENGTH_LONG).show();
                            visualObjectRefresh();
                        }*/



                    }else if (textViewAddGroupChooseCategory.getText().toString().trim().contains("SELECT")){
                        Toast.makeText(getContext(),"Check your information",Toast.LENGTH_LONG).show();
                        visualObjectRefresh();
                    }else {
                        Toast.makeText(getContext(),"Bilgilerinizi kontrol ediniz",Toast.LENGTH_LONG).show();
                        visualObjectRefresh();
                    }
                }

            }
        });

        return view;
    }

    private void checkInternetConnectionAddGroup() {
        ConnectivityManager connectivityManager = (ConnectivityManager)getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected() == true){

        }else if(textViewAddGroupChooseCategory.getText().toString().trim().contains("GRUP KATEGORİSİ")){
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

    private void addGroup(String uid, int groupcategory, int groupcountry, String groupname, String groupdescription, String groupuri) {
        String url = "https://frknnyz.tk/groupspad/insert_group.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

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
                params.put("groupcategory",String.valueOf(groupcategory));
                params.put("groupcountry",String.valueOf(groupcountry));
                params.put("groupname",groupname);
                params.put("groupdescription",groupdescription);
                params.put("groupuri",groupuri);


                return params;

            }
        };

        Volley.newRequestQueue(getContext()).add(stringRequest);
        visualObjectRefresh();

        if (textViewAddGroupChooseCategory.getText().toString().trim().contains("SELECT")){
            Toast.makeText(getContext(),"Your group has been created successfully",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(getContext(),"Grubunuz başarıyla oluşturuldu",Toast.LENGTH_LONG).show();
        }



    }

    private void visualObjectRefresh() {
        progressBarAddGroup.setVisibility(View.INVISIBLE);
        cardViewAddGroupCreateGroup.setVisibility(View.VISIBLE);
    }

}
