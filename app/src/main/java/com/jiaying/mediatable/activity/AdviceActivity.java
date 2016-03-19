package com.jiaying.mediatable.activity;

import com.jiaying.mediatable.R;
import com.jiaying.mediatable.utils.SetTopView;

/**
 * 意见与建议
 */
public class AdviceActivity extends BaseActivity {

    @Override
    public void initVariables() {

    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_advice);
        new SetTopView(this,R.string.advice,true);
    }

    @Override
    public void loadData() {

    }
}
