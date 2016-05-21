package project.alcoholmonitoring.event;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.text.DateFormat;
import java.util.Date;

import project.alcoholmonitoring.R;

public class EventActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_main);

        setUpReminderPopUp();

        Intent intent = getIntent();

        Bundle extras = intent.getExtras();
        Date chosenDate = ((CalendarDay)extras.get("chosen_date")).getDate();
        String stringDate = String.format("%1$tY %1$tb %1$td", chosenDate);
        TextView dateTextView = (TextView)findViewById(R.id.eventDateDisplay);
        dateTextView.setText(stringDate);
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

        switch (item.getItemId()) {
            case R.id.reminder_10_minutes:
                reminderButton.setText(item.getTitle());
                // Create notification
                return true;
            case R.id.reminder_30_minutes:
                reminderButton.setText(item.getTitle());
                // Create notification
                return true;
            case R.id.reminder_1_hour:
                reminderButton.setText(item.getTitle());
                // Create notification
                return true;
            case R.id.reminder_3_hours:
                reminderButton.setText(item.getTitle());
                // Create notification
                return true;
            case R.id.reminder_6_hours:
                reminderButton.setText(item.getTitle());
                // Create notification
                return true;
            case R.id.reminder_1_day:
                reminderButton.setText(item.getTitle());
                // Create notification
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

}
