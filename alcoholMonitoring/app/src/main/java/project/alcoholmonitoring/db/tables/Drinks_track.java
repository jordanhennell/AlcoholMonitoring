package project.alcoholmonitoring.db.tables;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.sql.Date;

/**
 * Created by jason_liu on 14/05/16.
 */
@Table(name = "DrinksTrack")
public class Drinks_track extends Model{
    @Column(name = "Date")
    public Integer date;

    @Column(name ="Type")
    public String dtype;

    @Column(name ="Volume")
    public Integer dvolume;

    @Column(name = "Strength")
    public Integer strength;

    @Column(name ="Mood")
    public String mood;

    @Column(name = "Qantity")
    public Integer quantity;

    public Drinks_track() {
        super();
    }

    public Drinks_track(Integer date, String dtype, Integer dvolume, Integer strength,String mood,Integer quantity) {
        super();
        this.date = date;
        this.dtype = dtype;
        this.dvolume = dvolume;
        this.strength=strength;
        this.mood = mood;
        this.quantity=quantity;
    }

    public static Drinks_track getDateStatus(Integer date)
    {
// TODO: 21/05/16 get query date from calendar 
        return new Select().from(Drinks_track.class).where("Date=?",date.toString()).executeSingle();
    }

    //// TODO: 27/05/16 see if record is stored
    public static void insert_record(Integer date, String dtype, Integer dvolume,Integer strength,String mood,Integer quantity )
    {
       // ActiveAndroid.beginTransaction();
        Drinks_track drinks_track=new Drinks_track(date,dtype,dvolume,strength,mood,quantity);
        drinks_track.save();
        //ActiveAndroid.endTransaction();
    }

    public static int checkEmpty()
    {

        return new Select().from(Drinks_track.class).count();

    }

}
