package project.alcoholmonitoring.entry;

/**
 * Created by YuxiaoXue on 2016/5/15 0015.
 */
public class Profile {

    public static final String TABLE_NAME = "tab_profile";

    // 消费记录表创建代码
    public static final String TABLE_PROFILE_CREATSTR = "CREATE TABLE "
            + TABLE_NAME
            + " (_id INTEGER PRIMARY KEY,name VARCHAR(255), weight INTEGER, gender INTEGER," +
            "bmi INTEGER)";

    /************************* KEY WORDS *************************/

    public static final  String ID = "_id";
    public static final  String NAME =  "name";
    public static final  String WEIGHT = "weight";
    public static final  String GENDER = "gender";
    public static final  String BMI = "bmi";

    public int id;
    public String name;
    public int weight;
    public int gender;
    public float bmi;
}
