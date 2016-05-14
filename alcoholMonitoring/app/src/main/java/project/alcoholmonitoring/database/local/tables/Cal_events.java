package project.alcoholmonitoring.database.local.tables;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by jason_liu on 14/05/16.
 */
@Table(name = "CalEvents")
public class Cal_events extends Model{
    @Column(name = "Name")
    public String name;

    @Column(name = "Type")
    public String type;

    @Column(name = "Time")
    public String time;

    @Column(name = "WillDrink")
    public String will_drink;

    @Column(name = "Reminder")
    public String reminder_time;

    public Cal_events() {
        super();
    }
}