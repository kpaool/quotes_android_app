package com.ecommerce.calendly


import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import android.app.NotificationChannel
import android.app.NotificationManager

class PresentNotification(context: Context) {

    val intent = Intent(context, BottomNavigation::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    private val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)


    var notificationId =  1;

    private var builder = NotificationCompat.Builder(context, "CHANNEL_ID")
        .setSmallIcon(R.mipmap.ic_launcher)
        .setContentTitle("First notification")
        .setContentText("Hello world")
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)

//    if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.POST_NOTIFICATIONS)!=PackageManager.PERMISSION_DENIED){
//        return;
//    }


//    with(NotificationManagerCompat.from(context)) {
//        // notificationId is a unique int for each notification that you must define
//        notify(notificationId, builder.build())
//    }


}