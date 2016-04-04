package com.jiaying.mediatablet.activity;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.format.DateFormat;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.cylinder.www.facedetect.FdActivity;
import com.iflytek.cloud.SpeechUtility;
import com.cylinder.www.facedetect.FdActivity;
import com.jiaying.mediatablet.R;
import com.jiaying.mediatablet.thread.AniThread;
import com.jiaying.mediatablet.net.handler.ObserverZXDCSignalRecordAndFilter;
import com.jiaying.mediatablet.net.handler.ObserverZXDCSignalUIHandler;
import com.jiaying.mediatablet.net.thread.ObservableZXDCSignalListenerThread;
import com.jiaying.mediatablet.adapter.VideoAdapter;
import com.jiaying.mediatablet.constants.Status;
import com.jiaying.mediatablet.entity.VideoEntity;
import com.jiaying.mediatablet.net.handler.ObserverZXDCSignalRecordAndFilter;
import com.jiaying.mediatablet.net.handler.ObserverZXDCSignalUIHandler;
import com.jiaying.mediatablet.net.thread.ObservableZXDCSignalListenerThread;
import com.jiaying.mediatablet.thread.AniThread;
import com.jiaying.mediatablet.fragment.AdviceFragment;
import com.jiaying.mediatablet.fragment.AppointmentFragment;
import com.jiaying.mediatablet.fragment.CollectionFragment;
import com.jiaying.mediatablet.fragment.EvaluationFragment;
import com.jiaying.mediatablet.fragment.FistFragment;
import com.jiaying.mediatablet.fragment.FunctionSettingFragment;
import com.jiaying.mediatablet.fragment.InitializeFragment;
import com.jiaying.mediatablet.fragment.OverFragment;
import com.jiaying.mediatablet.fragment.OverServiceEvaluateFragment;
import com.jiaying.mediatablet.fragment.PlayVideoFragment;
import com.jiaying.mediatablet.fragment.PressingFragment;
import com.jiaying.mediatablet.fragment.PunctureEvaluateFragment;
import com.jiaying.mediatablet.fragment.PunctureFragment;
import com.jiaying.mediatablet.fragment.ServerSettingFragment;
import com.jiaying.mediatablet.fragment.WaitingPlasmFragment;
import com.jiaying.mediatablet.fragment.WelcomePlasmFragment;
import com.jiaying.mediatablet.utils.ApkInstaller;
import com.jiaying.mediatablet.utils.AppInfoUtils;
import com.jiaying.mediatablet.utils.MyLog;
import com.jiaying.mediatablet.utils.VideoUtils;
import com.jiaying.mediatablet.widget.VerticalProgressBar;

import java.util.ArrayList;
import java.util.List;

import java.lang.ref.SoftReference;

import java.lang.ref.SoftReference;


