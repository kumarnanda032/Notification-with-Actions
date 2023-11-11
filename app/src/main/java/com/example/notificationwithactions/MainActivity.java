package com.example.notificationwithactions;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;


import android.widget.Button;


import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


public class MainActivity extends AppCompatActivity {

    private final String CHANNEL_ID = "Notification";

    private final int NOTIFICATION_ID = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button button = findViewById(R.id.button);

        button.setOnClickListener(v -> {

            createNotificationChannel();

            Intent homeIntent = new Intent(getApplicationContext(), MainActivity.class);

            Intent webIntent = new Intent(Intent.ACTION_VIEW);
            webIntent.setData(Uri.parse("http://www.google.com"));

            PendingIntent pendingIntentOne = PendingIntent.getActivity(getApplicationContext(), 1, homeIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            PendingIntent pendingIntentTwo = PendingIntent.getActivity(getApplicationContext(), 1, webIntent, PendingIntent.FLAG_UPDATE_CURRENT);


            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                    .setSmallIcon(R.drawable.notification)
                    .setContentTitle("Notification With Action")
                    .setShowWhen(true)
                    .setAutoCancel(true)
                    .setContentText("Android Studio Tutorials")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .addAction(R.drawable.ic_baseline_home_24, "HOME", pendingIntentOne)
                    .addAction(R.drawable.ic_baseline_web_24, "GOOGLE", pendingIntentTwo);


            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());

            //notificationId is a unique int for each notification that you must define
            if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
              
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            notificationManager.notify(NOTIFICATION_ID, builder.build());

        });


    }


    //create notification channel for android 8.0 or higher to deliver notification
    private void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            CharSequence name = "Notification";
            String description = "Simple Notification";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            notificationChannel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);

        }
    }
}