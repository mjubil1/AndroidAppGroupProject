package edu.towson.cosc431.jubilee.jubilee.projectapp431.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Montrell on 5/13/2018.
 */

public class MyBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = MyBroadcastReceiver.class.getName();

    IListener listener;
    public MyBroadcastReceiver(IListener listener) {
        this.listener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

    }

    public interface IListener {
        void onReceived();
    }

}
