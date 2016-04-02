package com.jiaying.mediatablet.activity;

import com.jiaying.mediatablet.R;
import com.jiaying.mediatablet.utils.SetTopView;

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
