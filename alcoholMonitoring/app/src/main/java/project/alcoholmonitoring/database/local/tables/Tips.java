package project.alcoholmonitoring.database.local.tables;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by jason_liu on 14/05/16.
 */
@Table(name = "Tips")
public class Tips extends Model{
   @Column(name = "Content")
   public String name;

    public Tips() {
        super();
    }

    public Tips(String name) {
        super();
        this.name = name;
    }
}