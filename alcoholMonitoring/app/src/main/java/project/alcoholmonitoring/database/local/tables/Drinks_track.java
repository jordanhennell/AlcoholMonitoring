package project.alcoholmonitoring.database.local.tables;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by jason_liu on 14/05/16.
 */
@Table(name = "DrinksTrack")
public class Drinks_track {
    @Column(name = "Date")
    public String date;

    @Column(name ="Type")
    public String dtype;

    @Column(name ="Volume")
    public String dvolume;

    @Column(name ="Mood")
    public int mood_index;

    public Drinks_track() {
        super();
    }

    public Drinks_track(String date, String dtype, String dvolume, int mood_index) {
        super();
        this.date = date;
        this.dtype = dtype;
        this.dvolume = dvolume;
        this.mood_index = mood_index;
    }
}
