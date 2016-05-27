package project.alcoholmonitoring.alcohol_details;


import android.widget.TextView;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by jason_liu on 27/05/16.
 */
public class data_converter {


    public static Integer string_convert_int (TextView textView)

    {
        return  Integer.parseInt(textView.getText().toString());
    }

    public static Integer date_convert_to_int (Date date)
    {
        if (date == null) {
            return null;
        }
        DateFormat df = new SimpleDateFormat("ddMMyyyy");
        String s=df.format(date);
        return Integer.valueOf(s) ;
    }

    public static Date int_convert_to_date(Integer int_date)
    {
        if (int_date== null)
        {return  null;}
        DateFormat df = new SimpleDateFormat("ddMMyyyy");
        try {
            return df.parse(String.valueOf(int_date));
        } catch (ParseException e) {
            e.printStackTrace();
            return  null;
        }


    }

}
