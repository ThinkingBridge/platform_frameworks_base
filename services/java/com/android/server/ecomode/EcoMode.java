/**
 * Created by Bridge on 2014. 1. 06..
 */

package com.android.server.ecomode;

import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.os.Looper;

public abstract class EcoMode {
    protected Context mContext;
    protected boolean mDoAction = false;
    protected EcoService mService;

    public EcoMode (Context context) {
        mContext = context;
    }

    protected boolean runInThread(){
        return true;
    }

    protected abstract boolean isEnabled();
    protected abstract boolean doScreenOnAction();
    protected abstract boolean doScreenOffAction();
    protected abstract Runnable getScreenOffAction();
    protected abstract Runnable getScreenOnAction();

    public void doScreenOff(){
        if(isEnabled() && doScreenOffAction()){
            final Runnable r = getScreenOffAction();
            if(runInThread()){
                Thread thread = new Thread()
                {
                    @Override
                    public void run() {
                        r.run();
                    }
                };

                thread.start();
            } else {
                r.run();
            }
        }

    }
    public void doScreenOn(){
        if(isEnabled() && doScreenOnAction()){
            final Runnable r = getScreenOnAction();
            if(runInThread()){
                Thread thread = new Thread()
                {
                    @Override
                    public void run() {
                        r.run();
                    }
                };

                thread.start();
            } else {
                r.run();
            }
        }
    }
}
