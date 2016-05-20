package project.alcoholmonitoring.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by YuxiaoXue on 2016/5/15 0015.
 */
public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        AlarmHelper.getInstance(context).onReceive(intent);
    }
}
