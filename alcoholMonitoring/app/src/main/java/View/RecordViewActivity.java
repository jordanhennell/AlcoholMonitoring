package View;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import project.alcoholmonitoring.R;
import project.alcoholmonitoring.entry.DrinkingRecord;
import project.alcoholmonitoring.entry.Profile;
import project.alcoholmonitoring.utils.DataHelper;

/**
 * Created by xxx on 2016/5/15 0015.
 */
public class RecordViewActivity extends Activity {

    public static final String DAY_TIME_INFO = "day_time_info";

    @ViewInject(R.id.tv_volume)
    private TextView mTvVolume;
    @ViewInject(R.id.tv_degree)
    private TextView mTvDegree;
    @ViewInject(R.id.tv_beverages)
    private TextView mTvBeverages;
    @ViewInject(R.id.tv_cost)
    private TextView mTvCost;
    @ViewInject(R.id.tv_est)
    private TextView mTvEst;
    @ViewInject(R.id.tv_date)
    private TextView mTvDate;

    private DrinkingRecord mDrinkingRecord;

    private Profile mProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_record);
        ViewUtils.inject(this);
        mDrinkingRecord = getIntent().getParcelableExtra(DrinkingRecord.EXT_DATA_INFO);
        String time = getIntent().getStringExtra(DAY_TIME_INFO);
        time = time.replace("\n" , "  ");//把时间字符串中的换行符("\n")替换成空格，以免显示的时候显示成了两行
        mTvDate.setText(time);
        if (mDrinkingRecord == null) {
            finish();
            return;
        }
        mProfile = DataHelper.loadProfile(this);

        mTvVolume.setText(getString(R.string.volume_value, mDrinkingRecord.volume));
        mTvDegree.setText(getString(R.string.degree_value, mDrinkingRecord.degree));
        mTvBeverages.setText(mDrinkingRecord.beverage);
        mTvCost.setText(getString(R.string.cost_value, mDrinkingRecord.cost));
        mTvEst.setText(getString(R.string.degree_value, calcValue()));
    }

    private float calcValue() {
        if (mProfile == null) {
            return 0f;
        }
        double fWeight = mProfile.weight * 0.8;
        if (fWeight == 0) {
            return  0f;
        }
        return (float) ((mDrinkingRecord.volume * mDrinkingRecord.degree) / fWeight);
    }
}
