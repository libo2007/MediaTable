package com.jiaying.mediatable.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.jiaying.mediatable.R;
import com.jiaying.mediatable.adapter.VideoAdapter;
import com.jiaying.mediatable.entity.VideoEntity;

import java.util.ArrayList;
import java.util.List;

/*
视频列表
 */
public class VideoFragment extends Fragment {
    private GridView mGridView;
    private List<VideoEntity> mList;
    private VideoAdapter mAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video, null);
        mGridView = (GridView) view.findViewById(R.id.gridview);
        mList = new ArrayList<VideoEntity>();
        mAdapter = new VideoAdapter(getActivity(),mList);
        mGridView.setAdapter(mAdapter);
        for(int i = 0 ;i < 20;i++){
            VideoEntity videoEntity = new VideoEntity();
            videoEntity.setCover_url("http;//www.baidu.com");
            mList.add(videoEntity);
        }
        mAdapter.notifyDataSetChanged();
        return view;
    }

}
