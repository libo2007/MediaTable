package com.jiaying.mediatable.fragment;

import android.app.Fragment;
import android.graphics.Color;
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
加压提示
 */
public class PressingFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pressing, null);
        TextView content_txt = (TextView) view.findViewById(R.id.content_txt);
        SpannableString ss = new SpannableString(getString(R.string.fragment_pressing_conent));
        ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.orange)), 4, 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.orange)), 15, 17, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        content_txt.setText(ss);
        return view;
    }
}
