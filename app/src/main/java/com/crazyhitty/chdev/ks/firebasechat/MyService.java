package com.crazyhitty.chdev.ks.firebasechat;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by sushant oberoi on 01-10-2017.
 */

public class MyService extends Service {
    NotificationCompat.Builder builder;
    NotificationManager mNotificationManager;
    int uniqueID;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Let it continue running until it is stopped.
        builder =
                new NotificationCompat.Builder(this);
        builder.setAutoCancel(true);
        uniqueID=34527;
        while(true){
            // check here for condition to notify
            try {

                 addNotification();
            } catch (IOException e) {
                e.printStackTrace();
            }
            break;
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }
    public  void addNotification() throws IOException {
        String data = "User has bought a book";
            builder.setSmallIcon(R.drawable.notification);
            builder.setTicker("Boook buy request:");
            builder.setWhen(System.currentTimeMillis());
            builder.setContentTitle("Book bought");
            //builder.setContentText(""+position);
         /* Increase notification number every time a new notification arrives */

   /* Add Big View Specific Configuration */
            NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
                inboxStyle.setBigContentTitle("what is the response");

                // Moves events into the big view
                for (int i = 0; i < 3; i++) {
                    inboxStyle.addLine("yo");
                }

                builder.setStyle(inboxStyle);
                Intent resultIntent = new Intent(this, HomeActivity.class);

                TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
                stackBuilder.addParentStack(HomeActivity.class);

   /* Adds the Intent that starts the Activity to the top of the stack */
                stackBuilder.addNextIntent(resultIntent);
                PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

                builder.setContentIntent(resultPendingIntent);
                mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

   /* notificationID allows you to update the notification later on. */
                mNotificationManager.notify(uniqueID, builder.build());
            }

}
