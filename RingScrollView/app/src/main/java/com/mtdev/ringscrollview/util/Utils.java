package com.mtdev.ringscrollview.util;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by MaoTong on 2016/12/7.
 * QQ:974291433
 */

public class Utils {
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int getWinWidth(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        if (context != null) {
            ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        }
        return dm.widthPixels;
    }

    public static int getWinHeight(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        if (context != null) {
            ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        }
        return dm.heightPixels;
    }

}
