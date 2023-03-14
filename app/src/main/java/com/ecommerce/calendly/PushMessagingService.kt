package com.ecommerce.calendly

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class PushMessagingService : FirebaseMessagingService() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        var data = message.data
        var title = data["title"]
        var message = data["message"]


        Log.i("PUSHNOTIFY",data.toString())


        val intent = Intent(this, BottomNavigation::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(applicationContext, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        var notificationId =  1;

        var builder = NotificationCompat.Builder(applicationContext, "CHANNEL_ID")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)


        if(ActivityCompat.checkSelfPermission(applicationContext,android.Manifest.permission.POST_NOTIFICATIONS)!= PackageManager.PERMISSION_DENIED){
            with(NotificationManagerCompat.from(this)) {
                val name = getString(R.string.closed)
                val descriptionText = getString(R.string.closed)
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val channel = NotificationChannel("CHANNEL_ID", name, importance).apply {
                    description = descriptionText
                }
                createNotificationChannel(channel)
                notify(notificationId, builder.build())
            }
            Log.i("pushnotification","Has sent notification")
        }else{
            Log.i("pushnotification","Hasn't been sent notification")
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

}