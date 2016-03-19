package com.jiaying.mediatable.activity;

import android.app.FragmentManager;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;

import com.jiaying.mediatable.R;
import com.jiaying.mediatable.fragment.InitializeFragment;
import com.jiaying.mediatable.fragment.WaitingPlasmFragment;
import com.jiaying.mediatable.utils.AppInfoUtils;


/**
 * 主界面
 */
public class MainActivity extends BaseActivity {
    private FragmentManager fragmentManager;

    private RadioGroup mGroup;
    private ImageView overflow_image;//弹出功能
    private PopupWindow mPopupWindow;
    private View mPopView;
    private View mParentView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container, new InitializeFragment()).commit();
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_main);
        mParentView = getLayoutInflater().inflate(R.layout.activity_main,
                null);
        mPopView = getLayoutInflater().inflate(R.layout.popupwin_main, null);
        overflow_image = (ImageView) findViewById(R.id.overflow_image);
        mGroup = (RadioGroup) findViewById(R.id.group);
        mGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.btn_1:
//观看影片
                        break;
                    case R.id.btn_2:
                        //上网
//                        fragmentManager.beginTransaction().replace(R.id.fragment_container, new RegisterFragment()).commit();
                        break;
                    case R.id.btn_3:
                        //意见
//                        fragmentManager.beginTransaction().replace(R.id.fragment_container, new PhysicalExamFragment()).commit();
                        break;
                    case R.id.btn_4:
                        //预约服务
//                        fragmentManager.beginTransaction().replace(R.id.fragment_container, new BloodPlasmaCollectionFragment()).commit();
                        break;

                }
            }
        });
        Test();
    }

    @Override
    public void loadData() {

    }

    @Override
    public void initVariables() {
    }

    private void Test() {

        //等待献浆元
        Button btn1 = (Button) findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().replace(R.id.fragment_container, new WaitingPlasmFragment()).commit();
            }
        });


        //选择功能设置，服务器地址设置以及重启
        overflow_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPopupWindow == null) {
                    mPopupWindow = new PopupWindow(mPopView,
                            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,
                            false);
                    mPopupWindow.setHeight(AppInfoUtils.dip2px(MainActivity.this,130));
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
        });
    }
}
