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
import com.jiaying.mediatable.activity.AppointmentActivity;

/*
预约
 */
public class AppointmentFragment extends Fragment implements View.OnClickListener{
    private Button appointment_btn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_appointment, null);
        appointment_btn = (Button) view.findViewById(R.id.appointment_btn);
        appointment_btn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.appointment_btn){
            //预约
            Intent it = new Intent(getActivity(), AppointmentActivity.class);
            startActivity(it);
        }
    }
}
