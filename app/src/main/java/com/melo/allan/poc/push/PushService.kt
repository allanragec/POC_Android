package com.melo.allan.poc.push

import android.app.IntentService
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.support.v4.app.NotificationCompat
import com.melo.allan.poc.activities.LoginActivity
import com.melo.allan.poc.R
import com.melo.allan.poc.activities.MainActivity

import org.json.JSONException
import org.json.JSONObject

import java.util.Date

/**
 * Created by allan.melo on 10/05/18.
 */
class PushService : IntentService(PushService::class.java.simpleName) {

    public override fun onHandleIntent(intent: Intent?) {
        val bundle = intent!!.extras
        try {
            val result = JSONObject(bundle!!.get("payload")!!.toString())

            val title = result.getString("title")
            val message = result.getString("message")
            val description = result.getString("description")

            val newIntent = Intent(this, MainActivity::class.java)
            newIntent.putExtra("title", title)
            newIntent.putExtra("message", message)
            newIntent.putExtra("description", description)

            val pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, newIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT)

            val style = NotificationCompat.BigTextStyle()

            style.setBigContentTitle(title)
            style.bigText(message)

            val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val notificationBuilder = NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setStyle(style)
                    .setContentIntent(pendingIntent)

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.notify(message.hashCode() + Date().hashCode(), notificationBuilder.build())

        } catch (e: JSONException) {
            e.printStackTrace()
        }

    }

}