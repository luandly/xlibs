package com.weshop.util.xlibsutils;


import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

/**
 * 屏幕工具类
 */
public class AndlyScreenUtil {

    /**
     * 获取屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getWindowWidth(Context context) {
        DisplayMetrics dm = new android.util.DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * 获取屏幕高度
     *
     * @param context
     * @return
     */
    public static int getWindowHeight(Context context) {
        DisplayMetrics dm = new android.util.DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }


}

