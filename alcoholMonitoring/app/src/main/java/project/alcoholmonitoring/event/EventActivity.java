package project.alcoholmonitoring.event;

import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


import project.alcoholmonitoring.R;

public class EventActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_main);

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
