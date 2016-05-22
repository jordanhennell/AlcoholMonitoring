package project.alcoholmonitoring.event;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import project.alcoholmonitoring.R;

public class ReminderService extends IntentService {

        public ReminderService() {
            super("Reminder service");
        }

        @Override
        protected void onHandleIntent(Intent intent) {
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(R.drawable.ic_cake)
                            .setContentTitle("My notification")
                            .setContentText("Hello World!");

            Random r = new Random();
            int mNotificationId = (r.nextInt());

            NotificationManager mNotifyMgr =
                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            mNotifyMgr.notify(mNotificationId, mBuilder.build());

        }

        public void createNotification(Date reminderForTime, long triggerFromTime, Activity fromActivity) {

            Calendar currentDate = Calendar.getInstance();
            long timeDifference = currentDate.getTime().getTime() - (reminderForTime.getTime() - triggerFromTime);

            AlarmManager mgr = (AlarmManager) fromActivity.getSystemService(Context.ALARM_SERVICE);

            Intent intent = new Intent(fromActivity, ReminderService.class);
            PendingIntent pi = PendingIntent.getService(fromActivity, 0, intent, 0);

            mgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    SystemClock.elapsedRealtime() + timeDifference,
                    pi);
        }

}
