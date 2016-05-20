package project.alcoholmonitoring.utils;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.text.TextUtils;

import java.sql.Date;

import project.alcoholmonitoring.entry.DrinkingRecord;
import project.alcoholmonitoring.entry.Profile;

/**
 * Created by wuyan on 2016/5/14 0014.
 */
public class DataHelper {

    private static final String TAG = "DataHelper";

    private static final String NAME_SHARED_PREFS = "light_weight_data";

    private DataHelper() {
    }

    /**
     * Insert Drinking Record
     * @param context
     * @param record
     * @return
     */
    public static synchronized boolean insertDrinkingRecord(Context context, DrinkingRecord record) {
        if (record == null || context == null) {
            return false;
        }
        SQLiteDatabase db = getWritableDatabase(context);
        if (db == null) {
            return false;
        }
        long id = db.insert(DrinkingRecord.TABLE_NAME, null, fillDrinkingInfo(record));
        db.close();
        if (id == -1) {
            Utils.logE(TAG, "insert drinking info error");
        } else {
            Utils.logD(TAG, "insert drinking info succ ");
        }
        return id != -1;
    }

    /**
     * Get Drinking Record by date
     * @param context
     * @param date
     * @return
     */
    public static DrinkingRecord getRecordByDate(Context context, String date) {
        if (context == null || TextUtils.isEmpty(date)) {
            return null;
        }
        SQLiteDatabase db = getReadableDatabase(context);
        if (db == null) {
            return null;
        }
        Cursor cursor = db.query(DrinkingRecord.TABLE_NAME, null, DrinkingRecord.TIME + "='" + date + "'", null, null, null, null, null);
        DrinkingRecord record = loadRecord(cursor);
        cursor.close();
        db.close();
        return record;
    }

    private static DrinkingRecord loadRecord(Cursor cursor) {
        int indexVolume = cursor.getColumnIndex(DrinkingRecord.VOLUME);
        int indexDegree = cursor.getColumnIndex(DrinkingRecord.DEGREE);
        int indexDuration = cursor.getColumnIndex(DrinkingRecord.DURATION);
        int indexBeverage = cursor.getColumnIndex(DrinkingRecord.BEVERAGE);
        int indexDate = cursor.getColumnIndex(DrinkingRecord.TIME);
        int indexCost = cursor.getColumnIndex(DrinkingRecord.COST);
        int indexFav = cursor.getColumnIndex(DrinkingRecord.FAVOURITE);
        DrinkingRecord record = null;
        while (cursor.moveToNext()) {
            record = new DrinkingRecord();
            record.volume = cursor.getFloat(indexVolume);
            record.degree = cursor.getFloat(indexDegree);
            record.duration = cursor.getInt(indexDuration);
            record.beverage = cursor.getString(indexBeverage);
            record.cost = cursor.getFloat(indexCost);
            record.time = cursor.getString(indexDate);
            record.favourite = cursor.getInt(indexFav) == 1;
        }
        return record;
    }

    private static ContentValues fillDrinkingInfo(DrinkingRecord record) {
        ContentValues value = new ContentValues();
        value.put(DrinkingRecord.VOLUME, record.volume);
        value.put(DrinkingRecord.DURATION, record.duration);
        value.put(DrinkingRecord.DEGREE, record.degree);
        value.put(DrinkingRecord.BEVERAGE, record.beverage);
        value.put(DrinkingRecord.COST, record.cost);
        value.put(DrinkingRecord.TIME, record.time);
        value.put(DrinkingRecord.FAVOURITE, record.favourite);
        return value;
    }

    /**
     * Insert profile info
     * @param context
     * @param profile
     * @return
     */
    public static synchronized boolean insertProfileInfo(Context context, Profile profile) {
        if (context == null || profile == null) {
            return false;
        }
        SQLiteDatabase db = getWritableDatabase(context);
        if (db == null) {
            return false;
        }
        long id = db.insert(Profile.TABLE_NAME, null, fillProfile(profile));
        db.close();
        if (id == -1) {
            Utils.logE(TAG, "insert profile info error");
        } else {
            Utils.logD(TAG, "insert profile info success");
        }
        return id != -1;
    }

    /**
     * Update profile info
     * @param context
     * @param profile
     * @return
     */
    public static boolean updateProfileInfo(Context context, Profile profile) {
        if (context == null || profile == null) {
            return false;
        }
        SQLiteDatabase db = getWritableDatabase(context);
        if (db == null) {
            return false;
        }
        int count = db.update(Profile.TABLE_NAME, fillProfile(profile), Profile.ID + "='" + profile.id + "'", null);
        db.close();
        return count > 0;
    }

    /**
     * Get profile info
     * @param context
     * @return
     */
    public static Profile loadProfile(Context context) {
        if (context == null) {
            return null;
        }
        SQLiteDatabase db = getReadableDatabase(context);
        if (db == null) {
            return null;
        }
        Cursor c = db.query(Profile.TABLE_NAME, null, null, null, null, null, null);
        Profile profile = fillProfile(c);
        c.close();
        db.close();
        return profile;
    }

    private  static Profile fillProfile(Cursor cursor) {
        int indexName = cursor.getColumnIndex(Profile.NAME);
        int indexId = cursor.getColumnIndex(Profile.ID);
        int indexWeight = cursor.getColumnIndex(Profile.WEIGHT);
        int indexGender = cursor.getColumnIndex(Profile.GENDER);
        int indexBmi = cursor.getColumnIndex(Profile.BMI);
        Profile profile = null;
        if (cursor.moveToNext()) {
            profile = new Profile();
            profile.id = cursor.getInt(indexId);
            profile.name = cursor.getString(indexName);
            profile.gender = cursor.getInt(indexGender);
            profile.weight = cursor.getInt(indexWeight);
            profile.bmi = cursor.getInt(indexBmi);
        }
        return profile;
    }

    private static ContentValues fillProfile(Profile profile) {
        ContentValues values = new ContentValues();
        values.put(Profile.NAME, profile.name);
        values.put(Profile.GENDER, profile.gender);
        values.put(Profile.WEIGHT, profile.weight);
        values.put(Profile.BMI, profile.bmi);
        return values;
    }

    public static SQLiteDatabase getReadableDatabase(Context context) {
        if (context == null) {
            return null;
        }
        try {
            return new DatabaseHelper(context).getReadableDatabase();
        } catch (SQLiteException e) {
            Utils.logE(TAG, e.getMessage());
        }
        return null;
    }

    public static SQLiteDatabase getWritableDatabase(Context context) {
        if (context == null) {
            return null;
        }
        try {
            return new DatabaseHelper(context).getWritableDatabase();
        } catch (SQLiteException e) {
            Utils.logE(TAG, e.getMessage());
        }
        return null;
    }

    public static SharedPreferences getSpForData(Context ctx) {
        return ctx.getSharedPreferences(NAME_SHARED_PREFS, 0);
    }

}
