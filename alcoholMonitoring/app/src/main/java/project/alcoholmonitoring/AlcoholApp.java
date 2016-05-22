package project.alcoholmonitoring;

import android.app.Application;

import com.activeandroid.ActiveAndroid;

import project.alcoholmonitoring.utils.DatabaseHelper;

/**
 * Created by YuxiaoXue on 2016/5/14 0014.
 */
public class AlcoholApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this
          );
    }
}
