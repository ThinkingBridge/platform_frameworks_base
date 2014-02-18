package android.thinkingbridge;

import android.content.Context;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.AbsListView;
import android.widget.ScrollView;

public class TBUtil {
	public static void onTouchStatusBar(View v) {
		if (v.hasFocus()) {
			DisplayMetrics displaymetrics = new DisplayMetrics();
			((WindowManager) v.getContext().getSystemService(
					Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(
					displaymetrics);
			int screenWidth = displaymetrics.widthPixels;
			int screenHeight = displaymetrics.heightPixels;
			if (v.getMeasuredWidth() / 3 * 4 >= screenWidth
					&& v.getMeasuredHeight() / 2 * 3 > screenHeight) {
				v.dispatchTouchEvent(MotionEvent.obtain(
						SystemClock.uptimeMillis(), SystemClock.uptimeMillis(),
						MotionEvent.ACTION_CANCEL, 0, 0, 0));
				if (v instanceof AbsListView) {
					((AbsListView) v).setSelection(0);
				} else if (v instanceof ScrollView) {
					((ScrollView) v).smoothScrollTo(0, 0);
				} else if (v instanceof WebView) {
					((WebView) v).scrollTo(0, 0);
				} else if (v instanceof FrameLayout) {
					((FrameLayout) v).scrollTo(0,0);
				}
			}
		}
	}
}
