package project.alcoholmonitoring;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import project.alcoholmonitoring.alcohol_details.AlcoholDetailsTrack;
import project.alcoholmonitoring.track_calendar.CalendarActivity;
import project.alcoholmonitoring.view.DrinkingRecordActivity;
import project.alcoholmonitoring.view.RecordViewActivity;
import project.alcoholmonitoring.view.SettingsActivity;
import project.alcoholmonitoring.view.WeeklySummaryActivity;
import project.alcoholmonitoring.entry.DrinkingRecord;
import project.alcoholmonitoring.utils.DataHelper;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @ViewInject(R.id.list_record)
    private ListView mListView;

    private String[] timeStr = new String[7];
    private String[] showTimeStr = new String[7];

    private int mTodayIndex;

    private ArrayList<DrinkingRecord> mDrinkingRecords;

    private DrinkingRecordAdapter mAdapter;

    private LayoutInflater mInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewUtils.inject(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        mInflater = LayoutInflater.from(this);
        mDrinkingRecords = new ArrayList<>();
        getCurrentWeekDate();
        loadDrinkingRecords();
        mAdapter = new DrinkingRecordAdapter();
        mListView.setAdapter(mAdapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_camera:
                startActivity(AlcoholDetailsTrack.class);
                break;
            case R.id.nav_calendar:
                startActivity(CalendarActivity.class);
                break;
            case R.id.nav_slideshow:
                startActivity(DrinkingRecordActivity.class);
                break;
            case R.id.nav_manage:

                break;
            case R.id.nav_setting:
                startActivity(SettingsActivity.class);
                break;
            case R.id.nav_share:

                break;

            case R.id.nav_send:

                break;

            default:
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void startActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        AlarmHelper.getInstance(this).clear(); // Cancle the alarm notice
//    }

    @OnClick({R.id.tv_menu, R.id.tv_statistics, R.id.tv_settings})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_settings:
                startActivity(SettingsActivity.class);
                break;
            case R.id.tv_statistics:
                Intent intent = new Intent(this, WeeklySummaryActivity.class);
                intent.putExtra(DrinkingRecord.EXT_DATA_INFO, mDrinkingRecords);
                startActivity(intent);
                break;
            case R.id.tv_menu:
                getCurrentWeekDate();
                break;
        }
    }

    private String getDateString(int weekIndex, int day, int monthIndex) {
        int index = weekIndex - 1  == -1 ? 6 : weekIndex - 1;
        return project.alcoholmonitoring.constant.Constant.WEEK_STR[index] + "\n" + day + " , " + project.alcoholmonitoring.constant.Constant.MONTH_STR[monthIndex];
    }

    private void getCurrentWeekDate() {
        SimpleDateFormat dateFormate = new SimpleDateFormat("yyyy/MM/dd");
        String today = dateFormate.format(new Date());
        int day = new Date().getDay();
        day = day == 0 ? 7 : day;
        long time = new Date().getTime() - day * 24 * 3600000;
        for (int i = 1; i <= 7; i++) {
            Date date = new Date();
            date.setTime(time + (i * 24 * 3600000));
            String dayTime = dateFormate.format(date.getTime());
            if (TextUtils.equals(today, dayTime)) {
                mTodayIndex = i - 1;
            }
            timeStr[i - 1] = dayTime;
            showTimeStr[i - 1] = getDateString(date.getDay(), date.getDate(), date.getMonth());
        }
    }

    private void loadDrinkingRecords() {
        for (int i = 0 ; i < timeStr.length ; i ++) {
            mDrinkingRecords.add(DataHelper.getRecordByDate(this, timeStr[i]));
        }
    }

    private class DrinkingRecordAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return 7;
        }

        @Override
        public DrinkingRecord getItem(int position) {
            return mDrinkingRecords.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.list_item_week_record, parent, false);
                viewHolder.mTvDate = (TextView) convertView.findViewById(R.id.tv_date);
                viewHolder.ivFlag = (ImageView) convertView.findViewById(R.id.iv_icon);
                viewHolder.mTvNotice = (TextView) convertView.findViewById(R.id.tv_notice);
                viewHolder.mBtnOpt = (TextView) convertView.findViewById(R.id.btn_opt);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.mTvDate.setText(showTimeStr[position]);
            DrinkingRecord record = getItem(position);
            if (record == null) {
                if (position > mTodayIndex) {
                    // 今天之后的时间段
                    viewHolder.mBtnOpt.setVisibility(View.GONE);
                    viewHolder.mTvNotice.setVisibility(View.INVISIBLE);
                    viewHolder.ivFlag.setVisibility(View.GONE);
                } else if (position == mTodayIndex) {
                    // 今天
                    viewHolder.mBtnOpt.setText("Edit Record");
                    viewHolder.mTvNotice.setVisibility(View.VISIBLE);
                    viewHolder.mBtnOpt.setVisibility(View.VISIBLE);
                    viewHolder.mBtnOpt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(MainActivity.this, DrinkingRecordActivity.class);
                            startActivityForResult(intent , 100);
                        }
                    });
                } else {
                    viewHolder.mBtnOpt.setText("Dry day!");
                    viewHolder.mTvNotice.setVisibility(View.INVISIBLE);
                    viewHolder.mBtnOpt.setVisibility(View.VISIBLE);
                    viewHolder.ivFlag.setImageResource(R.drawable.ic_cb_off);
                    viewHolder.ivFlag.setVisibility(View.VISIBLE);

                }
            } else {
                viewHolder.mBtnOpt.setText("View Record");
                viewHolder.mTvNotice.setVisibility(View.INVISIBLE);
                viewHolder.mBtnOpt.setVisibility(View.VISIBLE);
                viewHolder.ivFlag.setImageResource(R.drawable.ic_cb_on);
                viewHolder.ivFlag.setVisibility(View.VISIBLE);
                viewHolder.mBtnOpt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // view record
                        Intent intent = new Intent(MainActivity.this, RecordViewActivity.class);
                        intent.putExtra(DrinkingRecord.EXT_DATA_INFO, mDrinkingRecords.get(position));
                        intent.putExtra(RecordViewActivity.DAY_TIME_INFO, showTimeStr[position]);
                        startActivity(intent);
                    }
                });
            }
            return convertView;
        }
    }

    class ViewHolder {
        TextView mTvDate;
        ImageView ivFlag;
        TextView mTvNotice;
        TextView mBtnOpt;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            DrinkingRecord record = data.getParcelableExtra(DrinkingRecord.EXT_DATA_INFO);
            mDrinkingRecords.remove(mTodayIndex);
            mDrinkingRecords.add(mTodayIndex, record);
            mAdapter.notifyDataSetChanged();
        }
    }

}
