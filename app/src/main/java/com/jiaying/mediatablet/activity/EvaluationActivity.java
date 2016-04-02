package com.jiaying.mediatablet.activity;

import com.jiaying.mediatablet.R;
import com.jiaying.mediatablet.utils.SetTopView;

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
