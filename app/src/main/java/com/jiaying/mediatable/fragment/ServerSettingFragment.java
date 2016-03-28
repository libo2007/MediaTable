package com.jiaying.mediatable.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiaying.mediatable.R;
import com.jiaying.mediatable.activity.AdviceActivity;
import com.jiaying.mediatable.activity.EvaluationActivity;

/*
服务器配置
 */
public class ServerSettingFragment extends Fragment implements View.OnClickListener{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_server_setting, null);

        return view;
    }

    @Override
    public void onClick(View v) {

    }
}
