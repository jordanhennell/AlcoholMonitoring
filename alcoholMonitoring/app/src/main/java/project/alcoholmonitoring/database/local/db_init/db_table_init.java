package project.alcoholmonitoring.database.local.db_init;


import com.activeandroid.ActiveAndroid;
import com.activeandroid.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import project.alcoholmonitoring.database.local.tables.Beverage_specs;

/**
 * Created by jason_liu on 14/05/16.
 */
public class db_table_init {

    public db_table_init() {

       BeverageInit();

        //test from log

        android.util.Log.d(Integer.toString(Beverage_specs.checkEmpty()),"data");
    }

// BeverageInit
    public void BeverageInit()
    {
        Beverage_specs beverage_specs=new Beverage_specs();
        //if(Beverage_specs.checkEmpty()==0) {
            //init 3 drinks
           String[]drinks = new String[]{"Beer", "Wine", "Spirit"};
            Double[] sstrength = new Double[]{0.05, 0.09, 0.3};
            ActiveAndroid.beginTransaction();
            try{
                 for(int i=0;i<3;i++)
                {
                    beverage_specs.name=drinks[i];
                    beverage_specs.strength=sstrength[i];
                    beverage_specs.save();
                }
                ActiveAndroid.setTransactionSuccessful();
            }
            finally {
                ActiveAndroid.endTransaction();
            }
       // }

    }
}
