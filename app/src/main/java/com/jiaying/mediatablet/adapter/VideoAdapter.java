package com.jiaying.mediatablet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;

import com.jiaying.mediatablet.R;
import com.jiaying.mediatablet.entity.VideoEntity;

import java.util.List;

/**
 * 作者：lenovo on 2016/3/19 19:54
 * 邮箱：353510746@qq.com
 * 功能：
 */
public class VideoAdapter extends BaseAdapter {
    private Context mContext;
    private List<VideoEntity> mList;

    public VideoAdapter(Context mContext, List<VideoEntity> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyHolder  holder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.video_item,null);
            holder = new MyHolder();
            holder.play_btn = (Button) convertView.findViewById(R.id.play_btn);
            holder.cover_image = (ImageView) convertView.findViewById(R.id.cover_image);
            convertView.setTag(holder);
        }else{
            holder = (MyHolder) convertView.getTag();
        }

        holder.play_btn.setTag(position);
        holder.play_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return convertView;
    }

    private class MyHolder {
        ImageView cover_image;
        Button play_btn;
    }
}
