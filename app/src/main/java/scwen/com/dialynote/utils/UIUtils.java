package scwen.com.dialynote.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

/**
 * Created by 解晓辉 on 2017/2/15.
 * 作用： UI相关的工具类
 */

public class UIUtils {
    public static Toast mToast;


    public static Context mContext;

    public static void init(Context context) {
        mContext = context;
    }


    /**
     * 获取应用全局Application context对象
     *
     * @return
     */
    public static Context getAppContext() {
        return mContext;
    }

    /**
     * 根据 color配置的id获取颜色
     */
    public static int getColor(int colorId) {
        return getAppContext().getResources().getColor(colorId);
    }

    /**
     * 返回布局id 所对应的视图对象
     */
    public static View getView(int layoutId) {
        return View.inflate(getAppContext(), layoutId, null);
    }

    /**
     * 根据手机的分辨率从 dip 的单位 转成为 px(像素)
     */
    public static int dip2px(float dpValue) {
        final float scale = getAppContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(float pxValue) {
        final float scale = getAppContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 根据资源中的id获取数组
     */
    public static String[] getStringArray(int arrayId) {
        return getAppContext().getResources().getStringArray(arrayId);
    }


    public static String getString(int stringId) {
        return getAppContext().getResources().getString(stringId);
    }

    /**
     * 获取状态栏高度
     *
     * @param
     * @return
     */
    public static int getStatusBarHeight() {
        /**
         * 获取状态栏高度——方法1
         * */
        int statusBarHeight1 = -1;
//获取status_bar_height资源的ID
        int resourceId = getAppContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight1 = getAppContext().getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight1;
    }

    /**
     * 显示Toast 的工具类
     *
     * @param msg
     */
    public static void showToast(String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(getAppContext(), "", Toast.LENGTH_SHORT);
        }
        mToast.setText(msg);
        mToast.show();
    }

    /**
     * 显示Toast 的工具类
     *
     * @param stringId
     */
    public static void showToast(int stringId) {
        if (mToast == null) {
            mToast = Toast.makeText(getAppContext(), "", Toast.LENGTH_SHORT);
        }
        mToast.setText(getString(stringId));
        mToast.show();
    }


    public static void hideInput(View view) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


}
