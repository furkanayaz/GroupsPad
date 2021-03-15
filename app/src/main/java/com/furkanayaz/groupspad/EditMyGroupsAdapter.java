package com.furkanayaz.groupspad;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditMyGroupsAdapter extends RecyclerView.Adapter<EditMyGroupsAdapter.CardViewDesignThingsHolder>{
    private Context mContext;
    private List<Group> groupList = new ArrayList<>();

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    public EditMyGroupsAdapter(Context mContext, List<Group> groupList) {
        this.mContext = mContext;
        this.groupList = groupList;
    }

    @NonNull
    @Override
    public CardViewDesignThingsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.cardview_group,parent,false);
        return new CardViewDesignThingsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewDesignThingsHolder holder, int position) {
        Group group = groupList.get(position);
        holder.textViewGroupName.setText(group.getTextViewGroupName());
        holder.textViewGroupUri.setText(group.getGroupuri());
        holder.textViewGroupDescription.setText(group.getTextViewGroupDescription());
        holder.textViewGroupDate.setText(group.getTextViewGroupDate());
        holder.textViewGroupCountry.setText(group.getTextViewGroupCountry());
        holder.imageViewGroupApp.setImageResource(Integer.parseInt(group.getImageViewGroupApp()));

        holder.cardViewGroupNameAndDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(mContext,v);
                popupMenu.getMenuInflater().inflate(R.menu.groupmenu,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.action_delete:
                                firebaseAuth = FirebaseAuth.getInstance();
                                firebaseUser = firebaseAuth.getCurrentUser();

                                deleteMyGroup(firebaseUser.getUid(),holder.textViewGroupUri.getText().toString().trim());
                                Toast.makeText(mContext,"Grubunuz başarıyla silindi",Toast.LENGTH_LONG).show();

                                return true;
                            default:
                                return false;
                        }
                    }
                });

                popupMenu.show();

            }
        });

    }

    private void deleteMyGroup(String uid, String uri) {
        String url = "https://frknnyz.tk/groupspad/delete_group.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response: ",response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("uid",uid);
                params.put("uri",uri);
                return params;
            }
        };

        Volley.newRequestQueue(mContext).add(stringRequest);

    }

    @Override
    public int getItemCount() {
        return groupList.size();
    }

    public class CardViewDesignThingsHolder extends RecyclerView.ViewHolder{
        private TextView textViewGroupName,textViewGroupUri,textViewGroupDescription,textViewGroupDate,textViewGroupCountry;
        private ImageView imageViewGroupApp;
        private CardView cardViewGroupNameAndDescription;

        public CardViewDesignThingsHolder(@NonNull View itemView) {
            super(itemView);
            textViewGroupName = itemView.findViewById(R.id.textViewGroupName);
            textViewGroupUri = itemView.findViewById(R.id.textViewGroupUri);
            textViewGroupDescription = itemView.findViewById(R.id.textViewGroupDescription);
            textViewGroupDate = itemView.findViewById(R.id.textViewGroupDate);
            textViewGroupCountry = itemView.findViewById(R.id.textViewGroupCountry);
            imageViewGroupApp = itemView.findViewById(R.id.imageViewGroupApp);
            cardViewGroupNameAndDescription = itemView.findViewById(R.id.cardViewGroupNameAndDescription);
        }
    }

}
