package edu.towson.cosc431.jubilee.jubilee.projectapp431.services;

import android.app.IntentService;
import android.app.Notification;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import edu.towson.cosc431.jubilee.jubilee.projectapp431.MainActivity;

/**
 * Created by Montrell on 5/13/2018.
 */

public class MyIntentService extends IntentService {

    private NotificationCompat.Builder builder;

    private static final String TAG = MyIntentService.class.getName();

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
        builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("Waring User");
        builder.setContentText("You have reached your daily limit amount." + value);
        builder.setSmallIcon(android.R.drawable.ic_media_play);
        builder.setVibrate(new long[]{500L, 500L, 100L, 1000L});
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
