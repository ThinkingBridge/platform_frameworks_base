package com.android.systemui.statusbar.toggles;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.hardware.input.InputManager;
import android.view.View;
import android.view.KeyEvent;
import android.os.PowerManager;
import android.os.SystemClock;

import com.android.systemui.R;

public class SleepToggle extends BaseToggle {

    @Override
    public void init(Context c, int style) {
        super.init(c, style);
        setIcon(R.drawable.ic_qs_sleep);
        setLabel(R.string.quick_settings_sleep_label);
    }

    @Override
    public void onClick(View v) {
        PowerManager pm = (PowerManager) mContext.getSystemService(Context.POWER_SERVICE);
        pm.goToSleep(SystemClock.uptimeMillis());
    }

    @Override
    public boolean onLongClick(View v) {
        triggerVirtualKeypress(KeyEvent.KEYCODE_POWER, true);
        return super.onLongClick(v);
    }

    private void triggerVirtualKeypress(final int keyCode, final boolean longPress) {
        new Thread(new Runnable() {
            public void run() {
                InputManager im = InputManager.getInstance();
                KeyEvent keyEvent;
                if (longPress) {
                    keyEvent = new KeyEvent(KeyEvent.ACTION_DOWN, keyCode);
                    keyEvent.changeFlags(keyEvent, KeyEvent.FLAG_FROM_SYSTEM | KeyEvent.FLAG_LONG_PRESS);
                } else {
                    keyEvent = new KeyEvent(KeyEvent.ACTION_UP, keyCode);
                    keyEvent.changeFlags(keyEvent, KeyEvent.FLAG_FROM_SYSTEM);
                }
                im.injectInputEvent(keyEvent, InputManager.INJECT_INPUT_EVENT_MODE_WAIT_FOR_RESULT);
            }
        }).start();
    }
}
