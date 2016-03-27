package com.jiaying.mediatable.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jiaying.mediatable.R;

/*
穿刺提示界面
 */
public class PunctureFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_puncture, null);
        TextView content_txt = (TextView) view.findViewById(R.id.content_txt);
        SpannableString ss = new SpannableString(getString(R.string.fragment_puncture_content));
        ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.orange)), 4, 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.orange)), 8, 10, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.orange)), 19, 21, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        content_txt.setText(ss);
        return view;
    }
}
