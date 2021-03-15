package com.furkanayaz.groupspad;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FragmentCategories extends Fragment {
    private RecyclerView recyclerViewCategory;
    private ArrayList<Category> categoryArrayList;
    private TextView textView4;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_categories,container,false);

        recyclerViewCategory = view.findViewById(R.id.recyclerViewCategory);

        textView4 = view.findViewById(R.id.textView4);

        checkInternetConnectionCategories();

        recyclerViewCategory.setHasFixedSize(true);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerViewCategory.setLayoutManager(staggeredGridLayoutManager);

        categoryArrayList = new ArrayList<>();

        allCategories();

        return view;
    }

    private void checkInternetConnectionCategories() {
        ConnectivityManager connectivityManager = (ConnectivityManager)getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected() == true){

        }else if (textView4.getText().toString().trim().contains("Senin için")){
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

    private void allCategories() {
        String url = "https://frknnyz.tk/groupspad/all_categories.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray categoryList = jsonObject.getJSONArray("categories");

                    for (int i = 0;i<categoryList.length();i++){
                        JSONObject c = categoryList.getJSONObject(i);

                        String id = c.getString("id");
                        String categoryname = c.getString("categoryname");
                        String categorydescription = c.getString("categorydescription");

                        if (textView4.getText().toString().trim().contains("Senin için kategorilerimiz")){
                            Category category = new Category(id,categoryname,categorydescription);
                            categoryArrayList.add(category);
                        }else {
                            if (categoryname.equals("Teknoloji")){
                                Category category = new Category(id,"Technology","Find great groups where they can discuss tech information about technology!");
                                categoryArrayList.add(category);
                            }
                            if (categoryname.equals("Yazılım")){
                                Category category = new Category(id,"Software","You can improve yourself by finding groups in Computer Science to help each other!");
                                categoryArrayList.add(category);
                            }
                            if (categoryname.equals("Motivasyonel")){
                                Category category = new Category(id,"Motivational","Find the appropriate motivation with your group mates to achieve your goals!");
                                categoryArrayList.add(category);
                            }
                            if (categoryname.equals("Psikoloji")){
                                Category category = new Category(id,"Psychology","Motivate each other with your group mates to manage the stress that is a part of our daily life!");
                                categoryArrayList.add(category);
                            }
                            if (categoryname.equals("Oyun")){
                                Category category = new Category(id,"Game","Find groups to support each other for your video games!");
                                categoryArrayList.add(category);
                            }
                            if (categoryname.equals("Eğlence")){
                                Category category = new Category(id,"Entartainment","Is there a concert nearby? Find your group to chat with your concert mates!");
                                categoryArrayList.add(category);
                            }
                            if (categoryname.equals("Eğitim")){
                                Category category = new Category(id,"Education","Regardless of high school or university, you can find groups that you can get help and support in your exams and ask questions you cannot solve!");
                                categoryArrayList.add(category);
                            }
                            if (categoryname.equals("Spor")){
                                Category category = new Category(id,"Sport","Have fun finding your bandmates with whom you can play sports and even schedule a date for soccer!");
                                categoryArrayList.add(category);
                            }
                            if (categoryname.equals("Sohbet")){
                                Category category = new Category(id,"Conversation","Would you like to have some fun? Have fun with your friends to meet!");
                                categoryArrayList.add(category);
                            }
                            if (categoryname.equals("Seyehat")){
                                Category category = new Category(id,"Travel","See groups to meet with your guide and travel buddies!");
                                categoryArrayList.add(category);
                            }
                        }





                    }

                    CategoryAdapter categoryAdapter = new CategoryAdapter(categoryArrayList,getContext());
                    recyclerViewCategory.setAdapter(categoryAdapter);
                    categoryAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        Volley.newRequestQueue(getContext()).add(stringRequest);

    }
}
