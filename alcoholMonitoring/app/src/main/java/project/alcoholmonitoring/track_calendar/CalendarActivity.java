package project.alcoholmonitoring.track_calendar;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import project.alcoholmonitoring.R;
import project.alcoholmonitoring.alcohol_details.AlcoholDetailsTrackActivity;
import project.alcoholmonitoring.event.EventActivity;

/**
 * Created by jieliang on 1/05/2016.
 */
public class CalendarActivity extends AppCompatActivity
//implements

{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.track_calendar);
        final Button addEvent=(Button)findViewById(R.id.addEvent);
        final Button viewDrinkStatus=(Button)findViewById(R.id.ViewStatus);

        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialCalendarView cv = (MaterialCalendarView)findViewById(R.id.calendarView);

                Intent intent = new Intent(CalendarActivity.this, EventActivity.class);
                intent.putExtra("chosen_date", cv.getSelectedDate());
                startActivity(intent);
            }
        });
        //// TODO: 27/05/16 use view status button for now
        viewDrinkStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialCalendarView cv = (MaterialCalendarView)findViewById(R.id.calendarView);

                Intent intent = new Intent(CalendarActivity.this, AlcoholDetailsTrackActivity.class);
                intent.putExtra("chosen_date", cv.getSelectedDate());
                startActivity(intent);
            }
        });



    }
}
