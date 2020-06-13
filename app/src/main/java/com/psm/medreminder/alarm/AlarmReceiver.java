package com.psm.medreminder.alarm;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.psm.medreminder.R;
import com.psm.medreminder.activity.IconTabsActivity;
import com.psm.medreminder.fragments.TwoFragment;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
//        Toast.makeText(context,"Haluuu makan ubat!",Toast.LENGTH_SHORT).show();

        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent intent1 = new Intent(context, IconTabsActivity.class);
        @SuppressLint("WrongConstant") PendingIntent pIntent = PendingIntent.getActivity(context,0,intent1,FLAG_ACTIVITY_NEW_TASK);

        NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("MedReminder")
                .setContentText("Hello! It's time to take medicine.")
                .setContentIntent(pIntent)
                .setSound(soundUri);
//                .addAction(0,"View",pIntent);
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,builder.build());
    }
}
