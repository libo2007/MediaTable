package com.jiaying.mediatablet.net.handler;

import android.os.Message;
import android.util.Log;


import com.jiaying.mediatablet.R;
import com.jiaying.mediatablet.activity.MainActivity;
import com.jiaying.mediatablet.fragment.HintFragment;
import com.jiaying.mediatablet.net.signal.RecSignal;
import com.jiaying.mediatablet.thread.AniThread;
import com.jiaying.mediatablet.thread.AniThread;
import com.jiaying.mediatablet.activity.MainActivity;
import com.jiaying.mediatablet.fragment.HintFragment;
import com.jiaying.mediatablet.net.signal.RecSignal;

import java.lang.ref.SoftReference;
import java.util.Observable;

/**
 * Created by Administrator on 2015/9/13 0013.
 */
public class ObserverZXDCSignalUIHandler extends android.os.Handler implements java.util.Observer {

    private SoftReference<MainActivity> srMActivity;
    private AniThread aniThread = null;
    private Boolean isDeal = true;

    public Boolean getIsDeal() {
        return isDeal;
    }

    public void setIsDeal(Boolean isDeal) {
        this.isDeal = isDeal;
    }

    public ObserverZXDCSignalUIHandler(SoftReference<MainActivity> mActivity) {
        Log.e("camera", "ObserverZXDCSignalUIHandler constructor" + mActivity.get().toString());

        this.srMActivity = mActivity;
    }

    @Override
    public void handleMessage(Message msg) {

        super.handleMessage(msg);
        if (isDeal) {
            switch ((RecSignal) msg.obj) {

                // The nurse make sure the info of the donor is right.
                case CONFIRM:
                    Log.e("camera", "ObserverZXDCSignalUIHandler-CONFIRM");

                    dealSignalConfirm(this);
                    break;

                // The nurse punctuate the donor.
                case PUNCTURE:
                    dealSignalPuncture(this);
                    break;

                // Start the collection of plasma.
                case START:
                    Log.e("error","handleMessage==============start");

                    dealSignalStart(this);
                    break;

                // The pressure is not enough, recommend the donor to make a fist.
                case STARTFIST:
                    dealSignalStartFist(this);
                    break;

                case STOPFIST:
                    dealSignalStopFist(this);

                    // The collection is over.
                case END:
                    dealSignalEnd(this);
                    break;
//                case TOHOME:
////                    // Initialize the first fragment which is the slogan which says "献血献浆同样光荣！"
////                    SloganFragment sloganFragment = SloganFragment.newInstance(srMActivity.get().getString(R.string.slogan), "");
////                    // Construct the fragment manager to manager the fragment.
////                    FragmentManager fragmentManager = srMActivity.get().getFragmentManager();
////                    FragmentTransaction transaction = fragmentManager.beginTransaction();
////                    // Add whatever is in the fragment_container view with this fragment,
////                    // and add the transaction to the back stack
////                    transaction.replace(R.id.main_ui_fragment_container, sloganFragment);
////                    // Commit the transaction which won't occur immediately.
////                    transaction.commit();
//                    break;

                default:
            }
        }
    }

    @Override
    public void update(Observable observable, Object data) {
        Message msg = Message.obtain();
        switch ((RecSignal) data) {
            case CONFIRM:
                msg.obj = RecSignal.CONFIRM;
                sendMessage(msg);
                break;

            case PUNCTURE:
                msg.obj = RecSignal.PUNCTURE;
                sendMessage(msg);
                break;

            case START:
                Log.e("error","update==============start");
                Log.e("error","update =============start");
                msg.obj = RecSignal.START;
                sendMessage(msg);
                break;

            case STARTFIST:
                msg.obj = RecSignal.STARTFIST;
                sendMessage(msg);
                break;

            case END:
                msg.obj = RecSignal.END;
                sendMessage(msg);
                break;

            default:
                break;
        }
    }

