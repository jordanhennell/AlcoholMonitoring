package project.alcoholmonitoring.event;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.Calendar;
import java.util.Date;

import project.alcoholmonitoring.R;

public class EventActivity extends Activity {

    Date chosenDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_main);

        showChosenDate();
        setUpReminderPopUp();
        setUpTimePicker();
    }

    private void showChosenDate() {
        Bundle extras = getIntent().getExtras();

        chosenDate = ((CalendarDay)extras.get("chosen_date")).getDate();
        String stringDate = String.format("%1$tY %1$tb %1$td", chosenDate);

        TextView dateTextView = (TextView)findViewById(R.id.eventDateDisplay);
        dateTextView.setText(stringDate);
    }

    private void setUpTimePicker() {
        final Button chooseTimeButton = (Button)findViewById(R.id.eventChooseTimeButton);

        final TimePickerDialog tpd = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        chooseTimeButton.setText(hourOfDay + ":" + minute);
                    }
                }, 12, 0, false);

        chooseTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tpd.show();
            }
        });
    }

    private void setUpReminderPopUp() {
        Button reminderButton = (Button)findViewById(R.id.eventReminderButton);

        registerForContextMenu(reminderButton);

        reminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.showContextMenu();
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.event_reminder_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Button reminderButton = (Button)findViewById(R.id.eventReminderButton);
        int timeSpan = 0;

        switch (item.getItemId()) {
            case R.id.reminder_none:
                reminderButton.setText(item.getTitle());
                return true;
            case R.id.reminder_10_minutes:
                reminderButton.setText(item.getTitle());
                timeSpan = 600000;
                break;
            case R.id.reminder_30_minutes:
                reminderButton.setText(item.getTitle());
                timeSpan = 1800000;
                break;
            case R.id.reminder_1_hour:
                reminderButton.setText(item.getTitle());
                timeSpan = 3600000;
                break;
            case R.id.reminder_3_hours:
                reminderButton.setText(item.getTitle());
                timeSpan = 10800000;
                break;
            case R.id.reminder_6_hours:
                reminderButton.setText(item.getTitle());
                timeSpan = 21600000;
                break;
            case R.id.reminder_1_day:
                reminderButton.setText(item.getTitle());
                timeSpan = 86400000;
                break;
            default:
                return super.onContextItemSelected(item);
        }

        // TODO move creation of notification to on Save button click
        ReminderService rs = new ReminderService();
        rs.createNotification(chosenDate, timeSpan, this);
        return true;
    }

}
