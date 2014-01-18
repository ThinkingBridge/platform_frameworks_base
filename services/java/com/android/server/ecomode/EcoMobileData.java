/**
 * Created by Bridge on 2014. 1. 06..
 */

package com.android.server.ecomode;

import android.content.Context;
import android.provider.Settings;
import android.net.ConnectivityManager;
import android.util.Log;

public class EcoMobileData extends EcoMode {
    private static final String TAG = "EcoService_EcoMobileData";

    public EcoMobileData (Context context) {
        super(context);
    }

    protected boolean isEnabled(){
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (!cm.isNetworkSupported(ConnectivityManager.TYPE_MOBILE)){
            return false;
        }
        return Settings.System.getInt(mContext.getContentResolver(), Settings.System.ECO_MODE_MOBILE_DATA, 0) != 0;
    }

    protected boolean doScreenOnAction(){
        return mDoAction;
    }

    protected boolean doScreenOffAction(){
        if (isMobileDataEnabled()){
            mDoAction = true;
        } else {
            mDoAction = false;
        }
        return mDoAction;
    }

    private boolean isMobileDataEnabled(){
        ConnectivityManager cm =
                (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getMobileDataEnabled();
    }

    protected Runnable getScreenOffAction(){
        return new Runnable() {
            @Override
            public void run() {
                ConnectivityManager cm =
                    (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
                cm.setMobileDataEnabled(false);
                Log.d(TAG, "mobileData = false");
            }
        };
    }
    protected Runnable getScreenOnAction(){
        return new Runnable() {
            @Override
            public void run() {
                ConnectivityManager cm =
                    (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
                cm.setMobileDataEnabled(true);
                Log.d(TAG, "mobileData = true");
            }
        };
    }
}
