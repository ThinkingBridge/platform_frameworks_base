/*
 * Copyright (C) 2013 The Android Open Source Project
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

import android.app.ActivityOptions;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.UserHandle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;

import com.android.systemui.R;
import com.android.systemui.RecentsComponent;
import com.android.systemui.SystemUI;

public class Recents extends SystemUI implements RecentsComponent {
    private static final String TAG = "Recents";
    private static final boolean DEBUG = false;

    @Override
    public void start() {
        putComponent(RecentsComponent.class, this);
    }

    @Override
    public void toggleRecents(Display display, int layoutDirection,
            View statusBarView) {
        if (DEBUG)
            Log.d(TAG, "toggle recents panel");
        try {
            TaskDescription firstTask = RecentTasksLoader.getInstance(mContext)
                    .getFirstTask();

            Intent intent = new Intent(RecentsActivity.TOGGLE_RECENTS_INTENT);
            intent.setClassName("com.android.systemui",
                    "com.android.systemui.recent.RecentsActivity");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);

            ActivityOptions opts = ActivityOptions.makeCustomAnimation(
                    mContext, R.anim.recents_launch_from_launcher_enter,
                    R.anim.recents_launch_from_launcher_exit);
            mContext.startActivityAsUser(intent, opts.toBundle(),
                    new UserHandle(UserHandle.USER_CURRENT));
        } catch (ActivityNotFoundException e) {
            Log.e(TAG, "Failed to launch RecentAppsIntent", e);
        }
    }

    @Override
    public void preloadRecentTasksList() {
        if (DEBUG)
            Log.d(TAG, "preloading recents");
        Intent intent = new Intent(RecentsActivity.PRELOAD_INTENT);
        intent.setClassName("com.android.systemui",
                "com.android.systemui.recent.RecentsPreloadReceiver");
        mContext.sendBroadcastAsUser(intent, new UserHandle(
                UserHandle.USER_CURRENT));

        RecentTasksLoader.getInstance(mContext).preloadFirstTask();
    }

    @Override
    public void cancelPreloadingRecentTasksList() {
        if (DEBUG)
            Log.d(TAG, "cancel preloading recents");
        Intent intent = new Intent(RecentsActivity.CANCEL_PRELOAD_INTENT);
        intent.setClassName("com.android.systemui",
                "com.android.systemui.recent.RecentsPreloadReceiver");
        mContext.sendBroadcastAsUser(intent, new UserHandle(
                UserHandle.USER_CURRENT));

        RecentTasksLoader.getInstance(mContext).cancelPreloadingFirstTask();
    }

    @Override
    public void closeRecents() {
        if (DEBUG)
            Log.d(TAG, "closing recents panel");
        Intent intent = new Intent(RecentsActivity.CLOSE_RECENTS_INTENT);
        intent.setPackage("com.android.systemui");
        mContext.sendBroadcastAsUser(intent, new UserHandle(
                UserHandle.USER_CURRENT));
    }
}
