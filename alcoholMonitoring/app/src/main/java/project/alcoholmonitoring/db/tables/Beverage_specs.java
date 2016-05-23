package project.alcoholmonitoring.db.tables;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

/**
 * Created by jason_liu on 14/05/16.
 */

@Table(name = "BeverageSpecs")
public class Beverage_specs extends Model {
    @Column(name = "Name")
    public String name;

    @Column(name = "Strength")
    public Integer strength;


    public Beverage_specs() {
        super();
    }

    public Beverage_specs(String name, Integer strength) {
        super();
        this.name = name;
        this.strength = strength;
    }
    //queries
    public static int checkEmpty()
    {

        return new Select().from(Beverage_specs.class).count();

    }

    public static Beverage_specs getAll()
    {
        return new Select().from(Beverage_specs.class).executeSingle();
    }

}
