package com.example.ido_prism.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.ido_prism.MainActivity
import com.example.ido_prism.R

import android.util.Log

class NotificationHelper(private val context: Context) {

    companion object {
        private const val TAG = "NotificationHelper"
        const val CHANNEL_ID = "proyek_desa_channel"
        const val CHANNEL_NAME = "Monitoring Proyek Desa"
        const val CHANNEL_DESC = "Notifikasi untuk pembaruan progres dan pengingat deadline proyek desa"
    }

    init {
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance).apply {
            description = CHANNEL_DESC
            enableLights(true)
            enableVibration(true)
            setShowBadge(true)
        }
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
        Log.d(TAG, "Notification Channel Created")
    }

    fun showNotification(title: String, message: String, proyekId: String? = null) {
        Log.d(TAG, "Showing notification: $title - $message")
        
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("PROYEK_ID", proyekId)
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            System.currentTimeMillis().toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        
        // Cek izin secara eksplisit sebelum notify (untuk logging)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permission = context.checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS)
            Log.d(TAG, "POST_NOTIFICATIONS permission status: $permission")
        }

        notificationManager.notify(System.currentTimeMillis().toInt(), builder.build())
        Log.d(TAG, "Notification sent to NotificationManager")
    }
}
