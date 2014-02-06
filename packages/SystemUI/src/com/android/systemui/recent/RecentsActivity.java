/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.systemui.recent;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.UserHandle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.android.systemui.R;
import com.android.systemui.statusbar.StatusBarPanel;

import java.util.List;

public class RecentsActivity extends Activity {
    public static final String TOGGLE_RECENTS_INTENT = "com.android.systemui.recent.action.TOGGLE_RECENTS";
    public static final String PRELOAD_INTENT = "com.android.systemui.recent.action.PRELOAD";
    public static final String CANCEL_PRELOAD_INTENT = "com.android.systemui.recent.CANCEL_PRELOAD";
    public static final String CLOSE_RECENTS_INTENT = "com.android.systemui.recent.action.CLOSE";
    public static final String WINDOW_ANIMATION_START_INTENT = "com.android.systemui.recent.action.WINDOW_ANIMATION_START";
    public static final String PRELOAD_PERMISSION = "com.android.systemui.recent.permission.PRELOAD";
    public static final String WAITING_FOR_WINDOW_ANIMATION_PARAM = "com.android.systemui.recent.WAITING_FOR_WINDOW_ANIMATION";
    private static final String WAS_SHOWING = "was_showing";

    private RecentsPanelView mRecentsPanel;
    private IntentFilter mIntentFilter;
    private boolean mShowing;
    private boolean mForeground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
	Intent intent = new Intent();
	intent.setComponent(new ComponentName("tb.recents","tb.recents.RecentsActivity"));
        startActivity(intent);
        finish();
    }

    public void dismissAndGoHome() {

    }

    public void dismissAndGoBack() {

    }

    public boolean isActivityShowing() {
        return false;
    }

    public static boolean forceOpaqueBackground(Context mContext) {
        return false;
    }
}
