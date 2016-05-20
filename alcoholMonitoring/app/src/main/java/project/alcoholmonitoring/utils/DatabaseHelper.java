package project.alcoholmonitoring.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import project.alcoholmonitoring.entry.DrinkingRecord;
import project.alcoholmonitoring.entry.Profile;

/**
 * Created by xxx on 2016/5/14 0014.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    //数据库版本
    public static final int DB_VERSION = 1;
    //The database name
    public static final String DB = "db_alcohol";

    DatabaseHelper(Context context) {
        super(context, DB, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DrinkingRecord.TABLE_DRINK_CREATSTR);
        db.execSQL(Profile.TABLE_PROFILE_CREATSTR);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
