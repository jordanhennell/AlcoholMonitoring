package project.alcoholmonitoring.utils;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.text.format.Time;

import java.lang.ref.WeakReference;
import java.util.Calendar;

import project.alcoholmonitoring.MainActivity;
import project.alcoholmonitoring.R;

/**
 * Created by xxx on 2016/5/15 0015.
 */
public class AlarmHelper {

        public static final String ACTION_ALCOHOL_ALARM = "project.alcoholmonitoring.action.ACTION_ALCOHOL_ALARM";

        private static final String EXTRA_ALCOHOL_ALARM_DAY = "extra_alcohol_alarm_day";

        private static final long ONE_DAY_MILLIS = 24 * 60 * 60 * 1000L;

        private static WeakReference<AlarmHelper> sInstanceRef;

        private Context mContext;
        private AlarmManager mAlarmManager;

        public static synchronized AlarmHelper getInstance(Context context) {
            if (sInstanceRef != null && sInstanceRef.get() != null) {
                return sInstanceRef.get();
            }

            AlarmHelper instance = new AlarmHelper(context);
            sInstanceRef = new WeakReference<AlarmHelper>(instance);
            return instance;
        }

        private AlarmHelper(Context context) {
            mContext = context.getApplicationContext();
            mAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        }

        public void clear() {
            cancelAlarm();
            sInstanceRef.clear();
        }

        public void setAlcoholAlarm(long timeMillis) {
            Intent intent = new Intent(ACTION_ALCOHOL_ALARM);
            setAlarm(intent, true, timeMillis, ONE_DAY_MILLIS);
        }

        private void setAlarm(Intent intent, boolean isRepeating,
                              long triggerAtMillis, long intervalMillis) {
            PendingIntent pi = PendingIntent.getBroadcast(
                    mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            if (isRepeating) {
                mAlarmManager.setRepeating(
                        AlarmManager.RTC_WAKEUP, triggerAtMillis, intervalMillis, pi);
            } else {
                mAlarmManager.set(AlarmManager.RTC_WAKEUP, triggerAtMillis, pi);
            }
        }

        public void onReceive(Intent intent) {
            if (ACTION_ALCOHOL_ALARM.equals(intent.getAction())) {
                handleAlocholAlarm(intent);
            }
        }

        public void cancelAlarm() {
            PendingIntent pi = PendingIntent.getBroadcast(mContext, 0,
                    new Intent(ACTION_ALCOHOL_ALARM), PendingIntent.FLAG_UPDATE_CURRENT);
            mAlarmManager.cancel(pi);
        }

        private void handleAlocholAlarm(Intent intent) {
            String title = "Alcohol Notice";
            String content = "Hello ! Alcohol Notice";
            notification(title, content);
        }

        private void notification(String title, String content) {
            NotificationManager mNotificationManager = (NotificationManager) mContext
                    .getSystemService(Context.NOTIFICATION_SERVICE);
            Intent intent = new Intent(mContext, MainActivity.class);
            PendingIntent pi = PendingIntent.getActivity(
                    mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            if (TextUtils.isEmpty(title)) {
                title = mContext.getString(R.string.app_name);
            }

            Notification notification = new NotificationCompat.Builder(mContext)
                    .setContentIntent(pi)
                    .setContentTitle(title)
                    .setContentText(content)
                    .setWhen(System.currentTimeMillis())
                    .setDefaults(Notification.DEFAULT_LIGHTS)
                    .setSmallIcon(R.drawable.ic_action_calendar_day)
                    .setAutoCancel(true)
                    .build();
            notification.defaults |= Notification.DEFAULT_SOUND;
            notification.defaults |= Notification.DEFAULT_VIBRATE;

            mNotificationManager.notify("ALCOHOL", 0, notification);
        }
    }
