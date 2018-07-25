package scwen.com.dialynote.appbase;

import android.app.Application;

import scwen.com.dialynote.utils.UIUtils;

/**
 * Created by xxh on 2018/7/19.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        UIUtils.init(this);
    }
}
