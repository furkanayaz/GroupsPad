package com.furkanayaz.groupspad;


import android.app.Activity;
import android.content.Context;

import android.content.Intent;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CardViewDesignThingsHolder>{
    private List<Category> categoryList = new ArrayList<>();
    private Context mContext;



    public CategoryAdapter(List<Category> categoryList, Context mContext) {
        this.categoryList = categoryList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public CardViewDesignThingsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.cardview_category,parent,false);
        return new CardViewDesignThingsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewDesignThingsHolder holder, int position) {
        Category category = categoryList.get(position);

        holder.textViewCategoryName.setText(category.getCategoryname());
        holder.textViewCategoryDescription.setText(category.getCategorydescription());

        holder.cardViewCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,SubCategoryGroupsActivity.class);
                intent.putExtra("categoryobject",category);
                mContext.startActivity(intent);
                ((Activity)mContext).finish();
            }
        });

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class CardViewDesignThingsHolder extends RecyclerView.ViewHolder{
        private CardView cardViewCategory;
        private TextView textViewCategoryName;
        private TextView textViewCategoryDescription;

        public CardViewDesignThingsHolder(@NonNull View itemView) {
            super(itemView);
            cardViewCategory = itemView.findViewById(R.id.cardViewCategory);
            textViewCategoryName = itemView.findViewById(R.id.textViewCategoryName);
            textViewCategoryDescription = itemView.findViewById(R.id.textViewCategoryDescription);
        }
    }
}