    private void dealSignalConfirm(ObserverZXDCSignalUIHandler observerMainHandler) {
        Log.e("error", "dealSignalConfirm");
//        if (observerMainHandler.srMActivity.get().getVisibility()) {
//            Log.e("error", "dealSignalConfirm true");
//            // The main ui switches to welcome fragment which says "某某，欢迎你来献浆！"
//            Donor donor = Donor.getInstance();
//            WelcomeFragment welcomeFragment = WelcomeFragment.newInstance(srMActivity.get().getString(R.string.sloganoneabove), donor.getUserName() + ", " + srMActivity.get().getString(R.string.sloganonebelow));
//            observerMainHandler.srMActivity.get().getFragmentManager().beginTransaction().replace(R.id.main_ui_fragment_container, welcomeFragment).commit();
//
//        } else {
//            Log.e("error", "dealSignalConfirm false");
//
//        }
    }

    private void dealSignalPuncture(ObserverZXDCSignalUIHandler observerMainHandler) {
        Log.e("error", "dealSignalPuncture");

//        if (observerMainHandler.srMActivity.get().getVisibility()) {
//            Log.e("error", "dealSignalPuncture true");
////            observerMainHandler.srMActivity.get().getActionBar().hide();
//            // Begin playing the promotion video.
//            observerMainHandler.srMActivity.get().playVideoFragment = PlayVideoFragment.newInstance("", "");
//            observerMainHandler.srMActivity.get().getFragmentManager().beginTransaction().replace(R.id.main_ui_fragment_container, observerMainHandler.srMActivity.get().playVideoFragment).commit();
//        } else {
//            Log.e("error", "dealSignalPuncture false");
//
//        }

    }

    private void dealSignalStart(ObserverZXDCSignalUIHandler observerMainHandler) {
        Log.e("error", "dealSignalStart");
        MainActivity mainActivity = observerMainHandler.srMActivity.get();

        observerMainHandler.srMActivity.get().getFragmentManager().beginTransaction().replace(R.id.fragment_hint_container, HintFragment.newInstance("", "")).commit();
    }

    private void dealSignalStartFist(ObserverZXDCSignalUIHandler observerMainHandler) {
//        Log.e("error", "dealSignalStartFist");
//        MainActivity mainActivity = observerMainHandler.srMActivity.get();
//        ImageView ivStartFistHint = (ImageView) mainActivity.findViewById(R.id.iv_start_fist);
//        ivStartFistHint.setVisibility(View.VISIBLE);
//        AniThread startFist = new AniThread(mainActivity, ivStartFistHint, "startfist.gif", 150);
    }

    private void dealSignalStopFist(ObserverZXDCSignalUIHandler observerMainHandler) {
//        Log.e("error", "dealSignalStopFist");
//        MainActivity mainActivity = observerMainHandler.srMActivity.get();
//        ImageView ivStopFistHint = (ImageView) mainActivity.findViewById(R.id.iv_stop_fist);
//        AniThread startFist = new AniThread(mainActivity, ivStopFistHint, "startfist.gif", 150);
//        ivStopFistHint.setVisibility(View.VISIBLE);
    }

    private void dealSignalEnd(ObserverZXDCSignalUIHandler observerMainHandler) {
        Log.e("error", "结束");
//        Donor donor = Donor.getInstance();
//        String slogan = srMActivity.get().getString(R.string.slogantwoabove);
//        String thanks = donor.getUserName() + ", " + srMActivity.get().getString(R.string.slogantwoabelow);
//        FarewellFragment farewellFragment = FarewellFragment.newInstance(slogan, thanks);
//
//        observerMainHandler.srMActivity.get().getFragmentManager().beginTransaction().replace(R.id.main_ui_fragment_container, farewellFragment).commit();
//
//        if (CameraManager.isRecord()) {
//            CameraManager.getFrontInstance(observerMainHandler.srMActivity.get()).stopRecord(srMActivity.get(), true);
//        }
    }

}


