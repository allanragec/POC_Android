package com.melo.allan.poc.push

import android.content.Context
import android.content.Intent
import android.support.v4.content.WakefulBroadcastReceiver

/**
 * Created by allan.melo on 10/05/18.
 */
class PushReceiver : WakefulBroadcastReceiver() {

    val serviceClassName: String
        get() = PushService::class.java.name

    override fun onReceive(context: Context, intent: Intent) {
        intent.setClassName(context, serviceClassName)
        WakefulBroadcastReceiver.startWakefulService(context, intent)
    }
}
