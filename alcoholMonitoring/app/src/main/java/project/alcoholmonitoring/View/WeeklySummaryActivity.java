package project.alcoholmonitoring.View;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

import project.alcoholmonitoring.R;
import project.alcoholmonitoring.entry.DrinkingRecord;

/**
 * Created by YuxiaoXue on 2016/5/15 0015.
 */
public class WeeklySummaryActivity extends Activity implements OnChartValueSelectedListener {

    private static final String[] WEEK_LIST = new String[]{"Mon", "Tue", "Wed", "Thr", "Fri", "Sat", "Sun"};

    @ViewInject(R.id.chart)
    private BarChart mChart;
    @ViewInject(R.id.tv_check)
    private TextView mTvButton;
    private Typeface tf;

    private ArrayList<DrinkingRecord> mDrinkingRecords;
    private ArrayList<String> mXfValues;
    private ArrayList<BarEntry> mBarEntryCost;
    private ArrayList<BarEntry> mBarEntryVolume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_summary);
        ViewUtils.inject(this);
        mDrinkingRecords = getIntent().getParcelableArrayListExtra(DrinkingRecord.EXT_DATA_INFO);
        mChart.setOnChartValueSelectedListener(this);
        mChart.setDescription("");

//        mChart.setDrawBorders(true);//是否显示柱状图的边框线

        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);
        mChart.setDrawBarShadow(false);
        mChart.setDrawGridBackground(false);
        tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTypeface(tf);
        xAxis.setDrawGridLines(false);
        xAxis.setSpaceBetweenLabels(2);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setTypeface(tf);
        leftAxis.setValueFormatter(new LargeValueFormatter());
        leftAxis.setDrawGridLines(false);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(30f);
        leftAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setTypeface(tf);
        rightAxis.setValueFormatter(new LargeValueFormatter());
        rightAxis.setDrawGridLines(false);
        rightAxis.setSpaceTop(30f);
        rightAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)

        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setTypeface(tf);
        l.setTextSize(8f);
        l.setXEntrySpace(4f);

//        mChart.getAxisRight().setEnabled(true); //是否显示右侧的Y轴
        mChart.setDrawValueAboveBar(true); //是否需要在柱状图上方显示数值

        fillChartDatas();
    }

    /**
     * 填充数据
     */
    private void fillChartDatas() {
        mXfValues = new ArrayList<>(); // X轴方向的数据集合
        mBarEntryCost = new ArrayList<>(); //Cost数据集合
        mBarEntryVolume = new ArrayList<>(); // Volume数据集合
        boolean hasRecord = mDrinkingRecords != null;
        for (int i = 0; i < 7; i++) {
            mXfValues.add(WEEK_LIST[i]);
            BarEntry entryCost;
            BarEntry entryVolume;
            if (hasRecord && mDrinkingRecords.get(i) != null) {
                DrinkingRecord record = mDrinkingRecords.get(i);
                entryCost = new BarEntry(record.cost, i);
                entryVolume = new BarEntry(record.volume, i);
            } else {
                entryCost = new BarEntry(0f, i);
                entryVolume = new BarEntry(0f, i);
            }
            mBarEntryCost.add(entryCost);
            mBarEntryVolume.add(entryVolume);
        }
        BarDataSet set1, set2;
        set1 = new BarDataSet(mBarEntryCost, "Cost");
        set1.setColor(Color.rgb(104, 241, 175));
        set2 = new BarDataSet(mBarEntryVolume, "Volume");
        set2.setColor(Color.rgb(164, 228, 251));
        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(set1);
        dataSets.add(set2);
        BarData data = new BarData(mXfValues, dataSets);

        // add space between the dataset groups in percent of bar-width
        data.setGroupSpace(80f);
        data.setValueTypeface(tf);

        mChart.setData(data);
        mChart.invalidate();

    }

    public  void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_check:
                Toast.makeText(this,"Check achievements", Toast.LENGTH_SHORT).show();
                break;
            default:break;
        }
    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }
}
