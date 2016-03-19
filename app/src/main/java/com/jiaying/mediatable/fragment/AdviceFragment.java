package com.jiaying.mediatable.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jiaying.mediatable.R;
import com.jiaying.mediatable.activity.AdviceActivity;
import com.jiaying.mediatable.activity.AppointmentActivity;
import com.jiaying.mediatable.activity.EvaluationActivity;

/*
投诉与建议
 */
public class AdviceFragment extends Fragment implements View.OnClickListener{
    private Button advice_btn;
    private Button evaluate_btn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_advice, null);
        advice_btn = (Button) view.findViewById(R.id.advice_btn);
        advice_btn.setOnClickListener(this);
        evaluate_btn = (Button) view.findViewById(R.id.evaluate_btn);
        evaluate_btn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.advice_btn){
            //建议
            Intent adviceIntent = new Intent(getActivity(), AdviceActivity.class);
            startActivity(adviceIntent);
        }else if(v.getId() == R.id.evaluate_btn){
            //评论
            Intent evaluationIntent = new Intent(getActivity(), EvaluationActivity.class);
            startActivity(evaluationIntent);
        }
    }
}
