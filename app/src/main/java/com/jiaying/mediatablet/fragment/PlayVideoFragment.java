package com.jiaying.mediatablet.fragment;



/*
视频播放
 */

import android.app.Activity;
import android.app.Fragment;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;


import com.jiaying.mediatablet.R;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PlayVideoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PlayVideoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlayVideoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String play_url;//视频播放路径
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private SurfaceView surfaceView;
    private MediaPlayer mediaPlayer;
    private View view;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param play_url Parameter 1.视频播放的路径
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlayVideoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PlayVideoFragment newInstance(String play_url, String param2) {
        PlayVideoFragment fragment = new PlayVideoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, play_url);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public PlayVideoFragment() {
        Log.e("PlayVideoFragment", "PlayVideoFragment");
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.e("PlayVideoFragment", "onAttach ");

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("PlayVideoFragment", "onCreate ");

        if (getArguments() != null) {
            play_url = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e("PlayVideoFragment", "onCreateView 1");

        mediaPlayer = new MediaPlayer();
        Log.e("PlayVideoFragment", "onCreateView 2");

        View view = inflater.inflate(R.layout.fragment_play_video, container, false);
        Log.e("PlayVideoFragment", "onCreateView 3");

        surfaceView = (SurfaceView) view.findViewById(R.id.video_player);
        Log.e("PlayVideoFragment", "onCreateView 4");


        this.view = view;
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e("PlayVideoFragment", "onActivityCreated 1");

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("PlayVideoFragment", "onStart ");

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("PlayVideoFragment", "onResume ");

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

                // mediaPlayer state:idle
                Log.e("PlayVideoFragment", "surfaceCreated 1");
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                Log.e("PlayVideoFragment", "surfaceCreated 2");
                mediaPlayer.setDisplay(holder);
                Log.e("PlayVideoFragment", "surfaceCreated 3");
                try {

                    if(TextUtils.isEmpty(play_url)){
                        mediaPlayer.setDataSource("/sdcard/donation.mp4");
                    }else{
                        mediaPlayer.setDataSource(play_url);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                }

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.stop();

                        mp.reset();

                        try {
                            mp.setDataSource("/sdcard/lz.wmv");
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                        }

                        mp.setOnCompletionListener(this);
                        mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                            @Override
                            public void onPrepared(MediaPlayer mp) {
                                // mediaPlayer state:prepared

                                adjustTheScreenSize(mediaPlayer, surfaceView);
                                mp.start();

                                // mediaPlayer state:started
                            }
                        });

                        mediaPlayer.prepareAsync();
                    }
                });

                Log.e("PlayVideoFragment", "surfaceCreated 4");
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        // mediaPlayer state:prepared

                        adjustTheScreenSize(mediaPlayer, surfaceView);
                        mp.start();

                        // mediaPlayer state:started
                    }
                });

                // mediaPlayer state:initialized
                mediaPlayer.prepareAsync();
                Log.e("PlayVideoFragment", "surfaceCreated 5");

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                Log.e("PlayVideoFragment", "surfaceChanged");


            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                Log.e("PlayVideoFragment", "surfaceDestroyed");
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        mediaPlayer.stop();
        mediaPlayer.release();
        Log.e("PlayVideoFragment", "onPause");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(hidden){
            if(mediaPlayer != null){
                mediaPlayer.stop();
                mediaPlayer.release();
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("PlayVideoFragment", "onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e("PlayVideoFragment", "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("PlayVideoFragment", "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        Log.e("PlayVideoFragment", "onDetach");
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    private boolean adjustTheScreenSize(MediaPlayer mp, SurfaceView surfaceView) {

        // The video size.
        int vH = mp.getVideoHeight();
        int vW = mp.getVideoWidth();

        // The layout size.
        int lH = view.findViewById(R.id.video_frame_layout).getHeight();
        int lW = view.findViewById(R.id.video_frame_layout).getWidth();

        // The ratio.
        double vRatio = ((double) (vH) / vW);
        double fRatio = ((double) (lH) / lW);

        //check the ratio.
        if (vRatio > fRatio) {

            ViewGroup.LayoutParams layoutParams = surfaceView.getLayoutParams();
            layoutParams.height = lH;
            layoutParams.width = (int) ((1.0 * vW / vH) * lH - 0.5);

            surfaceView.setLayoutParams(layoutParams);
            surfaceView.setVisibility(View.VISIBLE);
        } else {
            ViewGroup.LayoutParams layoutParams = surfaceView.getLayoutParams();
            layoutParams.width = lW;
            layoutParams.height = (int) ((1.0 * vH / vW) * lW - 0.5);
            surfaceView.setLayoutParams(layoutParams);
            surfaceView.setVisibility(View.VISIBLE);
        }
        return true;
    }
}
