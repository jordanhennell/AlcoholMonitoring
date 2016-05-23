package project.alcoholmonitoring.db.tables;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

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

    public static int checkEmpty()
    {

        return new Select().from(Tips.class).count();

    }
  public static Tips getRandom()
  {

      return new Select().from(Tips.class).orderBy("Random()").executeSingle();

  }

}
