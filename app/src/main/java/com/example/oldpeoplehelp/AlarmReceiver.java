package com.example.oldpeoplehelp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.telephony.mbms.StreamingServiceInfo;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import static android.service.controls.ControlsProviderService.TAG;

public class AlarmReceiver extends BroadcastReceiver {

    private static final String CHANNEL_ID = "CHANNEL_SAMPLE";
    FirebaseAuth firebaseAuth;
    //ArrayList<Integer> ids;
    //ArrayList<String> messages;
    @Override
    public void onReceive(Context context, Intent intent) {

        //Current User ID
        FirebaseUser user = firebaseAuth.getInstance().getCurrentUser();
        final String currentUserId = user.getUid();

        // Get id & message
        int notificationId = intent.getIntExtra("notificationId", 0); //intent.getExtras.getInt("notificationId");//intent.getIntExtra("notificationId", 0);
        String message = intent.getStringExtra("medicineName")+" / "+intent.getStringExtra("medicineDose");
        //String time = intent.getStringExtra("time");
        //ids.add(notificationId);
        //messages.add(message);


            // Call MainActivity when notification is tapped.
            Intent mainIntent = new Intent(context, NotificationsMedicineActivity.class);
            PendingIntent contentIntent = PendingIntent.getActivity(context, notificationId, mainIntent, 0);//PendingIntent.getBroadcast(this, (int)System.currentTimeMillis(), notificationIntent, PendingIntent.FLAG_ONE_SHOT);
        Log.d(TAG, "onReceive: "+notificationId);
                    //PendingIntent.getActivity(context, 0, mainIntent, 0);

            // NotificationManager
            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // For API 26 and above
                CharSequence channelName = "My Notification";
                int importance = NotificationManager.IMPORTANCE_DEFAULT;

                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, channelName, importance);
                notificationManager.createNotificationChannel(channel);
            }

            // Prepare Notification
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(android.R.drawable.ic_dialog_info)
                    .setContentTitle("It's TIME ! Take Your Medicine ")
                    .setContentText(message)
                    .setContentIntent(contentIntent)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setAutoCancel(true);
            Notification notif = builder.build();
            notif.defaults |= Notification.DEFAULT_SOUND;
            notif.defaults |= Notification.DEFAULT_VIBRATE;

            // Notify
            notificationManager.notify(notificationId, builder.build());


    }
}
