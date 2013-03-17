
package com.android.systemui.screenshot;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.android.systemui.screenshot.GlobalScreenshot;

public class TrashScreenshot extends BroadcastReceiver {
    private static final String LOG_TAG = "TrashScreenshot";

    // Intent bungle fields
    public static final String SCREENSHOT_URI =
            "com.android.systemui.SCREENSHOT_URI";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();

        if (extras == null) {
            // We have nothing, abort
            return;
        }

        Uri screenshotUri = Uri.parse(extras.getString(SCREENSHOT_URI));
        if (screenshotUri != null) {
                context.getContentResolver().delete(screenshotUri, null, null);
        }

        // Dismiss the notification that brought us here.
        NotificationManager notificationManager =
                (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(GlobalScreenshot.SCREENSHOT_NOTIFICATION_ID);
    }

}
