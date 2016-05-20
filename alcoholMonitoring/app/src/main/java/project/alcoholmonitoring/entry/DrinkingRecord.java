package project.alcoholmonitoring.entry;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by YuxiaoXue on 2016/5/14 0014.
 */
public class DrinkingRecord implements Parcelable{

    public static final String TABLE_NAME = "tab_drink";

    public static  final  String EXT_DATA_INFO = "ext_data_info";

    // 消费记录表创建代码
    public static final String TABLE_DRINK_CREATSTR = "CREATE TABLE "
            + TABLE_NAME
            + " (_id INTEGER PRIMARY KEY,volume FLOAT, degree FLOAT,duration INTEGER," +
            "beverage VARCHAR(255),cost FLOAT, time DATE, favourite BOOLEAN)";

    /************************* KEY WORDS *************************/
    public static final String KEY_ID = "_id";
    public static final String VOLUME = "volume";
    public static final String DEGREE = "degree";
    public static final String DURATION = "duration";
    public static final String BEVERAGE = "beverage";
    public static final String COST = "cost";
    public static final String TIME = "time";
    public static final String FAVOURITE = "favourite";

    public float volume;
    public float degree;
    public int duration;
    public String beverage;
    public float cost;
    public String time;
    public boolean favourite;

    public DrinkingRecord(){}

    public static Creator CREATOR = new Creator() {
        @Override
        public DrinkingRecord createFromParcel(Parcel source) {
            return new DrinkingRecord(source);
        }

        @Override
        public DrinkingRecord[] newArray(int size) {
            return new DrinkingRecord[size];
        }
    };

    private DrinkingRecord(Parcel source) {
        volume = source.readFloat();
        degree = source.readFloat();
        duration = source.readInt();
        beverage = source.readString();
        cost = source.readFloat();
        time = source.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(volume);
        dest.writeFloat(degree);
        dest.writeInt(duration);
        dest.writeString(beverage);
        dest.writeFloat(cost);
        dest.writeString(time);
    }

}
