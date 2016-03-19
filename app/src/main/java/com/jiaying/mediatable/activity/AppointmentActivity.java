package com.jiaying.mediatable.activity;

import android.widget.CompoundButton;
import android.widget.Switch;

import com.jiaying.mediatable.R;
import com.jiaying.mediatable.utils.SetTopView;

/**
 * 预约activity
 */
public class AppointmentActivity extends BaseActivity {

    @Override
    public void initVariables() {

    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_appointment);
        new SetTopView(this,R.string.appointment,true);
    }

    @Override
    public void loadData() {

    }
}
