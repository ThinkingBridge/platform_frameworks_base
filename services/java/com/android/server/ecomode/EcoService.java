/**
 * Created by Bridge on 2014. 1. 06..
 */

package com.android.server.ecomode;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Settings;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;
import android.database.ContentObserver;
import android.content.ContentResolver;
import android.os.Handler;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class EcoService extends Service  {

    private static final String TAG = "EcoService";
    private BroadcastReceiver mPowerKeyReceiver;
    private EcoGps mEcoGps;
    private EcoMobileData mEcoMobileData;
    private boolean mEnabled = true;
    private Context mContext;
    private List<EcoMode> fEnabledToggles;
    private List<EcoMode> fAllToggles;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        if (mEnabled){
            unregisterReceiver();
        }
    }

    @Override
    public void onStart(Intent intent, int startid) {
        mContext = getApplicationContext();
        mEnabled = Settings.System.getInt(mContext.getContentResolver(), Settings.System.ECO_MODE_ENABLED, 0) != 0;

        if (mEnabled){
            registerBroadcastReceiver();
        }

        fAllToggles = new ArrayList<EcoMode>();
        mEcoGps = new EcoGps(mContext);
        fAllToggles.add(mEcoGps);
        mEcoMobileData = new EcoMobileData(mContext);
        fAllToggles.add(mEcoMobileData);

        updateEnabledToggles();
    }

    private void registerBroadcastReceiver() {
        final IntentFilter theFilter = new IntentFilter();
        theFilter.addAction(Intent.ACTION_SCREEN_ON);
        theFilter.addAction(Intent.ACTION_SCREEN_OFF);
        theFilter.addAction("android.intent.action.ECO_SERVICE_UPDATE");

        mPowerKeyReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String strAction = intent.getAction();

                if (strAction.equals(Intent.ACTION_SCREEN_OFF)){
                    Log.d(TAG, "scren off");
                    Iterator<EcoMode> nextToggle = fEnabledToggles.iterator();
                    while(nextToggle.hasNext()){
                    	EcoMode toggle = nextToggle.next();
                        toggle.doScreenOff();
                    }
                }
                if (strAction.equals(Intent.ACTION_SCREEN_ON)) {
                    Log.d(TAG, "scren on");
                    Iterator<EcoMode> nextToggle = fEnabledToggles.iterator();
                    while(nextToggle.hasNext()){
                    	EcoMode toggle = nextToggle.next();
                        toggle.doScreenOn();
                    }
                }
                if (strAction.equals("android.intent.action.ECO_SERVICE_UPDATE")){
                    Log.d(TAG, "update enabled toggles");
                    updateEnabledToggles();
                }
            }
        };

        Log.d(TAG, "registerBroadcastReceiver");
        mContext.registerReceiver(mPowerKeyReceiver, theFilter);
    }

    private void unregisterReceiver() {
        try {
            Log.d(TAG, "unregisterReceiver");
            mContext.unregisterReceiver(mPowerKeyReceiver);
        }
        catch (IllegalArgumentException e) {
            mPowerKeyReceiver = null;
        }
    }

    private void updateEnabledToggles() {
        fEnabledToggles = new ArrayList<EcoMode>();
        Iterator<EcoMode> nextToggle = fAllToggles.iterator();
        while(nextToggle.hasNext()){
        	EcoMode toggle = nextToggle.next();
            if (toggle.isEnabled()){
                Log.d(TAG, "active toggle "+ toggle.getClass().getName());
                fEnabledToggles.add(toggle);
            }
        }
    }
}
