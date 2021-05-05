package ca.on.conestogac.prog3210.erasflashcards;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.util.Timer;
import java.util.TimerTask;

public class BroadcastService extends Service {
    public BroadcastService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        final Timer timer = new Timer(true);
        final NotificationManager notifManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        final Notification localNotif = new Notification.Builder(getApplicationContext())
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(getString(R.string.broadcast_Title))
                .setContentText(getString(R.string.broadcast_batteryText))
                .setAutoCancel(true)
                .build();

        timer.schedule(new TimerTask(){
            @Override
            public void run() {
                notifManager.notify(1, localNotif);
                stopSelf();
            }
        },5000);

        super.onCreate();
    }
}