package project.alcoholmonitoring.db.db_init;


import com.activeandroid.ActiveAndroid;

import project.alcoholmonitoring.db.tables.Beverage_specs;
import project.alcoholmonitoring.db.tables.Drinks_track;
import project.alcoholmonitoring.db.tables.Tips;

/**
 * Created by jason_liu on 14/05/16.
 */
public class db_table_init {

    public db_table_init() {

       BeverageInit();
        TipsInit();
        //test from log
        android.util.Log.d(Integer.toString(Beverage_specs.checkEmpty()),"data");
        android.util.Log.d(Integer.toString(Drinks_track.checkEmpty()),"data_drinks");

    }

// BeverageInit
    public void BeverageInit()
    {
       // Beverage_specs beverage_specs=new Beverage_specs();
        if(Beverage_specs.checkEmpty()==0) {
            //init 3 drinks
           String[]drinks = new String[]{"Beer", "Wine", "Spirit"};
            Integer[] sstrength = new Integer[]{5, 9, 3};
            ActiveAndroid.beginTransaction();
            try{
                 for(int i=0;i<3;i++)
                {
                    Beverage_specs beverage_specs1=new Beverage_specs(drinks[i],sstrength[i]);
                    beverage_specs1.save();
                }
                ActiveAndroid.setTransactionSuccessful();
            }
            finally {
                ActiveAndroid.endTransaction();
            }
        }
    }
    public void TipsInit()
    {
        if(Tips.checkEmpty()==0)
        {
            String[] tips=new String[]{
              "Good job,buddy",
              "Keep it up!",
              "So many sober days already!"
            };
            ActiveAndroid.beginTransaction();
           try{
               for(int i=0;i<3;i++)
               {
                   Tips tips1=new Tips(tips[i]);
                   tips1.save();
               }
           }
           finally {
                  ActiveAndroid.endTransaction();
           }

        }


    }

}
