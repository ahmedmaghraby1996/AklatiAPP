package com.example.aklatiapp;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MyIntentService extends IntentService {

    Intent in;
    PendingIntent pe;
    public MyIntentService() {
        super("MyIntentService");
    }




    @Override
    protected void onHandleIntent(Intent intent) {


        in=new Intent(this,CookActivity.class);
      pe=PendingIntent.getActivity(getApplicationContext(),0,in,0);




        createNotificationChannel();

        //show notification
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this,"app");
        builder.setTicker("new message")
                .setContentTitle("new message")
                .setContentText("you recived a new order")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setContentIntent(pe);

        Notification notification = builder.build();

        NotificationManager manager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(1,notification);


    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "app";
            String description = "app";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("app", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
            }

