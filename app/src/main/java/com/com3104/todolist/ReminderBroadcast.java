package com.com3104.todolist;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class ReminderBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "Todo reminder");
        builder.setSmallIcon(R.drawable.ic_launcher_background);
        builder.setContentTitle(intent.getStringExtra("title"));
        //builder.setContentText(intent.getStringExtra("msg"));
        builder.setStyle(new
                NotificationCompat.BigTextStyle().bigText(intent.getStringExtra("msg")));
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        notificationManager.notify(Integer.parseInt(intent.getStringExtra("id")), builder.build());
    }
}
