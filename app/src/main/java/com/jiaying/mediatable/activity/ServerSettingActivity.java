package com.jiaying.mediatable.activity;

import android.widget.CompoundButton;
import android.widget.Switch;

import com.jiaying.mediatable.R;
import com.jiaying.mediatable.utils.SetTopView;

/**
 * 服务器配置页面
 */
public class ServerSettingActivity extends BaseActivity {

    @Override
    public void initVariables() {

    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_server_setting);
        new SetTopView(this,R.string.server_setting,true);
    }

    @Override
    public void loadData() {

    }
}
