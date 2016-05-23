package project.alcoholmonitoring.db.tables;

import android.graphics.AvoidXfermode;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by jason_liu on 21/05/16.
 */
@Table(name = "User_Physical")
public class User_Physical extends Model
{
    @Column(name ="UserName")
     public String name;

    @Column(name ="Weight")
    public Integer weight;

    @Column(name = "Gender")
    public String gender;

    @Column(name ="BMI")
    public Integer bmi;

    public User_Physical() {super();
    }

    public User_Physical(String name, Integer weight, String gender, Integer bmi) {
        this.name = name;
        this.weight = weight;
        this.gender = gender;
        this.bmi = bmi;
    }
}
