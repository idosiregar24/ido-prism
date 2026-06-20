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
        
        // Mengarahkan ke BaseActivity (bukan MainActivity) karena BaseActivity yang punya BottomNav
        val intent = Intent(context, com.example.ido_prism.BaseActivity::class.java).apply {
            // SINGLE_TOP agar tidak membuka aplikasi dari awal (splash screen) jika sudah terbuka
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra("PROYEK_ID", proyekId)
            putExtra("FROM_NOTIFICATION", true)
        }

        val requestCode = proyekId?.hashCode() ?: System.currentTimeMillis().toInt()
        val pendingIntent = PendingIntent.getActivity(
            context,
            requestCode,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_MAX) // Biar muncul sebagai popup DAN menetap di tray
            .setCategory(NotificationCompat.CATEGORY_REMINDER)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        
        // Gunakan ID yang konsisten berdasarkan proyekId agar tidak duplikat di tray
        val notificationId = proyekId?.hashCode() ?: System.currentTimeMillis().toInt()
        notificationManager.notify(notificationId, builder.build())
    }
}
