package com.example.mibandtextsender

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val CHANNEL_ID = "PUSH_CHANNEL"
    var current_notification_id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Create channel
        createNotificationChannel()

        var editText : EditText = findViewById(R.id.editText)

        button.setOnClickListener {
            with(NotificationManagerCompat.from(this)) {
                notify(current_notification_id, NotificationCompat.Builder(applicationContext, CHANNEL_ID)
                    .setSmallIcon(R.drawable.push_icon)
                    .setContentTitle("Your message\n")
                    .setStyle(NotificationCompat.BigTextStyle()
                        .bigText(editText.text))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .build())
                current_notification_id++
            }
        }

    }

    private fun  createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}