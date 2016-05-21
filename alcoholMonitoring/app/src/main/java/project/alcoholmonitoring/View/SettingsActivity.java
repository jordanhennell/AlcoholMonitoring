package project.alcoholmonitoring.view;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.Calendar;

import project.alcoholmonitoring.constant.Constant;
import project.alcoholmonitoring.R;
import project.alcoholmonitoring.entry.Profile;
import project.alcoholmonitoring.utils.AlarmHelper;
import project.alcoholmonitoring.utils.DataHelper;

/**
 * Created by YuxiaoXue on 2016/5/13 0013.
 */
public class SettingsActivity extends Activity implements RadioGroup.OnCheckedChangeListener,
        CompoundButton.OnCheckedChangeListener{

    @ViewInject(R.id.et_name)
    private EditText mEtName;
    @ViewInject(R.id.et_weight)
    private EditText mEtWeight;
    @ViewInject(R.id.et_bmi)
    private EditText mEtBmi;
    @ViewInject(R.id.radio_group)
    private RadioGroup mRadioGroup;
    @ViewInject(R.id.rb_man)
    private RadioButton mRadioButtonMan;
    @ViewInject(R.id.rb_female)
    private RadioButton mRadioButtonFemale;
    @ViewInject(R.id.switch1)
    private Switch mSwitch1;
    @ViewInject(R.id.switch2)
    private Switch mSwitch2;

    private Profile mProfile;
    private boolean mIsAlarmOpen;
    private SharedPreferences mPreferences;

    private AlarmHelper mAlarmHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ViewUtils.inject(this);
        mAlarmHelper = AlarmHelper.getInstance(this);
        mRadioGroup.setOnCheckedChangeListener(this);
        mSwitch1.setOnCheckedChangeListener(this);
        mProfile = DataHelper.loadProfile(this);
        if (mProfile != null) {
            mEtName.setText(mProfile.name);
            mEtWeight.setText(String.valueOf(mProfile.weight));
            mEtBmi.setText(String.format("%.0f", mProfile.bmi));
            mRadioButtonMan.setSelected(mProfile.gender == 1);
            mRadioButtonFemale.setSelected(mProfile.gender == 0);
        } else {
            mProfile = new Profile();
        }
        mPreferences = DataHelper.getSpForData(this);
        mIsAlarmOpen = mPreferences.getBoolean(Constant.SP_ALAME_SETTINGS, false);
        mSwitch1.setChecked(mIsAlarmOpen);
    }

    public void onClick(View view) {

    }

    @Override
    protected void onStop() {
        updateProfileInfo();
        super.onStop();
    }

    private void updateProfileInfo() {
        String name = mEtName.getText().toString().trim();
        String weight = mEtWeight.getText().toString().trim();
        String bmi = mEtBmi.getText().toString().trim();
        mProfile.name = name;
        try {
            mProfile.weight = Integer.parseInt(weight);
        } catch (NumberFormatException e) {
        }
        try {
            mProfile.bmi = Integer.parseInt(bmi);
        } catch (NumberFormatException e){
        }
        if (mProfile.id > 0) {
            DataHelper.updateProfileInfo(this, mProfile);
        } else {
            DataHelper.insertProfileInfo(this, mProfile);
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.rb_man) {
            mProfile.gender = 1;
        } else {
            mProfile.gender = 0;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            if (!mIsAlarmOpen) {
                setDateDialog();
            }
        } else {
            closeAlarm();
        }
    }

    private void saveAlarmSettingToSp(boolean open) {
        mPreferences.edit().putBoolean(Constant.SP_ALAME_SETTINGS, open).commit();
    }

    private void openAlarm() {
        saveAlarmSettingToSp(true);
    }

    private void closeAlarm() {
        saveAlarmSettingToSp(false);
        mAlarmHelper.cancelAlarm();
        mIsAlarmOpen = false;
    }

    private void toast(CharSequence content) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }

    public void setDateDialog() {
        final Calendar c = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                SettingsActivity.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker arg0, int year,
                                  int monthOfYear, int dayOfMonth) {
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, monthOfYear);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                mAlarmHelper.setAlcoholAlarm(c.getTimeInMillis());
                mIsAlarmOpen = true;
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
                .get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }
}
