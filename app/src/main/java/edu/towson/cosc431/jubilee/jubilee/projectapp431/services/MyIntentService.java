package edu.towson.cosc431.jubilee.jubilee.projectapp431.services;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import edu.towson.cosc431.jubilee.jubilee.projectapp431.MainActivity;
import edu.towson.cosc431.jubilee.jubilee.projectapp431.R;

/**
 * Created by Montrell on 5/13/2018.
 */

public class MyIntentService extends IntentService {

    private NotificationCompat.Builder builder;

    private static final String TAG = MyIntentService.class.getName();
    private static final String CHANNEL_ID = "CHANNEL";

    public MyIntentService() {
        super("ExampleIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if(intent != null) {
            String value = intent.getStringExtra(MainActivity.DAILY_LIMIT);
            showNotification(value);
        }
    }

    private void showNotification(String value) {
        builder = new NotificationCompat.Builder(this, CHANNEL_ID);
        builder.setContentTitle("Warning User");
        builder.setContentText("You have reached your daily limit amount.");
        builder.setSmallIcon(android.R.drawable.ic_dialog_alert);
        builder.setVibrate(new long[]{500L, 500L, 100L, 1000L});

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);

        //copied from android developers
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = (NotificationManager) getSystemService(
                    NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(mChannel);
        }

//        builder.setSound(Uri.fromFile())
        // builder.setStyle(new android.support.v7.app.NotificationCompat.InboxStyle().setBigContentTitle("Hello World"));
        Notification notification = builder.build();
        NotificationManagerCompat.from(this)
                .notify(101, notification);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"onCreate");
    }
}
