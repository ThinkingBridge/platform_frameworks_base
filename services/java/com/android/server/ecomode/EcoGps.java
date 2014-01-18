/**
 * Created by Bridge on 2014. 1. 06..
 */

package com.android.server.ecomode;

import android.content.Context;
import android.location.LocationManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;

public class EcoGps extends EcoMode {
    private static final String TAG = "EcoService_EcoGps";

    public EcoGps(Context context){
        super(context);
    }

    protected boolean isEnabled(){
        return Settings.System.getInt(mContext.getContentResolver(), Settings.System.ECO_MODE_GPS, 0) != 0;
    }

    protected boolean doScreenOnAction(){
        return mDoAction;
    }

    protected boolean doScreenOffAction(){
        if (isGpsEnabled()){
            mDoAction = true;
        } else {
            mDoAction = false;
        }
        return mDoAction;
    }

    private boolean isGpsEnabled(){
        // TODO: check if gps is available on this device?
        return Settings.Secure.isLocationProviderEnabled(
                mContext.getContentResolver(), LocationManager.GPS_PROVIDER);

    }

    protected Runnable getScreenOffAction(){
        return new Runnable() {
            @Override
            public void run() {
                Settings.Secure.setLocationProviderEnabled(mContext.getContentResolver(),
                        LocationManager.GPS_PROVIDER, false);
                Log.d(TAG, "gps = false");
            }
        };
    }
    protected Runnable getScreenOnAction(){
        return new Runnable() {
            @Override
            public void run() {
                Settings.Secure.setLocationProviderEnabled(mContext.getContentResolver(),
                        LocationManager.GPS_PROVIDER, true);
                Log.d(TAG, "gps = true");
            }
        };
    }
}
