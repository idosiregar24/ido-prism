package com.example.ido_prism.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.util.Log

class ReminderManager(private val context: Context) {

    private val alarmManager: AlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    fun setReminder(minutesFromNow: Int, proyekId: String, proyekTitle: String) {
        val triggerTime = System.currentTimeMillis() + (minutesFromNow * 60 * 1000)

        val intent = Intent(context, ProjectReminderReceiver::class.java).apply {
            putExtra("PROYEK_ID", proyekId)
            putExtra("TITLE", "Pengingat Proyek: $proyekTitle")
            putExtra("MESSAGE", "Waktu pengingat ($minutesFromNow menit) telah tiba!")
        }

        // Unique requestCode menggunakan hash code dari proyekId agar tidak menimpa alarm lain
        val requestCode = proyekId.hashCode()

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            requestCode,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (alarmManager.canScheduleExactAlarms()) {
                    alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        triggerTime,
                        pendingIntent
                    )
                    Log.d("ReminderManager", "Exact alarm scheduled for $proyekTitle")
                } else {
                    // Jika tidak punya izin exact alarm, gunakan set biasa dan beri peringatan
                    alarmManager.set(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent)
                    Log.w("ReminderManager", "Exact alarm permission not granted, using inexact fallback.")
                    
                    // Opsi: Arahkan user ke Settings jika benar-benar butuh exact alarm
                    // val intentSettings = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
                    // context.startActivity(intentSettings)
                }
            } else {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    triggerTime,
                    pendingIntent
                )
            }
        } catch (e: Exception) {
            Log.e("ReminderManager", "Error scheduling alarm: ${e.message}")
        }
    }

    fun cancelReminder(proyekId: String) {
        val intent = Intent(context, ProjectReminderReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            proyekId.hashCode(),
            intent,
            PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
        )
        if (pendingIntent != null) {
            alarmManager.cancel(pendingIntent)
        }
    }
}
