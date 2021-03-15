package com.furkanayaz.groupspad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.CardViewDesignThingsHolder>{
    private Context mContext;
    private List<Notification> notificationList = new ArrayList<>();

    public NotificationAdapter(Context mContext, List<Notification> notificationList) {
        this.mContext = mContext;
        this.notificationList = notificationList;
    }

    @NonNull
    @Override
    public CardViewDesignThingsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.cardview_notification,parent,false);
        return new CardViewDesignThingsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewDesignThingsHolder holder, int position) {
        Notification notification = notificationList.get(position);
        holder.textViewNotificationTitle.setText(notification.getNotificationtitle());
        holder.textViewNotificationDescription.setText(notification.getNotificationdescription());
        holder.textViewNotificationDate.setText(notification.getNotificationdate());

    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }


    public class CardViewDesignThingsHolder extends RecyclerView.ViewHolder{
        private TextView textViewNotificationTitle;
        private TextView textViewNotificationDescription;
        private TextView textViewNotificationDate;

        public CardViewDesignThingsHolder(@NonNull View itemView) {
            super(itemView);
            textViewNotificationTitle = itemView.findViewById(R.id.textViewNotificationTitle);
            textViewNotificationDescription = itemView.findViewById(R.id.textViewNotificationDescription);
            textViewNotificationDate = itemView.findViewById(R.id.textViewNotificationDate);
        }
    }
}
