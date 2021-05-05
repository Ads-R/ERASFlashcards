package ca.on.conestogac.prog3210.erasflashcards;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.util.Timer;
import java.util.TimerTask;

public class NotificationService extends Service {
    public NotificationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        final Timer timer = new Timer(true);
        final NotificationManager notifManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        final Intent intent = new Intent(getApplicationContext(), MainActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        final PendingIntent pendingIntent = PendingIntent
                .getActivity(getApplicationContext(),0, intent, 0);
        final Notification localNotif = new Notification.Builder(getApplicationContext())
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(getString(R.string.localNotif_title))
                .setContentText(getString(R.string.localNotif_text))
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
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

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}