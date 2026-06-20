package com.example.ido_prism.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class ProjectReminderReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val title = intent.getStringExtra("TITLE") ?: "Pengingat Proyek"
        val message = intent.getStringExtra("MESSAGE") ?: "Ada proyek yang mendekati deadline!"
        val proyekId = intent.getStringExtra("PROYEK_ID")

        val notificationHelper = NotificationHelper(context)
        notificationHelper.showNotification(title, message, proyekId)
    }
}
