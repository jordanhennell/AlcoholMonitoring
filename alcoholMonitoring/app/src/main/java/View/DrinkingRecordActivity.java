package View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import project.alcoholmonitoring.R;
import project.alcoholmonitoring.entry.DrinkingRecord;
import project.alcoholmonitoring.utils.DataHelper;

/**
 * Created by xxx on 2016/5/13 0013.
 */
public class DrinkingRecordActivity extends Activity {

    @ViewInject(R.id.et_volume)
    private EditText mEtVolume;
    @ViewInject(R.id.et_degree)
    private EditText mEtDegree;
    @ViewInject(R.id.et_duration)
    private EditText mEtDuration;
    @ViewInject(R.id.et_cost)
    private EditText mEtCost;
    @ViewInject(R.id.spinner)
    private Spinner mSpinner;
    @ViewInject(R.id.btn_save)
    private Button mBtnSave;
    @ViewInject(R.id.btn_favourite)
    private Button mBtnFavorite;
    @ViewInject(R.id.btn_cancle)
    private Button mBtnCancle;

    private  String mBeverage;

    private List<String> mSpinnerList;

    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drinking_record);
        ViewUtils.inject(this);
        mSpinnerList = new ArrayList<String>();
        mSpinnerList.add("beer");
        mSpinnerList.add("coco");
        mSpinnerList.add("coffee");
        mSpinnerList.add("orange Juice");
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,mSpinnerList);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(mAdapter);
        mSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mBeverage = mSpinnerList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mBeverage = mSpinnerList.get(0);
            }
        });
        mBeverage = mSpinnerList.get(0);
    }

    @OnClick({R.id.btn_save, R.id.btn_favourite, R.id.btn_cancle})
    public void onClick(View view) {
        boolean isSuccess;
        switch (view.getId()) {
            case R.id.btn_save:
                DrinkingRecord record = fillRecordInfo(false);
                if (record == null) {
                    return;
                }
                isSuccess = DataHelper.insertDrinkingRecord(this, record);
                toast("insert drinking record " + isSuccess);
                Intent data = new Intent();
                data.putExtra(DrinkingRecord.EXT_DATA_INFO, record);
                setResult(RESULT_OK, data);
                finish();
                break;
            case R.id.btn_favourite:
                DrinkingRecord favRecord = fillRecordInfo(true);
                if (favRecord == null) {
                    return;
                }
                isSuccess = DataHelper.insertDrinkingRecord(this, favRecord);
                toast("save favourite drinking record " + isSuccess);
                Intent fdata = new Intent();
                fdata.putExtra(DrinkingRecord.EXT_DATA_INFO, favRecord);
                setResult(RESULT_OK, fdata);
                finish();
                break;
            case R.id.btn_cancle:
                finish();
                break;
            default:
                break;
        }
    }

    private DrinkingRecord fillRecordInfo(boolean fav) {
        String volumeStr = mEtVolume.getText().toString();
        String degreeStr = mEtDegree.getText().toString();
        String durationStr = mEtDuration.getText().toString();
        String costStr = mEtCost.getText().toString();
        if (TextUtils.isEmpty(volumeStr)) {
            toast("The value of volume can not be empty");
            return null;
        }
        if (TextUtils.isEmpty(degreeStr)) {
            toast("The value of degree can not be empty");
            return null;
        }
        if (TextUtils.isEmpty(durationStr)) {
            toast("The value of duration can not be empty");
            return null;
        }
        if (TextUtils.isEmpty(costStr)) {
            toast("The value of cost can not be empty");
            return null;
        }

        DrinkingRecord record = new DrinkingRecord();
        try {
            record.volume = Float.parseFloat(volumeStr);
        } catch (NumberFormatException exception) {
            record.volume = 0f;
        }
        try {
            record.degree = Float.parseFloat(degreeStr);
        } catch (NumberFormatException exception) {
            record.degree = 0f;
        }
        try {
            record.duration = Integer.parseInt(durationStr);
        } catch (NumberFormatException exception) {
            record.duration = 0;
        }
        try {
            record.cost = Float.parseFloat(costStr);
        } catch (NumberFormatException exception) {
            record.cost = 0f;
        }
        record.beverage = mBeverage;
        record.time = getDateString();
        record.favourite = fav;
        return record;
    }

    private String getDateString () {
        SimpleDateFormat dateFormate = new SimpleDateFormat("yyyy/MM/dd");
        return dateFormate.format(new Date());
    }

    private void toast(CharSequence content) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }

}
