package com.example.pruebawear;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.pruebawear.databinding.ActivityMainBinding;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {

    private TextView mTextView;
    private ActivityMainBinding binding;
    private Button wButton = null;
    private Intent intent;
    private PendingIntent pendingIntent;

    private NotificationCompat.Builder notification_uno;
    private NotificationCompat.Builder notification_dos;
    private NotificationManagerCompat nm;
    private NotificationCompat.WearableExtender wearableExtender;

    String idChannel = "Mi_canal";
    int idNotification = 001;
    private NotificationCompat.BigTextStyle bigTextStyle;
    String longText = "Without BigStyle, only a single line of text would be visible"
                    + "Any additional text would not appear directly in the notification";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        wButton = findViewById(R.id.wButton);
        intent = new Intent(MainActivity.this, MainActivity.class);
        nm = NotificationManagerCompat.from(MainActivity.this);
        wearableExtender = new NotificationCompat.WearableExtender();
        bigTextStyle = new NotificationCompat.BigTextStyle().bigText(longText);
        Timer t = new Timer();

        wButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int importance = NotificationManager.IMPORTANCE_HIGH;
                String name = "Notificaci??n";
                NotificationChannel notificationChannel =
                        new NotificationChannel(idChannel, name, importance);
                nm.createNotificationChannel(notificationChannel);
                pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, 0);

                /*notification = new NotificationCompat.Builder(MainActivity.this, idChannel)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Notificaci??n Wear")
                        .setContentText(longText)
                        .setContentIntent(pendingIntent)
                        .extend(wearableExtender)
                        .setVibrate(new long[]{100,200,300,400,500,400,300,200,400})
                        .setStyle(bigTextStyle);*/

                notification_uno = new NotificationCompat.Builder(MainActivity.this, idChannel)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Notificaci??n Wear")
                        .setContentText("Mi primera notificaci??n wear")
                        .extend(wearableExtender);

                notification_dos = new NotificationCompat.Builder(MainActivity.this, idChannel)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Otra Notificaci??n Wear")
                        .setContentText("Esta es otra notificaci??n")
                        .extend(wearableExtender);

                //nm.notify(idNotification, notification_uno.build());

                TimerTask tt = new TimerTask() {
                    public void run() {
                        nm.notify(idNotification, notification_uno.build());
                        nm.notify(idNotification+1, notification_dos.build());
                    }
                };

                t.schedule(tt, 0, 1000);
            }


        });
    }
}