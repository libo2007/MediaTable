package com.jiaying.mediatable.activity;

import com.jiaying.mediatable.R;
import com.jiaying.mediatable.utils.SetTopView;

/**
 * 评价
 */
public class EvaluationActivity extends BaseActivity {

    @Override
    public void initVariables() {

    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_evaluation);
        new SetTopView(this,R.string.evalution,true);
    }

    @Override
    public void loadData() {

    }
}
