package com.jiaying.mediatablet.net.handler;



import com.jiaying.mediatablet.net.signal.RecSignal;
import com.jiaying.mediatablet.net.utils.FilterSignal;
import com.jiaying.mediatablet.net.utils.RecordState;

import java.util.Observable;

/**
 * Created by Administrator on 2015/9/30 0030.
 */
public class ObserverZXDCSignalRecordAndFilter implements java.util.Observer {
    public ObserverZXDCSignalRecordAndFilter(RecordState recordState, FilterSignal filterSignal) {
        this.recordState = recordState;
        this.filterSignal = filterSignal;
    }

    RecordState recordState;
    FilterSignal filterSignal;

    @Override
    public void update(Observable observable, Object data) {
        switch ((RecSignal) data) {

            case CONFIRM:

                break;

            case PUNCTURE:

                break;

            case START:

                break;

            case END:

                break;

            default:
                break;

        }
    }
}

