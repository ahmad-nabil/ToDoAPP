package com.ahmad.todoapp;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
String title=intent.getStringExtra("title");
String content=intent.getStringExtra("content");
        displayNotification(context, title, content);
    }

    @SuppressLint("MissingPermission")
    private void displayNotification(Context context, String title, String content) {
        NotificationCompat.Builder notfi=new NotificationCompat.Builder(context,"1")
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(title)
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);;
        NotificationManagerCompat  manager= NotificationManagerCompat.from(context);
        manager.notify(0,notfi.build());
    }

}
