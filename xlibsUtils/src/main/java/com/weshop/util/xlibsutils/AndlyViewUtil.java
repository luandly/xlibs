package com.weshop.util.xlibsutils;


import android.app.ProgressDialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 界面控件工具
 * 获取焦点、清空输入框的值 等
 */
public class AndlyViewUtil {

    /**
     * 清空多个控件的值
     *
     * @param views 控件View数组 目前匹配 TextView、EditText
     */
    public static void clearTxt(View... views) {
        // 可以通过它的类名来判断：v.getClassName() == "Button"
        // 也可以通过instanceof判断：v instanceof Button
        for (int i = 0; i < views.length; i++) {
            if (views[i] instanceof TextView) {
                TextView tv = (TextView) views[i];
                tv.setText("");
            }
            if (views[i] instanceof EditText) {
                EditText tv = (EditText) views[i];
                tv.setText("");
            }
        }
    }

    /***
     * 判断控件的值是否为空
     *
     * @param views
     */
    public static boolean viewTxtIsNull(View... views) {
        // 可以通过它的类名来判断：v.getClassName() == "Button"
        // 也可以通过instanceof判断：v instanceof Button
        String temp = "";
        boolean flag = false;
        for (int i = 0; i < views.length; i++) {
            if (views[i] instanceof TextView) {
                TextView tv = (TextView) views[i];
                temp = tv.getText().toString();
            }
            if (views[i] instanceof EditText) {
                EditText tv = (EditText) views[i];
                temp = tv.getText().toString();
            }
            if (AndlyStringUtils.isNullOrEmpty(temp)) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 取消进度条
     *
     * @param progressDialog
     */
    public static void cancelProgressDialog(ProgressDialog progressDialog) {
        // if (null != progressDialog) {
        // progressDialog.dismiss(); // 关闭进度条
        // }
        if (null != progressDialog && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    /**
     * 控件是否进行了初始化操作
     *
     * @param views
     * @return
     */
    public static boolean hadInit(View views) {
        boolean flag = false;
        if (null != views) {
            flag = true;
        }
        return flag;
    }

    /**
     * 设置焦点
     *
     * @param view
     */
    public static void viewSetFocus(View view) {
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
    }

    /**
     * @param context
     * @param id
     */
    public static void showCENTERToast(Context context, int id) {
        Toast toast = Toast.makeText(context, context.getString(id),
                Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void showCENTERToast(Context context, String str) {
        Toast toast = Toast.makeText(context, str, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
