package project.alcoholmonitoring.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by xxxx on 2016/5/13 0013.
 */
public class Utils {

    private static Context mContext;

    public static void init(Context context) {
        mContext = context;
    }

    public static void logD(String TAG, String content) {
        Log.d(TAG, content);
    }

    public static void logE(String TAG, String content) {
        Log.e(TAG, content);
    }
}