/**
 * 主界面
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";


    private FragmentManager fragmentManager;
    private AniThread startFist, stopFist;
    private FdActivity fdActivity;
    private View title_bar_view;//标题栏1
    private View title_bar_back_view;//带返回的标题栏
    private ImageView title_bar_back_img;//返回按钮
    private TextView title_bar_back_txt;//带返回标题栏的标题
    private RadioGroup mGroup;
    private ImageView overflow_image;//弹出功能
    private PopupWindow mPopupWindow;
    private View mPopView;
    private View mParentView;
    private ImageView ivStartFistHint, ivStopFistHint;
    private TextView fun_txt;//功能设置
    private TextView server_txt;//服务器设置
    private View wait_bg;//待机背景
    private TextView net_state_txt;//网络链接状态
    private TextView wifi_not_txt;
    private TextView title_txt;//标题
    private VerticalProgressBar battery_pb;//剩余电量
    private View right_view;//采浆过程状态显示
    private View call_view;//呼叫
    private TextView battery_not_connect_txt;//电源未连接提示
    private ProgressDialog mServiceDialog = null;//服务呼叫对话框
    private ProgressDialog mEvalutionDialog = null;//评价对话框
    private TextView time_txt;//当前时间
    private VerticalProgressBar collect_pb;//采集进度
    private View collection_container;//采浆页面所有的父空间
    private GridView collection_video_gridview;//视频列表
    private List<VideoEntity> collection_video_list;
    private VideoAdapter collection_video_adapter;
    private WebView collection_webview;//上网
    private Button collection_appointment_btn;//预约
    private View collection_advice_view;//包括意见和评价的父控件
    private Button collection_advice_btn;//意见
    private Button collection_evaluate_btn;//评价
    private View fragment_container;//所有fragment的父控件
    private int status = 0;//所在的状态
    //观看视频，上网娱乐，建议，预约 4中状态，用于判断点击后的返回
    private int group_check_index = GROUP_CHECK_INDEX_1;//默认是观看视频
    private static final int GROUP_CHECK_INDEX_1 = 1;
    private static final int GROUP_CHECK_INDEX_2 = 2;
    private static final int GROUP_CHECK_INDEX_3 = 3;
    private static final int GROUP_CHECK_INDEX_4 = 4;
    // 语记安装助手类
    ApkInstaller mInstaller;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            //判断电量
            String action = intent.getAction();
            if (Intent.ACTION_BATTERY_CHANGED.equals(action)) {
                int batteryLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
                //获取最大电量，如未获取到具体数值，则默认为100
                int batteryScale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 100);
                //显示电量
                battery_pb.setMax(batteryScale);
                battery_pb.setProgress(batteryLevel);

                int status = intent.getIntExtra("status", BatteryManager.BATTERY_STATUS_UNKNOWN);
                if (status == BatteryManager.BATTERY_STATUS_CHARGING) {
//                 正在充电
//                    battery_not_connect_txt.setVisibility(View.GONE);
                } else if (status == BatteryManager.BATTERY_STATUS_DISCHARGING) {
                    {
//                        battery_not_connect_txt.setVisibility(View.VISIBLE);
                    }

                }
            } else if (ConnectivityManager.CONNECTIVITY_ACTION.equals(action)) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo mobNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                NetworkInfo wifiNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
//                if (!mobNetInfo.isConnected() && !wifiNetInfo.isConnected()) {
//                    net_state_txt.setVisibility(View.VISIBLE);
//                    wifi_not_txt.setVisibility(View.VISIBLE);
//                } else {
                net_state_txt.setVisibility(View.GONE);
                wifi_not_txt.setVisibility(View.GONE);
//                }
            }
        }
    };

    private static final int WHAT_UPDATE_TIME = 1;
    private static final int WHAT_UPDATE_VIDEO = 2;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case WHAT_UPDATE_TIME:
                    long sysTime = System.currentTimeMillis();
                    CharSequence sysTimeStr = DateFormat.format("HH:mm:ss", sysTime);
                    time_txt.setText(sysTimeStr); //更新时间
                    break;
                case WHAT_UPDATE_VIDEO:
                    collection_video_adapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(receiver, filter);
        new Thread(new TimeRunnable()).start();
        new Thread(new LocalVideoRunable()).start();
        ObserverZXDCSignalRecordAndFilter observerZXDCSignalRecordAndFilter = new ObserverZXDCSignalRecordAndFilter(null, null);
        ObserverZXDCSignalUIHandler observerZXDCSignalUIHandler = new ObserverZXDCSignalUIHandler(new SoftReference<MainActivity>(this));
        ObservableZXDCSignalListenerThread observableZXDCSignalListenerThread = new ObservableZXDCSignalListenerThread(null, null);
        // Add the observers into the observable object.
        observableZXDCSignalListenerThread.addObserver(observerZXDCSignalUIHandler);
        observableZXDCSignalListenerThread.addObserver(observerZXDCSignalRecordAndFilter);
        observableZXDCSignalListenerThread.start();
        //*************************************************************************
        startFist = new AniThread(this, ivStartFistHint, "startfist.gif", 150);
        ivStartFistHint.setVisibility(View.VISIBLE);

        // *************************************************************************
//        stopFist = new AniThread(this, ivStopFistHint, "stopfist.gif", 150);
//        stopFist.startAni();
        mInstaller = new ApkInstaller(this);
        if (!SpeechUtility.getUtility().checkServiceInstalled()) {
            mInstaller.install();
        }
        //获取本地视频

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (receiver != null) {
            unregisterReceiver(receiver);
        }
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_main);
        initTitleBar();
        initGroup();
        initMain();
        Test();
    }

    public void setFdActivity(FdActivity fdActivity) {
        this.fdActivity = fdActivity;
    }

    public FdActivity getFdActivity() {
        return this.fdActivity;
    }

    //中间部分的ui初始化
    private void initMain() {
        fragmentManager = getFragmentManager();

        fragmentManager.beginTransaction().replace(R.id.fragment_container, new InitializeFragment()).commit();
        title_bar_view = findViewById(R.id.title_bar_view);
        title_bar_back_view = findViewById(R.id.title_bar_back_view);
        title_bar_back_img = (ImageView) findViewById(R.id.back_img);
        title_bar_back_img.setOnClickListener(this);
        title_bar_back_txt = (TextView) findViewById(R.id.title_text);
        battery_not_connect_txt = (TextView) findViewById(R.id.battery_not_connect_txt);

        time_txt = (TextView) findViewById(R.id.time_txt);
        collect_pb = (VerticalProgressBar) findViewById(R.id.collect_pb);
        collect_pb.setProgress(80);

        ivStartFistHint = (ImageView) this.findViewById(R.id.iv_start_fist);
        ivStopFistHint = (ImageView) this.findViewById(R.id.iv_stop_fist);

        collection_container = findViewById(R.id.collection_container);
        collection_video_gridview = (GridView) findViewById(R.id.collection_video_gridview);

        collection_video_list = new ArrayList<VideoEntity>();
        collection_video_adapter = new VideoAdapter(this, collection_video_list,new SoftReference<MainActivity>(this));
        collection_video_gridview.setAdapter(collection_video_adapter);

        collection_webview = (WebView) findViewById(R.id.collection_webview);
        collection_appointment_btn = (Button) findViewById(R.id.collection_appointment_btn);
        collection_advice_view = findViewById(R.id.collection_advice_view);
        collection_advice_btn = (Button) findViewById(R.id.collection_advice_btn);
        collection_evaluate_btn = (Button) findViewById(R.id.collection_evaluate_btn);
        fragment_container = findViewById(R.id.fragment_container);
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    //初始化tab选择
    private void initGroup() {
        mGroup = (RadioGroup) findViewById(R.id.group);
        mGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.btn_1:
                        group_check_index = GROUP_CHECK_INDEX_1;
                        //视频列表
//                        fragmentManager.beginTransaction().replace(R.id.fragment_container, new VideoFragment()).commit();
                        collection_video_gridview.setVisibility(View.VISIBLE);
                        collection_webview.setVisibility(View.GONE);
                        collection_advice_view.setVisibility(View.GONE);
                        collection_appointment_btn.setVisibility(View.GONE);
                        break;
                    case R.id.btn_2:
                        group_check_index = GROUP_CHECK_INDEX_2;
                        //上网
//                        fragmentManager.beginTransaction().replace(R.id.fragment_container, new SurfInternetFragment()).commit();


                        collection_video_gridview.setVisibility(View.GONE);
                        collection_webview.setVisibility(View.VISIBLE);
                        collection_advice_view.setVisibility(View.GONE);
                        collection_appointment_btn.setVisibility(View.GONE);

                        collection_webview.loadUrl("http://www.sina.com.cn");
                        collection_webview.setWebViewClient(new WebViewClient() {
                            @Override
                            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                view.loadUrl(url);
                                return true;
                            }
                        });


                        collection_webview.setWebChromeClient(new WebChromeClient() {
                            @Override
                            public void onProgressChanged(WebView view, int newProgress) {
                                //get the newProgress and refresh progress bar
                            }

                            @Override
                            public void onReceivedTitle(WebView view, String title) {
//               this is web title
                            }
                        });
                        break;

                    case R.id.btn_3:
                        group_check_index = GROUP_CHECK_INDEX_3;
                        //意见
//                        fragmentManager.beginTransaction().replace(R.id.fragment_container, new AdviceFragment()).commit();
                        collection_video_gridview.setVisibility(View.GONE);
                        collection_webview.setVisibility(View.GONE);
                        collection_advice_view.setVisibility(View.VISIBLE);
                        collection_appointment_btn.setVisibility(View.GONE);

                        collection_advice_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //建议
                                title_bar_view.setVisibility(View.GONE);
                                title_bar_back_view.setVisibility(View.VISIBLE);
                                title_bar_back_txt.setText(R.string.advice);
                                mGroup.setVisibility(View.GONE);
                                collection_container.setVisibility(View.GONE);
                                fragment_container.setVisibility(View.VISIBLE);
                                fragmentManager.beginTransaction().replace(R.id.fragment_container, new AdviceFragment()).commit();
                            }
                        });

                        collection_evaluate_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                title_bar_view.setVisibility(View.GONE);
                                title_bar_back_view.setVisibility(View.VISIBLE);
                                title_bar_back_txt.setText(R.string.evalution);
                                mGroup.setVisibility(View.GONE);
                                collection_container.setVisibility(View.GONE);
                                fragment_container.setVisibility(View.VISIBLE);
                                fragmentManager.beginTransaction().replace(R.id.fragment_container, new EvaluationFragment()).commit();
                            }
                        });
                        break;
                    case R.id.btn_4:
                        group_check_index = GROUP_CHECK_INDEX_4;
                        //预约
//                        fragmentManager.beginTransaction().replace(R.id.fragment_container, new AppointmentFragment()).commit();
                        collection_video_gridview.setVisibility(View.GONE);
                        collection_webview.setVisibility(View.GONE);
                        collection_advice_view.setVisibility(View.GONE);
                        collection_appointment_btn.setVisibility(View.VISIBLE);
                        collection_appointment_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //点击预约
                                title_bar_view.setVisibility(View.GONE);
                                title_bar_back_view.setVisibility(View.VISIBLE);
                                title_bar_back_txt.setText(R.string.appointment);
                                mGroup.setVisibility(View.GONE);
                                collection_container.setVisibility(View.GONE);
                                fragment_container.setVisibility(View.VISIBLE);
                                fragmentManager.beginTransaction().replace(R.id.fragment_container, new AppointmentFragment()).commit();
                            }
                        });
                        break;

                }
            }
        });
    }


    //标题栏初始化
    private void initTitleBar() {
        mParentView = getLayoutInflater().inflate(R.layout.activity_main,
                null);
        mPopView = getLayoutInflater().inflate(R.layout.popupwin_main, null);
        right_view = findViewById(R.id.right_view);
        call_view = findViewById(R.id.call_view);
        call_view.setOnClickListener(this);
        battery_pb = (VerticalProgressBar) findViewById(R.id.battery_pb);
        wifi_not_txt = (TextView) findViewById(R.id.wifi_not_txt);
        net_state_txt = (TextView) findViewById(R.id.net_state_txt);
        net_state_txt.setOnClickListener(this);
        //选择功能设置，服务器地址设置以及重启
        overflow_image = (ImageView) findViewById(R.id.overflow_image);
        overflow_image.setOnClickListener(this);
        fun_txt = (TextView) mPopView.findViewById(R.id.fun_txt);
        fun_txt.setOnClickListener(this);
        server_txt = (TextView) mPopView.findViewById(R.id.server_txt);
        server_txt.setOnClickListener(this);
        wait_bg = findViewById(R.id.wait_bg);
        title_txt = (TextView) findViewById(R.id.title_txt);
    }

    @Override
    public void loadData() {

    }

    @Override
    public void initVariables() {
    }

    private void Test() {

        //显示
        Button btn00 = (Button) findViewById(R.id.btnShow);
        btn00.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = findViewById(R.id.ll_test);
                view.setVisibility(View.VISIBLE);
            }
        });
        //隐藏
        Button btn01 = (Button) findViewById(R.id.btnHide);
        btn01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = findViewById(R.id.ll_test);
                view.setVisibility(View.GONE);
            }
        });
        //初始化
        Button btn0 = (Button) findViewById(R.id.btn0);
        btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status = Status.STATUS_INIT;
                right_view.setVisibility(View.GONE);
                wait_bg.setVisibility(View.GONE);
                title_txt.setText(R.string.app_name);
                mGroup.setVisibility(View.GONE);
                ivStartFistHint.setVisibility(View.GONE);
                ivStopFistHint.setVisibility(View.GONE);
                collection_container.setVisibility(View.GONE);
                fragment_container.setVisibility(View.VISIBLE);
                fragmentManager.beginTransaction().replace(R.id.fragment_container, new InitializeFragment()).commit();
            }
        });
        //休眠界面
        Button btn1_1 = (Button) findViewById(R.id.btn1_1);
        btn1_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                right_view.setVisibility(View.GONE);
                wait_bg.setVisibility(View.VISIBLE);
                title_txt.setText(R.string.app_name);
                mGroup.setVisibility(View.GONE);
                ivStartFistHint.setVisibility(View.GONE);
                ivStopFistHint.setVisibility(View.GONE);
                collection_container.setVisibility(View.GONE);
                fragment_container.setVisibility(View.VISIBLE);
                fragmentManager.beginTransaction().replace(R.id.fragment_container, new WaitingPlasmFragment()).commit();
            }
        });

        //等待献浆元
        Button btn1 = (Button) findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status = Status.STATUS_WAIT_PLASM;
                right_view.setVisibility(View.GONE);
                wait_bg.setVisibility(View.GONE);
                title_txt.setText(R.string.fragment_wait_plasm_title);
                mGroup.setVisibility(View.GONE);
                ivStartFistHint.setVisibility(View.GONE);
                ivStopFistHint.setVisibility(View.GONE);
                collection_container.setVisibility(View.GONE);
                fragment_container.setVisibility(View.VISIBLE);
                WaitingPlasmFragment waitingPlasmFragment = WaitingPlasmFragment.newInstance(getString(R.string.general_welcome), "");
                fragmentManager.beginTransaction().replace(R.id.fragment_container, waitingPlasmFragment).commit();
            }
        });
        //欢迎献浆
        Button btn2 = (Button) findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status = Status.STATUS_WELCOME_PLASM;
                right_view.setVisibility(View.GONE);
                wait_bg.setVisibility(View.GONE);
                title_txt.setText(R.string.fragment_welcome_plasm_title);
                mGroup.setVisibility(View.GONE);
                ivStartFistHint.setVisibility(View.GONE);
                ivStopFistHint.setVisibility(View.GONE);
                collection_container.setVisibility(View.GONE);
                fragment_container.setVisibility(View.VISIBLE);
                WelcomePlasmFragment welcomeFragment = WelcomePlasmFragment.newInstance(MainActivity.this.getString(R.string.sloganoneabove), "李白" + ", " + MainActivity.this.getString(R.string.sloganonebelow));

                fragmentManager.beginTransaction().replace(R.id.fragment_container, welcomeFragment).commit();
            }
        });

        //加压提示
        Button btn3 = (Button) findViewById(R.id.btn3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status = Status.STATUS_PRESSING;
                right_view.setVisibility(View.GONE);
                wait_bg.setVisibility(View.GONE);
                title_txt.setText(R.string.fragment_pressing_title);
                mGroup.setVisibility(View.GONE);
                ivStartFistHint.setVisibility(View.GONE);
                ivStopFistHint.setVisibility(View.GONE);
                collection_container.setVisibility(View.GONE);
                fragment_container.setVisibility(View.VISIBLE);
                fragmentManager.beginTransaction().replace(R.id.fragment_container, new PressingFragment()).commit();
            }
        });

        //穿刺提示
        Button btn4 = (Button) findViewById(R.id.btn4);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status = Status.STATUS_PUNCTURE;
                right_view.setVisibility(View.GONE);
                wait_bg.setVisibility(View.GONE);
                title_txt.setText(R.string.fragment_puncture_title);
                mGroup.setVisibility(View.GONE);
                ivStartFistHint.setVisibility(View.GONE);
                ivStopFistHint.setVisibility(View.GONE);
                collection_container.setVisibility(View.GONE);
                fragment_container.setVisibility(View.VISIBLE);
                fragmentManager.beginTransaction().replace(R.id.fragment_container, new PunctureFragment()).commit();
            }
        });

        //穿刺视频播放
        Button btn5 = (Button) findViewById(R.id.btn5);
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status = Status.STATUS_PUNCTURE;
                right_view.setVisibility(View.GONE);
                wait_bg.setVisibility(View.GONE);
                title_txt.setText(R.string.fragment_puncture_video);
                mGroup.setVisibility(View.GONE);
                ivStartFistHint.setVisibility(View.GONE);
                ivStopFistHint.setVisibility(View.GONE);
                collection_container.setVisibility(View.GONE);
                fragment_container.setVisibility(View.VISIBLE);
                fragmentManager.beginTransaction().replace(R.id.fragment_container, new PlayVideoFragment()).commit();
            }
        });

        //穿刺评价
        Button btn6 = (Button) findViewById(R.id.btn6);
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                right_view.setVisibility(View.GONE);
                wait_bg.setVisibility(View.GONE);
                title_txt.setText(R.string.fragment_puncture_evaluate);
                mGroup.setVisibility(View.GONE);
                ivStartFistHint.setVisibility(View.GONE);
                ivStopFistHint.setVisibility(View.GONE);
                collection_container.setVisibility(View.GONE);
                fragment_container.setVisibility(View.VISIBLE);

                fragmentManager.beginTransaction().replace(R.id.fragment_container, new PunctureEvaluateFragment()).commit();
            }
        });

        //采集提示
        Button btn7 = (Button) findViewById(R.id.btn7);
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                right_view.setVisibility(View.GONE);
                wait_bg.setVisibility(View.GONE);
                title_txt.setText(R.string.fragment_collect_title);
                mGroup.setVisibility(View.GONE);
                ivStartFistHint.setVisibility(View.GONE);
                ivStopFistHint.setVisibility(View.GONE);
                collection_container.setVisibility(View.GONE);
                fragment_container.setVisibility(View.VISIBLE);
                fragmentManager.beginTransaction().replace(R.id.fragment_container, new CollectionFragment()).commit();
            }
        });

        //采集播放视频，并且显示采集的进度等信息
        Button btn8 = (Button) findViewById(R.id.btn8);
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status = Status.STATUS_COLLECTION;
                title_bar_view.setVisibility(View.GONE);
                title_bar_back_view.setVisibility(View.VISIBLE);
                title_bar_back_txt.setText(R.string.play_video);
                right_view.setVisibility(View.VISIBLE);
                mGroup.setVisibility(View.GONE);
                ivStartFistHint.setVisibility(View.GONE);
                ivStopFistHint.setVisibility(View.GONE);
                collection_container.setVisibility(View.GONE);
                fragment_container.setVisibility(View.VISIBLE);
                showEvalutionDialog();
                fragmentManager.beginTransaction().replace(R.id.fragment_container, new PlayVideoFragment()).commit();
            }
        });
        //握拳提示
        Button btn9 = (Button) findViewById(R.id.btn9);
        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status = Status.STATUS_FIST;
                startFist.startAni();
                wait_bg.setVisibility(View.GONE);
                mGroup.setVisibility(View.GONE);
                ivStartFistHint.setVisibility(View.VISIBLE);
                ivStopFistHint.setVisibility(View.VISIBLE);
                collection_container.setVisibility(View.GONE);
                fragment_container.setVisibility(View.VISIBLE);
                fragmentManager.beginTransaction().replace(R.id.fragment_container, new FistFragment()).commit();
            }
        });

        //结束服务评价
        Button btn10 = (Button) findViewById(R.id.btn10);
        btn10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status = Status.STATUS_OVER;
                right_view.setVisibility(View.GONE);
                mGroup.setVisibility(View.GONE);
                ivStartFistHint.setVisibility(View.GONE);
                ivStopFistHint.setVisibility(View.GONE);
                collection_container.setVisibility(View.GONE);
                fragment_container.setVisibility(View.VISIBLE);
                fragmentManager.beginTransaction().replace(R.id.fragment_container, new OverServiceEvaluateFragment()).commit();
            }
        });

        //结束欢送
        Button btn11 = (Button) findViewById(R.id.btn11);
        btn11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status = Status.STATUS_OVER;
                right_view.setVisibility(View.GONE);
                mGroup.setVisibility(View.GONE);
                ivStartFistHint.setVisibility(View.GONE);
                ivStopFistHint.setVisibility(View.GONE);
                collection_container.setVisibility(View.GONE);
                fragment_container.setVisibility(View.VISIBLE);
                fragmentManager.beginTransaction().replace(R.id.fragment_container, new OverFragment()).commit();
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent it = null;
        switch (v.getId()) {
            case R.id.fun_txt:
                //功能设置
//                it = new Intent(MainActivity.this, FunctionSettingActivity.class);
                title_bar_view.setVisibility(View.GONE);
                title_bar_back_view.setVisibility(View.VISIBLE);
                title_bar_back_txt.setText(R.string.func_setting);
                right_view.setVisibility(View.GONE);
                mGroup.setVisibility(View.GONE);
                collection_container.setVisibility(View.GONE);
                fragment_container.setVisibility(View.VISIBLE);
                fragmentManager.beginTransaction().replace(R.id.fragment_container, new FunctionSettingFragment()).addToBackStack(null).commit();
                mPopupWindow.dismiss();
                break;
            case R.id.server_txt:
                //服务器设置
//                it = new Intent(MainActivity.this, ServerSettingActivity.class);
                title_bar_view.setVisibility(View.GONE);
                title_bar_back_view.setVisibility(View.VISIBLE);
                title_bar_back_txt.setText(R.string.server_setting);
                right_view.setVisibility(View.GONE);
                mGroup.setVisibility(View.GONE);
                collection_container.setVisibility(View.GONE);
                fragment_container.setVisibility(View.VISIBLE);
                fragmentManager.beginTransaction().replace(R.id.fragment_container, new ServerSettingFragment()).addToBackStack(null).commit();
                mPopupWindow.dismiss();
                break;
            case R.id.restar_txt:
                //重启
                mPopupWindow.dismiss();
                break;
            case R.id.overflow_image:
                showPopWindow();
                break;
            case R.id.net_state_txt:
                //检测网络和检查服务器配置
//                it = new Intent(MainActivity.this, ServerSettingActivity.class);
                break;
            case R.id.call_view:
                //呼叫护士提供服务
                showCallDialog();
                break;
            case R.id.back_img:
                //返回
                title_bar_back_view.setVisibility(View.GONE);
                title_bar_view.setVisibility(View.VISIBLE);

                MyLog.e(TAG, "status:" + status);
                if (status == Status.STATUS_COLLECTION) {

                    title_bar_view.setVisibility(View.VISIBLE);
                    title_txt.setText(R.string.fragment_collecting_title);
                    title_bar_back_view.setVisibility(View.GONE);
                    fragment_container.setVisibility(View.GONE);
                    mGroup.setVisibility(View.VISIBLE);
                    right_view.setVisibility(View.VISIBLE);
                    collection_container.setVisibility(View.VISIBLE);
                    fragmentManager.beginTransaction().replace(R.id.fragment_container, new InitializeFragment()).commit();

                    if (group_check_index == GROUP_CHECK_INDEX_1) {

                        collection_video_gridview.setVisibility(View.VISIBLE);
                        collection_video_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                //点击播放视频
                            }
                        });

                        collection_video_adapter.notifyDataSetChanged();
                    } else if (group_check_index == GROUP_CHECK_INDEX_3) {

                    } else if (group_check_index == GROUP_CHECK_INDEX_4) {

                    }

                } else {
                    fragmentManager.popBackStack();
                }
                break;
        }
        if (it != null) {
            startActivity(it);
        }
    }


    private void showPopWindow() {
        View view = findViewById(R.id.ll_test);
        view.setVisibility(View.VISIBLE);
        if (mPopupWindow == null) {
            mPopupWindow = new PopupWindow(mPopView,
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,
                    false);
            mPopupWindow.setHeight(AppInfoUtils.dip2px(MainActivity.this, 195));
            mPopupWindow.setWidth(AppInfoUtils.dip2px(MainActivity.this, 210));
            mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
            mPopupWindow.setOutsideTouchable(true);
            mPopupWindow.setFocusable(true);

        }
        mPopupWindow
                .setOnDismissListener(new PopupWindow.OnDismissListener() {

                    @Override
                    public void onDismiss() {
                    }
                });
        mPopupWindow.setAnimationStyle(R.style.popwin_anim_style);
        mPopupWindow.showAtLocation(mParentView, Gravity.RIGHT
                        | Gravity.TOP, AppInfoUtils.dip2px(MainActivity.this, 2),
                AppInfoUtils.dip2px(MainActivity.this, 76));
    }

    //呼叫护士
    private void showCallDialog() {
        if (mServiceDialog == null) {
            mServiceDialog = new ProgressDialog(this);
        }
        mServiceDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mServiceDialog.setCancelable(true);
        mServiceDialog.setCanceledOnTouchOutside(true);
        mServiceDialog.show();
        mServiceDialog.setContentView(R.layout.dlg_call_service);
    }

    //穿刺评价对话框
    private void showEvalutionDialog() {
        if (mEvalutionDialog == null) {
            mEvalutionDialog = new ProgressDialog(this);
        }
        mEvalutionDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mEvalutionDialog.setCancelable(true);
        mEvalutionDialog.setCanceledOnTouchOutside(true);
        mEvalutionDialog.show();
        View view = getLayoutInflater().inflate(R.layout.dlg_evalution, null);
        TextView content_txt = (TextView) view.findViewById(R.id.content_txt);
        SpannableString ss = new SpannableString(getString(R.string.fragment_puncture_evaluate_content));
        ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.orange)), 8, 12, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.orange)), 14, 16, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        content_txt.setText(ss);
        mEvalutionDialog.setContentView(view);
    }

    private class TimeRunnable implements Runnable {

        @Override
        public void run() {
            try {
                while (true) {
                    Message message = new Message();
                    message.what = WHAT_UPDATE_TIME;
                    mHandler.sendMessage(message);
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private class LocalVideoRunable implements Runnable {

        @Override
        public void run() {
            List<VideoEntity> videoList  = VideoUtils.getLocalVideoList(MainActivity.this);
            if(videoList != null){
                collection_video_list.addAll(videoList);
            }
        }
    }
}
