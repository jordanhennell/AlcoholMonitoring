package project.alcoholmonitoring.TrackCalendar;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;
import project.alcoholmonitoring.R;

/**
 * Created by jieliang on 1/05/2016.
 */
public class CalendarActivity extends Activity
//implements

{
    final Button addEvent=(Button)findViewById(R.id.addEvent);
    final Button viewDrinkStatus=(Button)findViewById(R.id.ViewStatus);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.track_calendar);


    }
}
