package com.furkanayaz.groupspad;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.CardViewDesignThingsHolder>{
    private List<Group> groupList = new ArrayList<>();
    private Context mContext;
    private SharedPreferences sharedPreferencesNotifications;
    private SharedPreferences.Editor editorNotifications;
    private NotificationCompat.Builder builder;

    public GroupAdapter(List<Group> groupList, Context mContext) {
        this.groupList = groupList;
        this.mContext = mContext;
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
                sharedPreferencesNotifications = mContext.getSharedPreferences("notificationstatus",Context.MODE_PRIVATE);
                int notificationcontroller = sharedPreferencesNotifications.getInt("notifications",0);

                if (notificationcontroller == 0){
                    situationalNotification();
                }

                String url = holder.textViewGroupUri.getText().toString().trim();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                mContext.startActivity(intent);




            }
        });

    }

    private void situationalNotification() {
        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intent = new Intent(mContext,MainActivity.class);
        
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext,1,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            String channelId = "channelId";
            String channelName = "channelName";
            String channelIntroduction = "channelIntroduction";
            int channelImportant = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel notificationChannel = notificationManager.getNotificationChannel(channelId);

            if (notificationChannel == null){
                notificationChannel = new NotificationChannel(channelId,channelName,channelImportant);
                notificationChannel.setDescription(channelIntroduction);
                notificationManager.createNotificationChannel(notificationChannel);
            }

            builder = new NotificationCompat.Builder(mContext,channelId);
            builder.setContentTitle("GroupsPad Bildirimler (GroupsPad Notifications)");
            builder.setContentText("Oluşabilecek problemlerden sorumluluğunuzu unutmayınız (Do not forget your responsibility for the problems that may occur.)");
            builder.setSmallIcon(R.drawable.notificationicon);
            builder.setAutoCancel(true);
            builder.setContentIntent(pendingIntent);
            builder.setFullScreenIntent(pendingIntent,true);
        }else {

            builder = new NotificationCompat.Builder(mContext);
            builder.setContentTitle("GroupsPad Bildirimler (GroupsPad Notifications)");
            builder.setContentText("Oluşabilecek problemlerden sorumluluğunuzu unutmayınız (Do not forget your responsibility for the problems that may occur.)");
            builder.setSmallIcon(R.drawable.notificationicon);
            builder.setAutoCancel(true);
            builder.setContentIntent(pendingIntent);
            builder.setFullScreenIntent(pendingIntent,true);
            builder.setPriority(Notification.PRIORITY_HIGH);

        }

        notificationManager.notify(1,builder.build());

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
